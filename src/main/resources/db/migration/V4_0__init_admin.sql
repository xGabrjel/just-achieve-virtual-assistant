INSERT INTO app_user (user_id, username, email, password, active)
VALUES (9999, 'admin', 'gabriel.luczyszyn@o2.pl', '$2a$12$HLfCBMJDd2guqIAhlomDnuSGnqG5oYewj7F0iDlyRjWF14bgXDmXS', true);

INSERT INTO app_role (role_id, role)
VALUES (9999, 'ADMIN');

INSERT INTO app_user_role (user_id, role_id)
VALUES (9999, 9999);

INSERT INTO user_profile (profile_id, user_id, name, surname, phone, age, sex, weight, height, diet_goal_id)
VALUES (9999, 9999, 'Gabriel', 'Łuczyszyn', '+48 511 507 164', 25, 'MALE', 87.6, 1.83, 2);