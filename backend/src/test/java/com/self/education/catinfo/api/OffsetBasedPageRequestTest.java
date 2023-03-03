package com.self.education.catinfo.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.self.education.catinfo.api.Attribute.NAME;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class OffsetBasedPageRequestTest {

    private static final Integer OFFSET = 2;
    private static final Integer LIMIT = 10;
    private static final OffsetBasedPageRequest OFFSET_BASED_PAGE_REQUEST =
            new OffsetBasedPageRequest(OFFSET, LIMIT, Sort.Direction.DESC, NAME);

    @Test
    void shouldCheckCommonInfo() {
        assertThat(OFFSET_BASED_PAGE_REQUEST.getPageNumber(), is(0));
        assertThat(OFFSET_BASED_PAGE_REQUEST.getPageSize(), is(LIMIT));
        assertThat(OFFSET_BASED_PAGE_REQUEST.getOffset(), is(2L));
        assertThat(OFFSET_BASED_PAGE_REQUEST.getSort(), is(Sort.by(Sort.Direction.DESC, NAME.getName())));
        assertThat(OFFSET_BASED_PAGE_REQUEST.hasPrevious(), is(false));
    }

    @Test
    void shouldFailureWhenLimitLessThanOne() {
        final RuntimeException actual =
                assertThrows(IllegalArgumentException.class, () -> new OffsetBasedPageRequest(OFFSET, -2));
        assertThat(actual.getMessage(), is("Limit must not be less than one!"));
    }

    @Test
    void shouldFailureWhenOffsetLessThanZero() {
        final RuntimeException actual =
                assertThrows(IllegalArgumentException.class, () -> new OffsetBasedPageRequest(-56,LIMIT));
        assertThat(actual.getMessage(), is("Offset index must not be less than zero!"));
    }

    @Test
    void shouldReturnNextPage() {
        final Pageable next = OFFSET_BASED_PAGE_REQUEST.next();
        assertThat(next.getOffset(), is(12L));
    }

    @Test
    void shouldReturnPreviousOrFirstWhenHasPrevious() {
        final OffsetBasedPageRequest request = new OffsetBasedPageRequest(20, LIMIT);
        assertThat(request.previousOrFirst().getOffset(), is(10L));
    }

    @Test
    void shouldReturnPreviousWhenHasNotPreviousValue() {
        assertThat(OFFSET_BASED_PAGE_REQUEST.previous(), is(OFFSET_BASED_PAGE_REQUEST));
    }

    @Test
    void shouldReturnFirstWhenHasNotPrevious() {
        assertThat(OFFSET_BASED_PAGE_REQUEST.previousOrFirst().getOffset(), is(0L));
    }

    @Test
    void shouldCreateNewPageRequestWithPageNumber() {
        assertThat(OFFSET_BASED_PAGE_REQUEST.withPage(45).getOffset(), is(450L));
    }
}