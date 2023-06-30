CREATE TABLE password_reset_token
(
    password_id         SERIAL                          NOT NULL,
    token      VARCHAR(256)                    NOT NULL,
    expiration_time     TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    user_id             INT                             NOT NULL,

    PRIMARY KEY(password_id),
        CONSTRAINT fk_app_user_password_reset_token
        	    FOREIGN KEY (user_id)
        		    REFERENCES app_user (user_id) ON DELETE CASCADE
);