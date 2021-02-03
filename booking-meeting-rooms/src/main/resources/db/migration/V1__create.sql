drop table if exists bookings;
drop table if exists rooms;
drop table if exists profiles;
drop table if exists users_role;
drop table if exists roles;
drop table if exists users;

------------------------------
-- table: rooms             --
------------------------------
create table rooms
(
    id                  bigserial
        constraint rooms_pk
            primary key,
    room_name           text not null,
    capacity            int  not null,
    has_conditioner     boolean   default false,
    has_videoconference boolean   default false,
    create_date         timestamp default current_timestamp,
    update_date         timestamp,
    delete_date         timestamp
);

comment on table rooms is 'Переговорные комнаты';
comment on column rooms.room_name is 'Название комнаты';
comment on column rooms.capacity is 'Вместимость (кол-во человек)';
comment on column rooms.has_videoconference is 'Возможность проводить видеоконференции';
comment on column rooms.has_conditioner is 'Наличие кондиционера';

------------------------------
-- table: users             --
------------------------------
create table users
(
    id               bigserial
        constraint users_pk
            primary key,
    login            text    not null,
    password         text    not null,
    fio              text    not null,
    locked           boolean not null default false,
    account_expired  timestamp,
    password_expired timestamp,
    enabled          boolean not null default true
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
-- table: bookings          --
------------------------------
create table bookings
(
    id          bigserial
        constraint bookings_pk
            primary key,
    room_id     int       not null,
    user_id     int       not null,
    begin_date  timestamp not null,
    end_date    timestamp not null,
    status      text      not null,
    create_date timestamp default current_timestamp,
    update_date timestamp,
    delete_date timestamp
);

alter table if exists bookings
    add constraint bookings_room_fk
        foreign key (room_id)
            references rooms;

alter table if exists bookings
    add constraint bookings_user_fk
        foreign key (user_id)
            references users;

------------------------------
-- table: roles             --
------------------------------
create table roles
(
    id          bigserial
        constraint roles_pk
            primary key,
    role        text not null,
    description text
);

------------------------------
-- table: users_role        --
------------------------------
create table users_role
(
    id       bigserial
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

------------------------------
-- table: profiles          --
------------------------------

create table profiles
(
    id              bigserial
        constraint profiles_pk
            primary key,
    user_id         bigint not null,
    email           text,
    mobile_phone    text,
    is_email_notify boolean default false,
    is_phone_notify boolean default false
);

comment on table profiles is 'Профиль пользователя';
comment on column profiles.email is 'email пользователя';
comment on column profiles.mobile_phone is 'Номер сотового телефона пользователя';
comment on column profiles.is_email_notify is 'Оповещение по email';
comment on column profiles.is_phone_notify is 'Оповещение по sms';

alter table profiles
    add constraint profiles_fk
        foreign key (user_id) references users
            on update cascade on delete cascade;

insert into users(id, login, password, fio, locked, account_expired, password_expired, enabled)
values (1, 'admin', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Администратор', false, null, null,
        true),
       (2, 'manager_all', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Заведующая библиотекой',
        false, null, null, true),
       (3, 'manager_foreign', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42',
        'Заведующая отделом зарубежной литературы', false, null, null, true),
       (4, 'manager_home', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42',
        'Заведующая отделом отечественной литературы', false, null, null, true);

insert into roles(id, role, description)
values (1, 'ROLE_ADMIN', 'Администратор'),
       (2, 'ROLE_USER', 'Редактирование зарубежных книг');

insert into users_role(users_id, roles_id)
values (1, 1),
       (2, 2);

alter sequence if exists rooms_id_seq restart with 10;
alter sequence if exists profiles_id_seq restart with 10;
alter sequence if exists users_id_seq restart with 10;
alter sequence if exists roles_id_seq restart with 10;