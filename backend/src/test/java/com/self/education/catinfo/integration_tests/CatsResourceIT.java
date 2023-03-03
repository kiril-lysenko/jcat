package com.self.education.catinfo.integration_tests;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static com.self.education.catinfo.api.Attribute.NAME;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.catinfo.CatInfoApplication;
import com.self.education.catinfo.api.Attribute;
import com.self.education.catinfo.integration_tests.config.H2Config;

@SpringBootTest(classes = { CatInfoApplication.class }, webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = { "classpath:/application-test.properties" })
@Import(H2Config.class)
@EnableConfigurationProperties
class CatsResourceIT {

    @Value("${local.server.port}")
    private int port;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldCreateCatSuccess() throws IOException {
        expectedCreateCat();
    }

    @Test
    void shouldCreateCatReturnBadRequestWhenValidationFailure() throws IOException {
        expectedCreateCatWithError(SC_BAD_REQUEST, "create_cat_bad_request.json",
                "create_cat_bad_request_response.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldCreateCatReturnConflictWhenDuplicateName() throws IOException {
        expectedCreateCatWithError(SC_CONFLICT, "create_cat_with_duplicate_name.json",
                "create_cat_conflict_response.json");
    }

    @Test
    void shouldCreateCatReturnInternalServerErrorWhenWrongColor() throws IOException {
        expectedCreateCatWithError(SC_INTERNAL_SERVER_ERROR, "create_cat_with_wrong_color.json",
                "create_cat_internal_server_error.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldFindAllWithoutParamsSuccess() throws IOException {
        expectedFindAllCats(null, null, null, null, SC_OK, "find_all_cats_success_with_default_params.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldFindAllWithParamsSuccess() throws IOException {
        expectedFindAllCats("1", "1", NAME, Sort.Direction.DESC, SC_OK, "find_all_cats_success.json");
    }

    @Test
    void shouldReturnBadRequestWhenLimitLessThanOne() throws IOException {
        expectedFindAllCats("1", "0", NAME, Sort.Direction.ASC, SC_BAD_REQUEST, "find_all_cats_not_correct_limit.json");
    }

    @Test
    void shouldReturnBadRequestWhenOffsetThanZero() throws IOException {
        expectedFindAllCats("-11", "5", NAME, Sort.Direction.DESC, SC_BAD_REQUEST,
                "find_all_cats_not_correct_offset.json");
    }

    @Test
    void shouldReturnInternalServerErrorWhenWrongParams() throws IOException {
        expectedFindAllCats("90", "99098889900", null, Sort.Direction.DESC, SC_INTERNAL_SERVER_ERROR,
                "find_all_cats_internal_server_error.json");
    }

    private void expectedFindAllCats(final String offset, final String limit, final Attribute attribute,
            final Sort.Direction order, final int statusCode, final String responseFile) throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .param("offset", offset)
                .param("limit", limit)
                .param("order", order)
                .param("attribute", attribute)
                .when()
                .get(buildRequestUrlStr() + "/cats")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private void expectedCreateCat() throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(getRequest("create_cat_success.json"))
                .when()
                .post(buildRequestUrlStr() + "/cat")
                .then()
                .assertThat()
                .statusCode(SC_CREATED);
        //@formatter:on
    }

    private void expectedCreateCatWithError(final int statusCode, final String requestFile, final String responseFile)
            throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(getRequest(requestFile))
                .when()
                .post(buildRequestUrlStr() + "/cat")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private String buildRequestUrlStr() {
        return "http://localhost:" + port + "/cat-info/v1";
    }

    private String getRequest(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/requests/" + file)));
    }

    private String getResponse(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/responses/" + file)));
    }
}
