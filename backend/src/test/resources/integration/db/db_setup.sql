CREATE TABLE cats_stat
(
    lock                   BOOLEAN DEFAULT TRUE NOT NULL,
    tail_length_mean       FLOAT,
    tail_length_median     FLOAT,
    tail_length_mode       INTEGER ARRAY,
    whiskers_length_mean   FLOAT,
    whiskers_length_median FLOAT,
    whiskers_length_mode   INTEGER ARRAY
);
ALTER TABLE cats_stat
    ADD CONSTRAINT cats_stat_pk PRIMARY KEY (lock);

CREATE TYPE colors AS ENUM ('WHITE','BLACK','BLACK_WHITE','RED','BLACK_RED','RED_WHITE','RED_WHITE_BLACK','BLUE','FAWN','CREAM');

CREATE TABLE cat_colors_info
(
    cat_color colors NOT NULL,
    count     INTEGER
);
ALTER TABLE cat_colors_info
    ADD CONSTRAINT cat_colors_info_pk PRIMARY KEY (cat_color);

CREATE TABLE cats
(
    id              IDENTITY NOT NULL PRIMARY KEY,
    name            VARCHAR(30) NOT NULL UNIQUE,
    color           colors,
    tail_length     INT,
    whiskers_length INT
);

CREATE SEQUENCE cats_seq START WITH 1;
