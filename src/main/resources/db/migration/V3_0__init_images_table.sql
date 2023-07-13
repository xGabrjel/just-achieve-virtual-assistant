CREATE TABLE images
(
    image_id            SERIAL          NOT NULL,
    name                VARCHAR(128)    NOT NULL,
    type                VARCHAR(28)     NOT NULL,
    image_data          BYTEA             NOT NULL,
    PRIMARY KEY (image_id),
    UNIQUE(name)
);

INSERT INTO images (name, type, image_data) VALUES ('test.png', 'image/png', E'\\x0123456789ABCDEF');