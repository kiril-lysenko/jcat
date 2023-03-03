package com.self.education.catinfo.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static com.self.education.catinfo.api.Attribute.COLOR;
import static com.self.education.catinfo.api.Attribute.NAME;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatRequest;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatResponse;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.domain.Cats;
import com.self.education.catinfo.mapper.CatsMapper;
import com.self.education.catinfo.repository.CatsRepository;

class CatsServiceTest {

    private static final Integer OFFSET = 0;
    private static final Integer LIMIT = 1;
    private static final Exception EXCEPTION = new RuntimeException("some error message");

    @Mock
    private CatsRepository catsRepository;
    @Mock
    private CatsMapper catsMapper;

    private CatsService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new CatsServiceImpl(catsRepository, catsMapper);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(catsRepository, catsMapper);
    }

    @Test
    void shouldFindAllCatsSuccess() {
        given(catsRepository.findAll(Mockito.any(Pageable.class))).willReturn(
                new PageImpl<>(List.of(whiteCatEntity().build())));
        given(catsMapper.transformEntityToResponse(whiteCatEntity().build())).willReturn(whiteCatResponse().build());

        final Page<CatsResponse> actual = service.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, NAME);
        assertThat(actual, is(new PageImpl<>(List.of(whiteCatResponse().build()))));

        then(catsRepository).should(only()).findAll(Mockito.any(Pageable.class));
        then(catsMapper).should(only()).transformEntityToResponse(whiteCatEntity().build());
    }

    @Test
    void shouldCreateCatSuccess() {
        final CatRequest request = whiteCatRequest().build();
        final Cats cat = whiteCatEntity().build();

        given(catsMapper.transformRequestToEntity(request)).willReturn(cat);
        willDoNothing().given(catsRepository).createNewCat(cat);

        service.createCat(request);

        then(catsMapper).should(only()).transformRequestToEntity(request);
        then(catsRepository).should(only()).createNewCat(cat);
    }

    @Test
    void shouldFindAllCatsFailureWhenOffsetBasedPageRequestThrowException() {

        final RuntimeException actual = assertThrows(IllegalArgumentException.class,
                () -> service.findAllCats(-1, LIMIT, Sort.Direction.ASC, COLOR));
        assertThat(actual.getMessage(), is("Offset index must not be less than zero!"));
    }

    @Test
    void shouldFindAllCatsFailureWhenCatsRepositoryThrowException() {
        given(catsRepository.findAll(Mockito.any(Pageable.class))).willThrow(EXCEPTION);

        final Exception actual = assertThrows(RuntimeException.class,
                () -> service.findAllCats(OFFSET, LIMIT, Sort.Direction.ASC, NAME));
        assertThat(actual, is(EXCEPTION));

        then(catsRepository).should(only()).findAll(Mockito.any(Pageable.class));
    }

    @Test
    void shouldCreateCatFailureWhenCatsRepositoryThrowException() {
        final CatRequest request = whiteCatRequest().build();
        final Cats cat = whiteCatEntity().build();

        given(catsMapper.transformRequestToEntity(request)).willReturn(cat);
        willThrow(EXCEPTION).given(catsRepository).createNewCat(cat);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.createCat(request));
        assertThat(actual, is(EXCEPTION));

        then(catsMapper).should(only()).transformRequestToEntity(request);
        then(catsRepository).should(only()).createNewCat(cat);
    }
}