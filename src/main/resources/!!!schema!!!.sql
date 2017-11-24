DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS menulists;
DROP TABLE IF EXISTS votes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL
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
  id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_menu_idx
  ON restaurant (name);

CREATE TABLE menus
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  date          DATE DEFAULT now() NOT NULL,
  curvotenum    INTEGER             DEFAULT 0,
  restaurant_id INTEGER            NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_date_idx
  ON menu (date);

CREATE TABLE menulists
(
  id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  dish    VARCHAR NOT NULL,
  price   NUMERIC(10, 4),
  menu_id INTEGER NOT NULL,
  FOREIGN KEY (menu_id) REFERENCES menu (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
  datetime TIMESTAMP DEFAULT now() NOT NULL,
  user_id  INTEGER                 NOT NULL,
  menu_id  INTEGER                 NOT NULL,
  CONSTRAINT datetine_iser_idx UNIQUE (datetime, user_id),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
)