CREATE TABLE user_profile
(
    profile_id      SERIAL          NOT NULL,
    name            VARCHAR(32)     NOT NULL,
    surname         VARCHAR(32)     NOT NULL,
    email           VARCHAR(32)     NOT NULL,
    phone           VARCHAR(32)     NOT NULL,
    age             SMALLINT        NOT NULL,
    sex             VARCHAR(32)     NOT NULL,
    weight          NUMERIC(3, 1)   NOT NULL,
    height          SMALLINT        NOT NULL,
    diet_goal_id    INT             NOT NULL,

    PRIMARY KEY (profile_id),
    UNIQUE(phone),
    CONSTRAINT fk_app_user_user_profile
    	FOREIGN KEY (email)
            REFERENCES app_user (email),
    CONSTRAINT fk_diet_goals_user_profile
        	FOREIGN KEY (diet_goal_id)
                REFERENCES diet_goals (diet_goal_id)
);