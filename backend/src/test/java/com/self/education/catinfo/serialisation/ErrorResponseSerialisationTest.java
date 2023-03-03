package com.self.education.catinfo.serialisation;

import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.catinfo.api.ErrorResponse;

public class ErrorResponseSerialisationTest extends JsonTestBase<ErrorResponse> {

    @BeforeEach
    void beforeEach() {
        //@formatter:off
        expected = () -> ErrorResponse.builder()
                .statusCode(SC_CONFLICT)
                .message("some error message")
                .description("Indicates a request conflict with current state of the target resource").build();
        //@formatter:on
        fileName = "expected_error_response.json";
        expectedType = ErrorResponse.class;
    }
}
