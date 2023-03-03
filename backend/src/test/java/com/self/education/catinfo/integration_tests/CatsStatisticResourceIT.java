package com.self.education.catinfo.integration_tests;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.catinfo.CatInfoApplication;
import com.self.education.catinfo.integration_tests.config.H2Config;

@SpringBootTest(classes = { CatInfoApplication.class }, webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = { "classpath:/application-test.properties" })
@Import(H2Config.class)
@EnableConfigurationProperties
class CatsStatisticResourceIT {

    @Value("${local.server.port}")
    private int port;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:integration/db/db_drop_table.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "classpath:integration/db/db_setup.sql" })
    void shouldReturnInternalErrorWhenDbDoesNotExist() throws IOException {
        expectedCreateCatColorInfo(SC_INTERNAL_SERVER_ERROR, "db_not_exist_error.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldReturnOkWhenCreateCatColorInfo() throws IOException {
        expectedCreateCatColorInfo(SC_OK, "create_cat_color_info_success.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/cats_data.sql" })
    void shouldReturnOkWhenCreateCatsStatistic() throws IOException {
        expectedCreateCatsStatistic();
    }

    private void expectedCreateCatColorInfo(final int statusCode, final String responseFile) throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post(buildRequestUrlStr() + "/cat-colors")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private void expectedCreateCatsStatistic() throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .post(buildRequestUrlStr() + "/tail-and-whiskers-length")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(sameJSONAs(getResponse("create_cats_statistic_success.json")));
        //@formatter:on
    }

    private String buildRequestUrlStr() {
        return "http://localhost:" + port + "/cat-info/v1/cats-statistic";
    }

    private String getResponse(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/responses/" + file)));
    }
}
