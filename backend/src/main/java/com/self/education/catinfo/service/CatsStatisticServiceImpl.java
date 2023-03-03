package com.self.education.catinfo.service;

import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.domain.CatColorsInfo;
import com.self.education.catinfo.domain.Cats;
import com.self.education.catinfo.domain.CatsStat;
import com.self.education.catinfo.mapper.CatColorInfoMapper;
import com.self.education.catinfo.mapper.CatsStatMapper;
import com.self.education.catinfo.repository.CatColorsInfoRepository;
import com.self.education.catinfo.repository.CatsRepository;
import com.self.education.catinfo.repository.CatsStatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Service
@AllArgsConstructor
public class CatsStatisticServiceImpl implements CatsStatisticService {

    private final CatColorsInfoRepository catColorsInfoRepository;
    private final CatColorInfoMapper catColorInfoMapper;
    private final CatsStatRepository catsStatRepository;
    private final CatsRepository catsRepository;
    private final CatsStatMapper catsStatMapper;

    @Override
    public List<CatsColorInfoResponse> createCatColorsInfo() {
        final List<CatColorsInfo> catColorsInfo = catColorsInfoRepository.countCatsByColor();
        catColorsInfo.forEach(this::createOrUpdate);
        return catColorsInfoRepository.findAll().stream().map(catColorInfoMapper::transform).toList();
    }

    @Override
    public CatsStatResponse createOrUpdateCatsStat() {
        final List<Cats> cats = catsRepository.findAll();
        final List<Integer> tailLengths = cats.stream().map(Cats::getTailLength).toList();
        final List<Integer> whiskersLengths = cats.stream().map(Cats::getWhiskersLength).toList();
        //@formatter:off
        final CatsStat catsStat = CatsStat.builder()
                .lock(TRUE)
                .tailLengthMean(calculateMean(tailLengths))
                .tailLengthMedian(calculateMedian(tailLengths))
                .tailLengthMode(calculateMode(tailLengths))
                .whiskersLengthMean(calculateMean(whiskersLengths))
                .whiskersLengthMedian(calculateMedian(whiskersLengths))
                .whiskersLengthMode(calculateMode(whiskersLengths)).build();
        //@formatter:on
        return catsStatMapper.transform(catsStatRepository.save(catsStat));
    }

    private Double calculateMean(final List<Integer> nums) {
        final Integer sum = nums.stream().reduce(0, Integer::sum);
        return round((double) sum / nums.size());
    }

    private Double calculateMedian(final List<Integer> nums) {
        final List<Integer> sorted = nums.stream().sorted().toList();
        final int size = sorted.size();
        if (size % 2 == 1) {
            return round((double) sorted.get((size + 1) / 2 - 1));
        }
        return round((sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0);
    }

    private Integer[] calculateMode(final List<Integer> nums) {
        final HashMap<Integer, Integer> hashMap = new HashMap<>();
        final List<Integer> modes = new ArrayList<>();
        int frequency = 1;
        for (final Integer num : nums) {
            if (hashMap.get(num) != null) {
                int current = hashMap.get(num);
                current++;
                hashMap.put(num, current);
                if (current > frequency) {
                    frequency = current;
                }
            } else
                hashMap.put(num, 1);
        }
        for (final Map.Entry<Integer, Integer> result : hashMap.entrySet()) {
            if (result.getValue() == frequency)
                modes.add(result.getKey());
        }
        return modes.toArray(new Integer[0]);
    }

    private Double round(final Double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    private void createOrUpdate(final CatColorsInfo catColorInfo) {
        final String color = catColorInfo.getCatColor().name();
        final int count = catColorInfo.getCount();
        final Optional<CatColorsInfo> existing = catColorsInfoRepository.findByColor(color);
        if (existing.isPresent()) {
            catColorsInfoRepository.update(color, count);
        } else {
            catColorsInfoRepository.save(color, count);
        }
    }
}
