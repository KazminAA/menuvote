DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS menus_dishes;
DROP TABLE IF EXISTS votes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id         INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  name       VARCHAR                 NOT NULL,
  email      VARCHAR                 NOT NULL,
  password   VARCHAR                 NOT NULL,
  registered DATE DEFAULT now()      NOT NULL,
  enabled    BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id   INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  name VARCHAR NOT NULL,
  address VARCHAR
);
CREATE UNIQUE INDEX restaurant_name_idx
  ON restaurants (name);

CREATE TABLE menus
(
  id            INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  date          DATE DEFAULT now() NOT NULL,
  restaurant_id INTEGER            NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_date_restaurant_idx
  ON menus (date, restaurant_id);

CREATE TABLE dishes
(
  id          INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  name        VARCHAR NOT NULL,
  description VARCHAR DEFAULT ''
);
CREATE UNIQUE INDEX dish_name_idx
  ON dishes (name);

CREATE TABLE menus_dishes
(
  menu_id INTEGER NOT NULL,
  dish_id INTEGER NOT NULL,
  price   NUMERIC(10, 4) DEFAULT 0.0,
  PRIMARY KEY (menu_id, dish_id),
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE,
  FOREIGN KEY (dish_id) REFERENCES dishes (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
  date    DATE DEFAULT now() NOT NULL,
  user_id INTEGER            NOT NULL,
  menu_id INTEGER            NOT NULL,
  CONSTRAINT date_user_idx UNIQUE (date, user_id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
)
