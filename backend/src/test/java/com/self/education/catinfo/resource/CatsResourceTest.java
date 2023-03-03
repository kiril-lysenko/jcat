package com.self.education.catinfo.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static com.self.education.catinfo.api.Attribute.TAIL_LENGTH;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatRequest;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatResponse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.service.CatsService;

class CatsResourceTest {

    private static final Exception ERROR = new RuntimeException("error message");

    private static final Integer OFFSET = 0;
    private static final Integer LIMIT = 5;

    @Mock
    private CatsService catsService;
    @InjectMocks
    private CatsResource resource;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void shouldFindAllCatsSuccess() {
        final Page<CatsResponse> response = new PageImpl<>(List.of(whiteCatResponse().build()));

        given(catsService.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH)).willReturn(response);

        final ResponseEntity<Page<CatsResponse>> actual =
                resource.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH);
        assertThat(actual, is(ok(response)));

        then(catsService).should(only()).findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH);
    }

    @Test
    void shouldFindAllCatsFailureWhenCatsServiceThrowException() {
        given(catsService.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH)).willThrow(ERROR);

        final Exception actual = assertThrows(RuntimeException.class,
                () -> resource.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH));
        assertThat(actual, is(ERROR));

        then(catsService).should(only()).findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, TAIL_LENGTH);
    }

    @Test
    void shouldCreateCatSuccess() {
        final CatRequest request = whiteCatRequest().build();

        willDoNothing().given(catsService).createCat(request);

        final ResponseEntity<Void> actual = resource.createCat(request);
        assertThat(actual, is(new ResponseEntity<>(CREATED)));

        then(catsService).should(only()).createCat(request);
    }

    @Test
    void shouldCreateCatFailureWhenCatsServiceThrowException() {
        final CatRequest request = whiteCatRequest().build();

        willThrow(ERROR).given(catsService).createCat(request);

        final Exception actual = assertThrows(RuntimeException.class, () -> resource.createCat(request));
        assertThat(actual, is(ERROR));

        then(catsService).should(only()).createCat(request);
    }
}