package com.self.education.catinfo.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.catinfo.helper.CatInfoHelper.fawnCatInfoEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.fawnCatInfoResponse;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.domain.CatColorsInfo;

class CatColorInfoMapperTest {

    private final CatColorInfoMapper mapper = new CatColorInfoMapperImpl();

    private static Stream<Arguments> provideCatsColorInfo() {
        //@formatter:off
        return Stream.of(
                Arguments.of(fawnCatInfoEntity().build(), fawnCatInfoResponse().build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("provideCatsColorInfo")
    void shouldTransform(final CatColorsInfo entity, final CatsColorInfoResponse response) {
        assertThat(mapper.transform(entity), is(response));
    }
}