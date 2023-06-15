INSERT INTO app_user (user_id, username, email, password, active)
VALUES (1, 'admin', 'gabriel.luczyszyn@o2.pl', '$2a$12$HLfCBMJDd2guqIAhlomDnuSGnqG5oYewj7F0iDlyRjWF14bgXDmXS', true);

INSERT INTO app_role (role_id, role)
VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO app_user_role (user_id, role_id)
VALUES (1, 1);