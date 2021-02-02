insert into author(id, fio)
values (1, 'Толстой Лев Николаевич'),
       (2, 'Пушкин Александр Сергеевич'),
       (3, 'Дойл Артур Конан'),
       (4, 'Стругацкий Аркадий Натанович'),
       (5, 'Стругацкий Борис Натанович');

insert into genre(id, name)
values (1, 'Роман'),
       (2, 'Рассказ'),
       (3, 'Повесть'),
       (4, 'Стихотворение'),
       (5, 'Сказка'),
       (6, 'Детектив'),
       (7, 'Приключение'),
       (8, 'Фантастика'),
       (9, 'Юмор');

insert into book(id, name, year_edition)
values (1, 'Война и мир', 1981),
       (2, 'Хаджи-Мурат', 1975),
       (3, 'Евгений Онегин', 1987),
       (4, 'Сказка о рыбаке и рыбке', 2015),
       (5, 'Песнь о вещем Олеге', 2015),
       (6, 'Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе', 1998),
       (7, 'Улитка на склоне', 2001),
       (8, 'Понедельник начинается в субботу', 2001);

insert into book_author(book_id, author_id)
values (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 3),
       (7, 4),
       (7, 5),
       (8, 4),
       (8, 5);

insert into book_genre(book_id, genre_id)
values (1, 1),
       (2, 3),
       (3, 1),
       (3, 4),
       (4, 5),
       (4, 4),
       (5, 3),
       (5, 4),
       (6, 2),
       (6, 3),
       (7, 1),
       (7, 8),
       (8, 3),
       (8, 8),
       (8, 9);

insert into comment_book(id, book_id, comment)
values (1, 7, 'превосходно'),
       (2, 7, 'нормально'),
       (3, 7, 'класс!!!');

insert into user_library(id, login, password, locked, account_expired, password_expired, enabled)
values (1, 'admin', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', false, null, null, true);