insert into authorSource(id, fio) values
    (1, 'Толстой Лев Николаевич'),
    (2, 'Дойл Артур Конан');

insert into genreSource(id, name) values
    (1, 'Роман'),
    (2, 'Рассказ'),
    (3, 'Повесть'),
    (4, 'Детектив');

insert into bookSource(id, name, year_edition) values
    (1, 'Война и мир', 1981),
    (2, 'Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе', 1998);

insert into book_author(book_id, author_id) values
    (1, 1),
    (2, 2);

insert into book_genre(book_id, genre_id) values
    (1, 1),
    (2, 2),
    (2, 3),
    (2, 4);

insert into users(id, login, password, fio, locked, account_expired, password_expired, enabled) values
    (1, 'admin', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Администратор', false, null, null, true),
    (2, 'manager_all', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Заведующая библиотекой', false, null, null, true),
    (3, 'manager_foreign', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Заведующая отделом зарубежной литературы', false, null, null, true),
    (4, 'manager_home', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Заведующая отделом отечественной литературы', false, null, null, true);

insert into roles(id, role, description) values
(1, 'ROLE_ADMIN', 'Администратор'),
(2, 'ROLE_FOREIGN_UPDATE', 'Редактирование зарубежных книг'),
(3, 'ROLE_FOREIGN_READ', 'Просмотр зарубежных книг'),
(4, 'ROLE_HOME_UPDATE', 'Редактирование отечественных книг'),
(5, 'ROLE_HOME_READ', 'Просмотр отечественных книг');

insert into users_role(users_id, roles_id) values
(1, 1),
(2, 3),
(2, 5),
(3, 2),
(3, 3),
(4, 4),
(4, 5);