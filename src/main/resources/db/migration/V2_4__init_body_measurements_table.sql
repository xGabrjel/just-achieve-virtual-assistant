CREATE TABLE body_measurements
(
    body_measurement_id         SERIAL                               NOT NULL,
    user_id                     INT                                  NOT NULL,
    date                        TIMESTAMP WITH TIME ZONE             NOT NULL,
    current_weight              NUMERIC(3, 1)                        NOT NULL,
    calf                        NUMERIC(3, 1)                        NOT NULL,
    thigh                       NUMERIC(3, 1)                        NOT NULL,
    waist                       NUMERIC(3, 1)                        NOT NULL,
    chest                       NUMERIC(3, 1)                        NOT NULL,
    arm                         NUMERIC(3, 1)                        NOT NULL,
    measurement_note            TEXT,

    PRIMARY KEY (body_measurement_id),
    CONSTRAINT fk_app_user_body_measurements
        FOREIGN KEY (user_id)
            REFERENCES app_user (user_id)
);
