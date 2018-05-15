DELETE FROM vote;
DELETE FROM menu_item;
DELETE FROM menu;
DELETE FROM restaurant;
DELETE FROM user_role;
DELETE FROM user;

INSERT INTO user (id, name, email, password) VALUES
  (1, 'User1', 'user1@mail.com', 'password1'),
  (2, 'User2', 'user2@mail.com', 'password2'),
  (3, 'Admin', 'admin@mail.com', 'admin');

INSERT INTO user_role (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_ADMIN', 3);

INSERT INTO restaurant (id, name) VALUES
  (1, 'Restaurant1'),
  (2, 'Restaurant2');

INSERT INTO menu (id, restaurant_id) VALUES
  (1, 1),
  (2, 2);

INSERT INTO menu_item (id, menu_id, name, price) VALUES
  (1, 1, 'Item1', 300),
  (2, 1, 'Item2', 600),
  (3, 1, 'Item3', 900),
  (4, 2, 'Item1', 200),
  (5, 2, 'Item2', 400),
  (6, 2, 'Item3', 600);

INSERT INTO vote(id, menu_id, user_id) VALUES
  (1, 1, 1)