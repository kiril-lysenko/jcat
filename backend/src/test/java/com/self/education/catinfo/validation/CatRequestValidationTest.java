package com.self.education.catinfo.validation;

import com.self.education.catinfo.api.CatRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.self.education.catinfo.domain.Colors.WHITE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSuccessValidate() {
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(defaultCat().build());
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t\n\r "})
    void shouldReturnErrorWhenNameIsBlank(final String name) {
        final CatRequest cat = defaultCat().name(name).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Cat name can't blank"));
    }

    @Test
    void shouldReturnErrorWhenColorIsNull() {
        final CatRequest cat = defaultCat().color(null).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Color can't be null"));
    }

    @Test
    void shouldReturnErrorWhenTailLengthLessThanZero() {
        final CatRequest cat = defaultCat().tailLength(-23).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Tail length must be equal or greater than 0"));
    }

    @Test
    void shouldReturnErrorWhenTailLengthGreaterThanHundred() {
        final CatRequest cat = defaultCat().tailLength(1209).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Tail length must be equal or less than 100"));
    }

    @Test
    void shouldReturnErrorWhenWhiskersLengthLessThanZero() {
        final CatRequest cat = defaultCat().whiskersLength(-89).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Whiskers length must be equal or greater than 0"));
    }

    @Test
    void shouldReturnErrorWhenWhiskersLengthGreaterThanFifty() {
        final CatRequest cat = defaultCat().whiskersLength(89).build();
        final Set<ConstraintViolation<CatRequest>> violations = validator.validate(cat);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("Whiskers length must be equal or less than 50"));
    }

    private CatRequest.CatRequestBuilder defaultCat() {
        //@formatter:off
        return CatRequest.builder()
                .name("Boris")
                .color(WHITE)
                .tailLength(18)
                .whiskersLength(11);
        //@formatter:on
    }
}
