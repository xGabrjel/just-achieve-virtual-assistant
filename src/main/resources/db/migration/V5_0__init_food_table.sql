CREATE TABLE food
(
    food_id                  SERIAL             NOT NULL,
    profile_id               INT                NOT NULL,
    name                     VARCHAR(128)       NOT NULL,
    calories                 NUMERIC(10,1)      NOT NULL,
    serving_size_g           INT                NOT NULL,
    fat_total_g              NUMERIC(10,1)      NOT NULL,
    fat_saturated_g          NUMERIC(10,1)      NOT NULL,
    protein_g                NUMERIC(10,1)      NOT NULL,
    sodium_mg                NUMERIC(10,1)      NOT NULL,
    potassium_mg             NUMERIC(10,1)      NOT NULL,
    cholesterol_mg           NUMERIC(10,1)      NOT NULL,
    carbohydrates_total_g    NUMERIC(10,1)      NOT NULL,
    fiber_g                  NUMERIC(10,1)      NOT NULL,
    sugar_g                  NUMERIC(10,1)      NOT NULL,
    PRIMARY KEY(food_id),
    CONSTRAINT fk_user_profile_food
    	    FOREIGN KEY (profile_id)
    		    REFERENCES user_profile (profile_id) ON DELETE CASCADE
);