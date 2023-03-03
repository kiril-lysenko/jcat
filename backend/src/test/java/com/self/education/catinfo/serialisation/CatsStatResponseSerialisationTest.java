package com.self.education.catinfo.serialisation;

import static com.self.education.catinfo.helper.CatInfoHelper.catsStatResponse;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.catinfo.api.CatsStatResponse;

public class CatsStatResponseSerialisationTest extends JsonTestBase<CatsStatResponse> {

    @BeforeEach
    void beforeEach() {
        expected = () -> catsStatResponse().build();
        fileName = "expected_cats_stat.json";
        expectedType = CatsStatResponse.class;
    }
}
