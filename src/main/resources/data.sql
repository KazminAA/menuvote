DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menus;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('User2', 'user2@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_USER', 100002);

INSERT INTO restaurants (name, address) VALUES
  ('Арчибальд', 'ул.Большая, 21'),
  ('YellowSubmarine', 'ул.Никиты-Кожемяки, 33'),
  ('Словянка', 'ул.Неокрашенная, 2');

INSERT INTO menus (date, restaurant_id) VALUES
  ('2017-11-01', 100004),
  ('2017-11-01', 100003),
  ('2017-11-01', 100005),
  ('2017-11-02', 100003),
  ('2017-11-02', 100004),
  ('2017-11-02', 100005);

INSERT INTO menulists (dish, price, menu_id) VALUES
  ('Свинная вырезка, 100г', 120.00, 100007),
  ('Чешское светлое, 0,5л', 22, 100007),
  ('Селедка под шубой', 55, 100008),
  ('Молоко', 11, 100008),
  ('Борщ классический', 45, 100006),
  ('Макароны по флотски', 15, 100006),
  ('Свинная вырезка, 100г', 120.95, 100009),
  ('Чешское светлое, 0,5л', 18, 100009),
  ('Борщ классический', 44.22, 100011),
  ('Макароны по флотски', 15.10, 100011),
  ('Пирог с черникой', 27, 100010),
  ('Чай', 0.45, 100010);

INSERT INTO votes (date, user_id, menu_id) VALUES
  ('2017-11-01', 100000, 100007),
  ('2017-11-01', 100001, 100007),
  ('2017-11-01', 100002, 100006),
  ('2017-11-02', 100000, 100009),
  ('2017-11-02', 100001, 100010),
  ('2017-11-02', 100002, 100011);