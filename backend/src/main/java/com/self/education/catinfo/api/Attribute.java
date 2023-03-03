package com.self.education.catinfo.api;

public enum Attribute {

    ID("id"),
    NAME("name"),
    COLOR("color"),
    TAIL_LENGTH("tailLength"),
    WHISKERS_LENGTH("whiskersLength");

    private String name;

    Attribute(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
