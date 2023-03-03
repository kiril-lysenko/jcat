package com.self.education.catinfo.serialisation;

import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatRequest;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.catinfo.api.CatRequest;

class CatRequestSerialisationTest extends JsonTestBase<CatRequest> {

    @BeforeEach
    void beforeEach() {
        expected = () -> whiteCatRequest().build();
        fileName = "expected_cat.json";
        expectedType = CatRequest.class;
    }
}
