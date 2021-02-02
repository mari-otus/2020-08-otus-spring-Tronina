drop table if exists book_author;
drop table if exists book_genre;
drop table if exists comment_book;
drop table if exists book;
drop table if exists genre;
drop table if exists author;
drop table if exists user_library;

------------------------------
-- table: author            --
------------------------------
create table author
(
    id  bigserial
        constraint author_pk
            primary key,
    fio text not null
);

comment on table author is 'Авторы';
comment on column author.fio is 'Фамилия имя отчество';

------------------------------
-- table: genre             --
------------------------------

create table genre
(
    id   bigserial
        constraint genre_pk
            primary key,
    name text not null
);

comment on table genre is 'Жанры';
comment on column genre.name is 'Наименование жанра';

------------------------------
-- table: book              --
------------------------------

create table book
(
    id           bigserial
        constraint book_pk
            primary key,
    name         text not null,
    year_edition int
);

comment on table book is 'Книги';
comment on column book.name is 'Название книги';
comment on column book.year_edition is 'Год издания';

------------------------------
-- table: comment           --
------------------------------

create table comment_book
(
    id      bigserial
        constraint comment_book_pk primary key,
    comment text   not null,
    book_id bigint not null
);

comment on table comment_book is 'Комментарии к книге';
comment on column comment_book.comment is 'Комментарий';
comment on column comment_book.book_id is 'Идентификатор книги';

alter table comment_book
    add constraint comment_book_fk
        foreign key (book_id) references book
            on update cascade on delete cascade;

------------------------------
-- table: book_author       --
------------------------------

create table book_author
(
    book_id   bigint,
    author_id bigint
);

comment on table book_author is 'Связь книг и авторов';
comment on column book_author.book_id is 'Идентификатор книги';
comment on column book_author.author_id is 'Идентификатор автора';

alter table book_author
    add constraint book_fk
        foreign key (book_id) references book
            on update cascade on delete cascade;

alter table book_author
    add constraint author_fk
        foreign key (author_id) references author
            on update cascade on delete cascade;

------------------------------
-- table: book_genre        --
------------------------------

create table book_genre
(
    book_id  bigint,
    genre_id bigint
);

comment on table book_genre is 'Связь книг и жанров';
comment on column book_genre.book_id is 'Идентификатор книги';
comment on column book_genre.genre_id is 'Идентификатор жанра';

alter table book_genre
    add constraint genre_book_fk
        foreign key (book_id) references book
            on update cascade on delete cascade;

alter table book_genre
    add constraint genre_fk
        foreign key (genre_id) references genre
            on update cascade on delete cascade;

alter sequence if exists author_id_seq restart with 10;
alter sequence if exists genre_id_seq restart with 10;
alter sequence if exists book_id_seq restart with 10;
alter sequence if exists comment_book_id_seq restart with 10;

------------------------------
-- table: user_library      --
------------------------------
create table user_library
(
    id               bigserial
        constraint user_library_pk
            primary key,
    login            text    not null,
    password         text    not null,
    locked           boolean not null default false,
    account_expired  timestamp,
    password_expired timestamp,
    enabled          boolean not null default true
);

comment on table user_library is 'Пользователи';
comment on column user_library.login is 'Логин';
comment on column user_library.password is 'Пароль';
comment on column user_library.locked is 'Признак блокировки пользователя: false - не заблокирован, true - заблокирован';
comment on column user_library.account_expired is 'Дата и время окончания срока действия учетной записи пользователя';
comment on column user_library.password_expired is 'Дата и время окончания срока действия пароля пользователя';
comment on column user_library.enabled is 'Признак активности пользователя: false - не активен, true - активен';
