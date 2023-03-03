package com.self.education.catinfo.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatRequest;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatResponse;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.domain.Cats;

class CatsMapperTest {

    private final CatsMapper mapper = new CatsMapperImpl();

    private static Stream<Arguments> provideCatsEntityAndResponse() {
        //@formatter:off
        return Stream.of(
                Arguments.of(whiteCatEntity().build(), whiteCatResponse().build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    private static Stream<Arguments> provideCatsEntityAndRequest() {
        //@formatter:off
        return Stream.of(
                Arguments.of(whiteCatEntity().id(null).build(), whiteCatRequest().build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("provideCatsEntityAndResponse")
    void shouldTransformEntityToResponse(final Cats entity, final CatsResponse response) {
        assertThat(mapper.transformEntityToResponse(entity), is(response));
    }

    @ParameterizedTest
    @MethodSource("provideCatsEntityAndRequest")
    void shouldTransformRequestToEntity(final Cats entity, final CatRequest request) {
        assertThat(mapper.transformRequestToEntity(request), is(entity));
    }
}