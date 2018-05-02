DELETE FROM user_role;
DELETE FROM user;

ALTER SEQUENCE USER_SEQ RESTART WITH 1;

INSERT INTO user (name, email, password) VALUES
  ('User1', 'user1@mail.com', 'password1'),
  ('User2', 'user2@mail.com', 'password2'),
  ('Admin', 'admin@mail.com', 'admin');

INSERT INTO user_role (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_ADMIN', 3);