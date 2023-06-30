CREATE TABLE fitness_tips
(
    tip_id              SERIAL              NOT NULL,
    diet_goal_id        INT                 NOT NULL,
    tip                 TEXT                NOT NULL,
    PRIMARY KEY (tip_id),
    CONSTRAINT fk_diet_goals_fitness_tips
        	FOREIGN KEY (diet_goal_id)
                REFERENCES diet_goals (diet_goal_id)
);