CREATE TABLE user_profile
(
    profile_id      SERIAL          NOT NULL,
    user_id         INT             NOT NULL,
    name            VARCHAR(32)     NOT NULL,
    surname         VARCHAR(32)     NOT NULL,
    phone           VARCHAR(32)     NOT NULL,
    age             SMALLINT        NOT NULL,
    sex             VARCHAR(32)     NOT NULL,
    weight          NUMERIC(4, 1)   NOT NULL,
    height          NUMERIC(4, 2)   NOT NULL,
    diet_goal_id    INT             NOT NULL,

    PRIMARY KEY (profile_id),
    UNIQUE(phone),
    CONSTRAINT fk_app_user_user_profile
    	FOREIGN KEY (user_id)
            REFERENCES app_user (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_diet_goals_user_profile
        	FOREIGN KEY (diet_goal_id)
                REFERENCES diet_goals (diet_goal_id)
);