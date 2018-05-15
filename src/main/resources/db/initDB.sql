DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id               INTEGER IDENTITY PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON user (email);

CREATE TABLE user_role
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
  id               INTEGER IDENTITY PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL
);

CREATE TABLE menu
(
  id                INTEGER IDENTITY PRIMARY KEY,
  restaurant_id     INTEGER               NOT NULL,
  date_entered      DATE DEFAULT current_date NOT NULL,
  CONSTRAINT restaurant_date_idx UNIQUE (restaurant_id, date_entered),
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE menu_item
(
  id                INTEGER IDENTITY PRIMARY KEY,
  menu_id           INTEGER,
  name              VARCHAR(255)            NOT NULL,
  price             INTEGER                 NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
  id                INTEGER IDENTITY PRIMARY KEY,
  menu_id           INTEGER  NOT NULL,
  user_id           INTEGER  NOT NULL,
  date_time         TIMESTAMP DEFAULT now() NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES restaurant (id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_datetime_idx ON vote (user_id, date_time);