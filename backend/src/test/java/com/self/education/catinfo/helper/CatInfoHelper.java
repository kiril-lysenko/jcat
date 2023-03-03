package com.self.education.catinfo.helper;

import static java.lang.Boolean.TRUE;
import static com.self.education.catinfo.domain.Colors.BLACK;
import static com.self.education.catinfo.domain.Colors.FAWN;
import static com.self.education.catinfo.domain.Colors.RED;
import static com.self.education.catinfo.domain.Colors.WHITE;

import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.domain.CatColorsInfo;
import com.self.education.catinfo.domain.Cats;
import com.self.education.catinfo.domain.CatsStat;

public class CatInfoHelper {

    private static final String WHITE_CAT_NAME = "Snow";
    private static final int WHITE_CAT_TAIL_LENGTH = 21;
    private static final int WHITE_CAT_WHISKERS_LENGTH = 22;
    private static final String RED_CAT_NAME = "Orange";
    private static final int RED_CAT_TAIL_LENGTH = 18;
    private static final int RED_CAT_WHISKERS_LENGTH = 12;
    private static final Double TAIL_MEAN = 19.5;
    private static final Double TAIL_MEDIAN = 19.5;
    private static final Integer[] TAIL_MODE = new Integer[] { 18, 21 };
    private static final Double WHISKERS_MEAN = 17.0;
    private static final Double WHISKERS_MEDIAN = 17.0;
    private static final Integer[] WHISKERS_MODE = new Integer[] { 22, 12 };
    public static int BLACK_COLOR_COUNT = 7;
    public static int FAWN_COLOR_COUNT = 3;

    public static CatColorsInfo.CatColorsInfoBuilder blackCatInfoEntity() {
        //@formatter:off
        return CatColorsInfo.builder()
                .catColor(BLACK)
                .count(BLACK_COLOR_COUNT);
        //@formatter:on
    }

    public static CatColorsInfo.CatColorsInfoBuilder fawnCatInfoEntity() {
        //@formatter:off
        return CatColorsInfo.builder()
                .catColor(FAWN)
                .count(FAWN_COLOR_COUNT);
        //@formatter:on
    }

    public static CatsColorInfoResponse.CatsColorInfoResponseBuilder blackCatInfoResponse() {
        //@formatter:off
        return CatsColorInfoResponse.builder()
                .catColor(BLACK)
                .count(BLACK_COLOR_COUNT);
        //@formatter:on
    }

    public static CatsColorInfoResponse.CatsColorInfoResponseBuilder fawnCatInfoResponse() {
        //@formatter:off
        return CatsColorInfoResponse.builder()
                .catColor(FAWN)
                .count(FAWN_COLOR_COUNT);
        //@formatter:on
    }

    public static Cats.CatsBuilder whiteCatEntity() {
        //@formatter:off
        return Cats.builder()
                .id(18L)
                .name(WHITE_CAT_NAME)
                .color(WHITE)
                .tailLength(WHITE_CAT_TAIL_LENGTH)
                .whiskersLength(WHITE_CAT_WHISKERS_LENGTH);
        //@formatter:on
    }

    public static Cats.CatsBuilder redCatEntity() {
        //@formatter:off
        return Cats.builder()
                .id(17L)
                .name(RED_CAT_NAME)
                .color(RED)
                .tailLength(RED_CAT_TAIL_LENGTH)
                .whiskersLength(RED_CAT_WHISKERS_LENGTH);
        //@formatter:on
    }

    public static CatsResponse.CatsResponseBuilder whiteCatResponse() {
        //@formatter:off
        return CatsResponse.builder()
                .name(WHITE_CAT_NAME)
                .color(WHITE)
                .tailLength(WHITE_CAT_TAIL_LENGTH)
                .whiskersLength(WHITE_CAT_WHISKERS_LENGTH);
        //@formatter:on
    }

    public static CatRequest.CatRequestBuilder whiteCatRequest() {
        //@formatter:off
        return CatRequest.builder()
                .name(WHITE_CAT_NAME)
                .color(WHITE)
                .tailLength(WHITE_CAT_TAIL_LENGTH)
                .whiskersLength(WHITE_CAT_WHISKERS_LENGTH);
        //@formatter:on
    }

    public static CatsStat.CatsStatBuilder catsStat() {
        //@formatter:off
        return CatsStat.builder()
                .lock(TRUE)
                .tailLengthMean(TAIL_MEAN)
                .tailLengthMedian(TAIL_MEDIAN)
                .tailLengthMode(TAIL_MODE)
                .whiskersLengthMean(WHISKERS_MEAN)
                .whiskersLengthMedian(WHISKERS_MEDIAN)
                .whiskersLengthMode(WHISKERS_MODE);
        //@formatter:on

    }

    public static CatsStatResponse.CatsStatResponseBuilder catsStatResponse() {
        //@formatter:off
        return CatsStatResponse.builder()
                .tailLengthMean(TAIL_MEAN)
                .tailLengthMedian(TAIL_MEDIAN)
                .tailLengthMode(TAIL_MODE)
                .whiskersLengthMean(WHISKERS_MEAN)
                .whiskersLengthMedian(WHISKERS_MEDIAN)
                .whiskersLengthMode(WHISKERS_MODE);
        //@formatter:on
    }
}
