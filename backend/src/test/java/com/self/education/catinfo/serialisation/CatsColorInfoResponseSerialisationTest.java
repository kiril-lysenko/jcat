package com.self.education.catinfo.serialisation;

import static com.self.education.catinfo.helper.CatInfoHelper.blackCatInfoResponse;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.catinfo.api.CatsColorInfoResponse;

class CatsColorInfoResponseSerialisationTest extends JsonTestBase<CatsColorInfoResponse> {

    @BeforeEach
    void beforeEach() {
        expected = () -> blackCatInfoResponse().build();
        fileName = "expected_cat_color_info_response.json";
        expectedType = CatsColorInfoResponse.class;
    }
}
