ALTER SEQUENCE cats_seq RESTART WITH 1;
INSERT INTO cats (name, color, tail_length, whiskers_length)
VALUES ('Orange', 'RED', 18, 12),
       ('Snow', 'WHITE', 21, 22),
       ('John', 'BLACK_RED', 15, 11),
       ('Lucky', 'BLACK_RED', 21, 10),
       ('Norman', 'BLACK_RED', 11, 8);
ALTER SEQUENCE cats_seq RESTART WITH 5;

INSERT INTO cat_colors_info (cat_color, count)
VALUES ('BLACK', 7);