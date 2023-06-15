CREATE TABLE diet_goals
(
    diet_goal_id    SERIAL         NOT NULL,
    diet_goal       VARCHAR(32)    NOT NULL,

    PRIMARY KEY (diet_goal_id),
    UNIQUE(diet_goal_id)
);

