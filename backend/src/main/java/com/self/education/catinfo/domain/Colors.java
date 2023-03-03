package com.self.education.catinfo.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Colors {

    WHITE("White"),
    BLACK("Black"),
    BLACK_WHITE("Black & White"),
    RED("Red"),
    BLACK_RED("Black & Red"),
    RED_WHITE("Red & White"),
    RED_WHITE_BLACK("Red & White & Black"),
    BLUE("Blue"),
    FAWN("Fawn"),
    CREAM("Cream");

    private final String title;

    Colors(final String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
