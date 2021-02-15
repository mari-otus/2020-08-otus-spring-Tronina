drop table if exists book_author;
drop table if exists book_genre;
drop table if exists comment_book;
drop table if exists bookSource;
drop table if exists genreSource;
drop table if exists authorSource;
drop table if exists users_role;
drop table if exists roles;
drop table if exists users;

------------------------------
-- table: authorSource            --
------------------------------
create table authorSource
(
    id bigserial
        constraint author_pk
            primary key,
    fio text not null
);

comment on table authorSource is 'Авторы';
comment on column authorSource.fio is 'Фамилия имя отчество';

------------------------------
-- table: genreSource             --
------------------------------

create table genreSource
(
    id bigserial
        constraint genre_pk
            primary key,
    name text not null
);

comment on table genreSource is 'Жанры';
comment on column genreSource.name is 'Наименование жанра';

------------------------------
-- table: bookSource              --
------------------------------

create table bookSource
(
	id bigserial
		constraint book_pk
			primary key,
	name text not null,
	year_edition int
);

comment on table bookSource is 'Книги';
comment on column bookSource.name is 'Название книги';
comment on column bookSource.year_edition is 'Год издания';

------------------------------
-- table: comment           --
------------------------------

create table comment_book (
  id bigserial constraint comment_book_pk primary key,
  comment text not null,
  book_id bigint not null
);

comment on table comment_book is 'Комментарии к книге';
comment on column comment_book.comment is 'Комментарий';
comment on column comment_book.book_id is 'Идентификатор книги';

alter table comment_book
    add constraint comment_book_fk
        foreign key (book_id) references bookSource
            on update cascade on delete cascade;

------------------------------
-- table: book_author       --
------------------------------

create table book_author
(
    book_id bigint,
    author_id bigint
);

comment on table book_author is 'Связь книг и авторов';
comment on column book_author.book_id is 'Идентификатор книги';
comment on column book_author.author_id is 'Идентификатор автора';

alter table book_author
    add constraint book_fk
        foreign key (book_id) references bookSource
            on update cascade on delete cascade;

alter table book_author
    add constraint author_fk
        foreign key (author_id) references authorSource
            on update cascade on delete cascade;

------------------------------
-- table: book_genre        --
------------------------------

create table book_genre
(
    book_id bigint,
    genre_id bigint
);

comment on table book_genre is 'Связь книг и жанров';
comment on column book_genre.book_id is 'Идентификатор книги';
comment on column book_genre.genre_id is 'Идентификатор жанра';

alter table book_genre
    add constraint genre_book_fk
        foreign key (book_id) references bookSource
            on update cascade on delete cascade;

alter table book_genre
    add constraint genre_fk
        foreign key (genre_id) references genreSource
            on update cascade on delete cascade;

alter sequence if exists author_id_seq restart with 10;
alter sequence if exists genre_id_seq restart with 10;
alter sequence if exists book_id_seq restart with 10;
alter sequence if exists comment_book_id_seq restart with 10;

------------------------------
-- table: users             --
------------------------------
create table users
(
    id bigserial
        constraint users_pk
            primary key,
    login text not null,
    password text not null,
    fio text not null,
    locked boolean not null default false,
    account_expired timestamp,
    password_expired timestamp,
    enabled boolean not null default true
);

comment on table users is 'Пользователи';
comment on column users.login is 'Логин';
comment on column users.password is 'Пароль';
comment on column users.fio is 'ФИО';
comment on column users.locked is 'Признак блокировки пользователя: false - не заблокирован, true - заблокирован';
comment on column users.account_expired is 'Дата и время окончания срока действия учетной записи пользователя';
comment on column users.password_expired is 'Дата и время окончания срока действия пароля пользователя';
comment on column users.enabled is 'Признак активности пользователя: false - не активен, true - активен';

------------------------------
-- table: roles             --
------------------------------
create table roles
(
    id bigserial
        constraint roles_pk
            primary key,
    role text not null,
    description text
);

------------------------------
-- table: users_role        --
------------------------------
create table users_role
(
    id bigserial
        constraint users_role_pk
            primary key,
    users_id bigint not null,
    roles_id bigint not null
);

alter table users_role
    add constraint users_role_users_fk
        foreign key (users_id) references users
            on update cascade on delete cascade;

alter table users_role
    add constraint users_role_roles_fk
        foreign key (roles_id) references roles
            on update cascade on delete cascade;