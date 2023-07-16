@startuml
object app_user {
{field} + user_id <<PK>>
username <<UN1>>
email <<UN2>>
password
active
}

object app_role {
{field} + role_id <<PK>>
role
}

object app_user_role {
{field} + user_id <<PK>> <<FK1>>
{field} + role_id <<PK>> <<FK2>>
}

object verification_token {
{field} + token_id <<PK>>
expiration_time
token
{field} - user_id <<FK1>>
}

object password_reset_token {
{field} + password_id <<PK>>
token
expiration_time
{field} - user_id <<FK1>>
}

object user_profile {
{field} + profile_id <<PK>>
{field} - user_id <<FK1>>
name
surname
phone <<UN1>>
age
sex
weight
height
{field} - diet_goal_id <<FK2>>
}

object images {
{field} + image_id <<PK>>
name <<UN1>>
type
image_data
{field} - profile_id <<FK1>>
}

object food {
{field} + food_id <<PK>>
{field} - user_id <<FK1>>
name
calories
serving_size_g
fat_total_g
fat_saturated_g
protein_g
sodium_mg
potassium_mg
cholesterol_mg
carbohydrates_total_g
fiber_g
sugar_g
}

object fitness_tips {
{field} + tip_id <<PK>>
{field} - diet_goal_id <<FK1>>
tip
}

object diet_goals {
{field} + diet_goal_id <<PK>>
diet_goal <<UN1>>
}

object body_measurements {
{field} + body_measurement_id <<PK>>
{field} - profile_id <<FK1>>
date
current_weight
calf
thigh
waist
chest
arm
measurement_note
}


app_user "1" <-- "1" user_profile
app_user_role "(1, *)" --> "(1, *)" app_user
app_user_role "(1, *)" --> "(1, *)" app_role
verification_token "1" --> "1" app_user
password_reset_token "1" --> "1" app_user
user_profile "1" --> "(1, *)" diet_goals
user_profile "(0, *)" <-- "1" body_measurements
user_profile "(0, *)" <-- "1" food
diet_goals "(1, *)" <-- "1" fitness_tips
user_profile "1" <-- "(0, 1)" images
@enduml