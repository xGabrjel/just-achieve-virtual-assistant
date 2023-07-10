CREATE TABLE images
(
    image_id            SERIAL          NOT NULL,
    name                VARCHAR(128)    NOT NULL,
    type                VARCHAR(28)     NOT NULL,
    image_data          OID             NOT NULL,
    PRIMARY KEY (image_id),
    UNIQUE(name)
);