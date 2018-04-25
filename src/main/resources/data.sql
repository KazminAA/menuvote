DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM menus;
DELETE FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('User2', 'user2@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

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

INSERT INTO dishes (name, description) VALUES
  ('Свинная вырезка', 'готовится на мангале'),
  ('Чешское светлое', 'пиво по классическому рецепту'),
  ('Селедка под шубой', 'пролетарский салат'),
  ('Молоко', 'коровье, 33% жирность'),
  ('Борщ классический', 'просто борщ'),
  ('Макароны по флотски', 'макароны с мясом'),
  ('Пирог с черникой', 'долго не черствеет'),
  ('Чай', 'сорт Принцесса Дури');

INSERT INTO menus_dishes (menu_id, dish_id, price) VALUES
  (100007, 100012, 120.00),
  (100007, 100013, 22),
  (100008, 100014, 55),
  (100008, 100015, 11),
  (100006, 100016, 45),
  (100006, 100017, 15),
  (100009, 100012, 120.95),
  (100009, 100013, 18),
  (100011, 100016, 44.22),
  (100011, 100017, 15.10),
  (100010, 100018, 27),
  (100010, 100019, 0.45);

INSERT INTO votes (date, user_id, menu_id) VALUES
  ('2017-11-01', 100000, 100007),
  ('2017-11-01', 100001, 100007),
  ('2017-11-01', 100002, 100006),
  ('2017-11-02', 100001, 100010),
  ('2017-11-02', 100002, 100011);