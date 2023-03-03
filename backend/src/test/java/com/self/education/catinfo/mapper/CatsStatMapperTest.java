package com.self.education.catinfo.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.catinfo.helper.CatInfoHelper.catsStat;
import static com.self.education.catinfo.helper.CatInfoHelper.catsStatResponse;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.domain.CatsStat;

class CatsStatMapperTest {

    private final CatsStatMapper mapper = new CatsStatMapperImpl();

    private static Stream<Arguments> provideCatsStat() {
        //@formatter:off
        return Stream.of(
                Arguments.of(catsStat().build(), catsStatResponse().build()),
                Arguments.of(null, null),
                Arguments.of(catsStat().tailLengthMode(null).whiskersLengthMode(null).build(),
                        catsStatResponse().tailLengthMode(null).whiskersLengthMode(null).build())
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("provideCatsStat")
    void shouldTransform(final CatsStat entity, final CatsStatResponse response) {
        assertThat(mapper.transform(entity), is(response));
    }
}