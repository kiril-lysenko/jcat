package com.self.education.catinfo.service;

import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.domain.CatColorsInfo;
import com.self.education.catinfo.domain.CatsStat;
import com.self.education.catinfo.domain.Colors;
import com.self.education.catinfo.mapper.CatColorInfoMapper;
import com.self.education.catinfo.mapper.CatsStatMapper;
import com.self.education.catinfo.repository.CatColorsInfoRepository;
import com.self.education.catinfo.repository.CatsRepository;
import com.self.education.catinfo.repository.CatsStatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.self.education.catinfo.helper.CatInfoHelper.BLACK_COLOR_COUNT;
import static com.self.education.catinfo.helper.CatInfoHelper.FAWN_COLOR_COUNT;
import static com.self.education.catinfo.helper.CatInfoHelper.blackCatInfoEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.blackCatInfoResponse;
import static com.self.education.catinfo.helper.CatInfoHelper.catsStat;
import static com.self.education.catinfo.helper.CatInfoHelper.catsStatResponse;
import static com.self.education.catinfo.helper.CatInfoHelper.fawnCatInfoEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.fawnCatInfoResponse;
import static com.self.education.catinfo.helper.CatInfoHelper.redCatEntity;
import static com.self.education.catinfo.helper.CatInfoHelper.whiteCatEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;

class CatsStatisticServiceTest {

    @Mock
    private CatColorsInfoRepository catColorsInfoRepository;
    @Mock
    private CatColorInfoMapper catColorInfoMapper;
    @Mock
    private CatsStatRepository catsStatRepository;
    @Mock
    private CatsRepository catsRepository;
    @Mock
    private CatsStatMapper catsStatMapper;

    private CatsStatisticService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new CatsStatisticServiceImpl(catColorsInfoRepository, catColorInfoMapper, catsStatRepository, catsRepository, catsStatMapper);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(catColorsInfoRepository, catColorInfoMapper, catsStatRepository, catsRepository, catsStatMapper);
    }

    @Test
    void shouldCreateAndUpdateCatColorsInfoSuccess() {
        final List<CatColorsInfo> catColorsInfo = List.of(blackCatInfoEntity().build(), fawnCatInfoEntity().build());
        final List<CatsColorInfoResponse> expected = List.of(blackCatInfoResponse().build(), fawnCatInfoResponse().build());

        given(catColorsInfoRepository.countCatsByColor()).willReturn(catColorsInfo);
        given(catColorsInfoRepository.findByColor(Colors.BLACK.name())).willReturn(Optional.of(blackCatInfoEntity().build()));
        willDoNothing().given(catColorsInfoRepository).update(Colors.BLACK.name(), BLACK_COLOR_COUNT);
        given(catColorsInfoRepository.findByColor(Colors.FAWN.name())).willReturn(Optional.empty());
        willDoNothing().given(catColorsInfoRepository).save(Colors.FAWN.name(), FAWN_COLOR_COUNT);
        given(catColorsInfoRepository.findAll()).willReturn(catColorsInfo);
        given(catColorInfoMapper.transform(blackCatInfoEntity().build())).willReturn(blackCatInfoResponse().build());
        given(catColorInfoMapper.transform(fawnCatInfoEntity().build())).willReturn(fawnCatInfoResponse().build());

        final List<CatsColorInfoResponse> actual = service.createCatColorsInfo();
        assertThat(actual, is(expected));

        then(catColorsInfoRepository).should(times(1)).countCatsByColor();
        then(catColorsInfoRepository).should(times(1)).findByColor(Colors.BLACK.name());
        then(catColorsInfoRepository).should(times(1)).update(Colors.BLACK.name(), BLACK_COLOR_COUNT);
        then(catColorsInfoRepository).should(times(1)).findByColor(Colors.FAWN.name());
        then(catColorsInfoRepository).should(times(1)).save(Colors.FAWN.name(), FAWN_COLOR_COUNT);
        then(catColorsInfoRepository).should(times(1)).findAll();
        then(catColorInfoMapper).should(times(1)).transform(blackCatInfoEntity().build());
        then(catColorInfoMapper).should(times(1)).transform(fawnCatInfoEntity().build());
    }

    @Test
    void shouldFailureCreateCatColorsInfoWhenCatColorsInfoRepositoryThrowException() {
        final Exception exception = new RuntimeException("some error message");

        given(catColorsInfoRepository.countCatsByColor()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.createCatColorsInfo());
        assertThat(actual, is(exception));

        then(catColorsInfoRepository).should(only()).countCatsByColor();
    }

    @Test
    void shouldCreateCatsStatSuccess() {
        final CatsStatResponse expected = catsStatResponse().build();
        final CatsStat stat = catsStat().build();

        given(catsRepository.findAll()).willReturn(List.of(whiteCatEntity().build(), redCatEntity().build()));
        given(catsStatRepository.save(stat)).willReturn(stat);
        given(catsStatMapper.transform(stat)).willReturn(expected);

        final CatsStatResponse actual = service.createOrUpdateCatsStat();
        assertThat(actual, is(expected));

        then(catsRepository).should(only()).findAll();
        then(catsStatRepository).should(only()).save(stat);
        then(catsStatMapper).should(only()).transform(stat);
    }

    @Test
    void shouldCreateCatsStatSuccessWhenOddNumberOfCats() {
        //@formatter:off
        final CatsStatResponse expected = catsStatResponse()
                .tailLengthMean(20.0)
                .tailLengthMedian(21.0)
                .tailLengthMode(new Integer[]{21})
                .whiskersLengthMean(18.67)
                .whiskersLengthMedian(22.0)
                .whiskersLengthMode(new Integer[]{22}).build();
        final CatsStat stat = catsStat()
                .tailLengthMean(20.0)
                .tailLengthMedian(21.0)
                .tailLengthMode(new Integer[]{21})
                .whiskersLengthMean(18.67)
                .whiskersLengthMedian(22.0)
                .whiskersLengthMode(new Integer[]{22}).build();
        //@formatter:on

        given(catsRepository.findAll()).willReturn(List.of(whiteCatEntity().build(), redCatEntity().build(), whiteCatEntity().build()));
        given(catsStatRepository.save(stat)).willReturn(stat);
        given(catsStatMapper.transform(stat)).willReturn(expected);

        final CatsStatResponse actual = service.createOrUpdateCatsStat();
        assertThat(actual, is(expected));

        then(catsRepository).should(only()).findAll();
        then(catsStatRepository).should(only()).save(stat);
        then(catsStatMapper).should(only()).transform(stat);
    }

    @Test
    void shouldFailureCreateCatsStatWhenCatsRepositoryThrowException() {
        final Exception exception = new RuntimeException("some error message");

        given(catsRepository.findAll()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.createOrUpdateCatsStat());
        assertThat(actual, is(exception));

        then(catsRepository).should(only()).findAll();
    }

    @Test
    void shouldFailureCreateCatsStatWhenCatsStatRepositoryThrowException() {
        final Exception exception = new RuntimeException("some error message");

        given(catsRepository.findAll()).willReturn(List.of(whiteCatEntity().build(), redCatEntity().build()));
        given(catsStatRepository.save(catsStat().build())).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.createOrUpdateCatsStat());
        assertThat(actual, is(exception));

        then(catsRepository).should(only()).findAll();
        then(catsStatRepository).should(only()).save(catsStat().build());
        then(catsStatMapper).should(never()).transform(catsStat().build());
    }
}