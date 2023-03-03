package com.self.education.catinfo.serialisation;

import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatResponse;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.catinfo.api.CatsResponse;

class CatsResponseSerialisationTest extends JsonTestBase<CatsResponse> {

    @BeforeEach
    void beforeEach() {
        expected = () -> whiteCatResponse().build();
        fileName = "expected_cat.json";
        expectedType = CatsResponse.class;
    }
}
