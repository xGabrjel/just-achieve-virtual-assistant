CREATE TABLE verification_token
(
    token_id            SERIAL                          NOT NULL,
    expiration_time     TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    token               VARCHAR(256)                    NOT NULL,
    user_id             INT                             NOT NULL,

    PRIMARY KEY(token_id),
    CONSTRAINT fk_app_user_verification_token
    	    FOREIGN KEY (user_id)
    		    REFERENCES app_user (user_id) ON DELETE CASCADE
);