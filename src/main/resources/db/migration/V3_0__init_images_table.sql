CREATE TABLE images
(
    image_id            SERIAL          NOT NULL,
    name                VARCHAR(256)    NOT NULL,
    type                VARCHAR(28)     NOT NULL,
    image_data          BYTEA           NOT NULL,
    profile_id          INT,

    PRIMARY KEY (image_id),
    UNIQUE(name),
    CONSTRAINT fk_user_profile_images
            FOREIGN KEY (profile_id)
                REFERENCES user_profile (profile_id) ON DELETE CASCADE
);

INSERT INTO images (name, type, image_data) VALUES ('test.png', 'image/png', E'\\x0123456789ABCDEF');
