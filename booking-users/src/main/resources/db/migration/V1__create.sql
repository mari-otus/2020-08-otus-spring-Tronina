drop table if exists users_role;
drop table if exists roles;
drop table if exists users;

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

insert into users(id, login, password, fio, locked, account_expired, password_expired, enabled)
values (1, 'admin', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Администратор Петров', false, null, null,
        true),
       (2, 'user', '$2a$10$E7CswtV8IX36w0iPW//rlu5ptkxvTrOZtVUEIWLQiFOUnQYfe3T42', 'Пользователь Иванов',
        false, null, null, true);

insert into roles(id, role, description)
values (1, 'ROLE_ADMIN', 'Администратор'),
       (2, 'ROLE_USER', 'Пользователь');

insert into users_role(users_id, roles_id)
values (1, 1),
       (2, 2);

alter sequence if exists users_id_seq restart with 10;
alter sequence if exists roles_id_seq restart with 10;