package com.self.education.catinfo.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.ResponseEntity.ok;
import static com.self.education.catinfo.helper.CatInfoHelper.blackCatInfoResponse;
import static com.self.education.catinfo.helper.CatInfoHelper.catsStatResponse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.service.CatsStatisticService;

class CatsStatisticResourceTest {

    private static final Exception ERROR = new RuntimeException("some error message");

    @Mock
    private CatsStatisticService catsStatisticService;
    @InjectMocks
    private CatsStatisticResource resource;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void shouldCreateCatsColorInfoSuccess() {
        final List<CatsColorInfoResponse> response = List.of(blackCatInfoResponse().build());

        given(catsStatisticService.createCatColorsInfo()).willReturn(response);

        final ResponseEntity<List<CatsColorInfoResponse>> actual = resource.createCatsColorInfo();
        assertThat(actual, is(ok(response)));

        then(catsStatisticService).should(only()).createCatColorsInfo();
    }

    @Test
    void shouldCreateCatsColorInfoFailureWhenCatsStatisticServiceThrowException() {
        given(catsStatisticService.createCatColorsInfo()).willThrow(ERROR);

        final Exception actual = assertThrows(RuntimeException.class, () -> resource.createCatsColorInfo());
        assertThat(actual, is(ERROR));

        then(catsStatisticService).should(only()).createCatColorsInfo();
    }

    @Test
    void shouldCreateCatsStatisticSuccess() {
        final CatsStatResponse response = catsStatResponse().build();

        given(catsStatisticService.createOrUpdateCatsStat()).willReturn(response);

        final ResponseEntity<CatsStatResponse> actual = resource.createOrUpdateCatsStatistic();
        assertThat(actual, is(ok(response)));

        then(catsStatisticService).should(only()).createOrUpdateCatsStat();
    }

    @Test
    void shouldCreateCatsStatisticFailureWhenCatsStatisticServiceThrowException() {
        given(catsStatisticService.createOrUpdateCatsStat()).willThrow(ERROR);

        final Exception actual = assertThrows(RuntimeException.class, () -> resource.createOrUpdateCatsStatistic());
        assertThat(actual, is(ERROR));

        then(catsStatisticService).should(only()).createOrUpdateCatsStat();
    }
}