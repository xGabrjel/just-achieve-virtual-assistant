CREATE TABLE body_measurements
(
    body_measurement_id         SERIAL                               NOT NULL,
    profile_id                  INT                                  NOT NULL,
    date                        TIMESTAMP WITH TIME ZONE             NOT NULL,
    current_weight              NUMERIC(4, 1)                        NOT NULL,
    calf                        NUMERIC(4, 1)                        NOT NULL,
    thigh                       NUMERIC(4, 1)                        NOT NULL,
    waist                       NUMERIC(4, 1)                        NOT NULL,
    chest                       NUMERIC(4, 1)                        NOT NULL,
    arm                         NUMERIC(4, 1)                        NOT NULL,
    measurement_note            TEXT,

    PRIMARY KEY (body_measurement_id),
    CONSTRAINT fk_profile_id_body_measurements
        FOREIGN KEY (profile_id)
            REFERENCES user_profile (profile_id) ON DELETE CASCADE
);
