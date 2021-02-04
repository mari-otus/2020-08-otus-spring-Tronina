drop table if exists bookings;
drop table if exists rooms;
drop table if exists profiles;

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
-- table: bookings          --
------------------------------
create table bookings
(
    id          bigserial
        constraint bookings_pk
            primary key,
    room_id     int       not null,
    login       text      not null,
    begin_date  timestamp not null,
    end_date    timestamp not null,
    create_date timestamp default current_timestamp,
    update_date timestamp,
    delete_date timestamp
);

alter table if exists bookings
    add constraint bookings_room_fk
        foreign key (room_id)
            references rooms;

------------------------------
-- table: profiles          --
------------------------------

create table profiles
(
    id              bigserial
        constraint profiles_pk
            primary key,
    login           text not null,
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

alter sequence if exists rooms_id_seq restart with 10;
alter sequence if exists profiles_id_seq restart with 10;