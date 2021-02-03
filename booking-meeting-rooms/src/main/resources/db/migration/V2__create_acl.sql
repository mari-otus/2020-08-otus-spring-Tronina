drop table if exists acl_entry;
drop table if exists acl_object_identity;
drop table if exists acl_class;
drop table if exists acl_sid;

create table if not exists acl_sid
(
    id        bigserial
        constraint acl_sid_pk primary key,
    principal boolean,
    sid       varchar(100) not null,
    constraint acl_sid_uk unique (sid, principal)
);

comment on table acl_sid is 'Идентификатор sid';
comment on column acl_sid.id is 'Идентификатор sid';
comment on column acl_sid.principal is 'Признак того, является sid именем аутентифицированного пользователя или ролью';
comment on column acl_sid.sid is 'Имя пользователя или роль';

create table if not exists acl_class
(
    id    bigserial
        constraint acl_class_pk primary key,
    class varchar(255) not null,
    constraint acl_class_uk unique (class)
);

comment on table acl_class is 'Классы';
comment on column acl_class.id is 'Идентификатор';
comment on column acl_class.class is 'Путь к классу';

create table if not exists acl_object_identity
(
    id                 bigserial
        constraint acl_object_identity_pk primary key,
    object_id_class    bigint  not null,
    object_id_identity varchar(50) not null,
    owner_sid          bigint default null,
    parent_object      bigint default null,
    entries_inheriting boolean not null,
    constraint acl_object_identity_uk unique (object_id_class, object_id_identity)
);

comment on table acl_object_identity is 'Экземпляры классов';
comment on column acl_object_identity.id is 'Идентификатор';
comment on column acl_object_identity.object_id_class is 'Идентификатор класса';
comment on column acl_object_identity.object_id_identity is 'Идентификатор экземпляра';
comment on column acl_object_identity.owner_sid is 'Идентификатор sid';
comment on column acl_object_identity.parent_object is 'Родитель';
comment on column acl_object_identity.entries_inheriting is 'Признак того, разрешается ли наследовать ACL от родителя';

create table if not exists acl_entry
(
    id                  bigserial
        constraint acl_entry_pk primary key,
    acl_object_identity bigint  not null,
    ace_order           int     not null,
    sid                 bigint  not null,
    mask                int     not null,
    granting            boolean not null,
    audit_success       boolean not null,
    audit_failure       boolean not null,
    constraint acl_entry_uk unique (acl_object_identity, ace_order)
);

comment on table acl_entry is 'Разрешения';
comment on column acl_entry.id is 'Идентификатор';
comment on column acl_entry.acl_object_identity is 'Идентификатор экземпляра класса';
comment on column acl_entry.ace_order is 'Идентификатор';
comment on column acl_entry.sid is 'Идентификатор sid';
comment on column acl_entry.mask is 'Битовая маска разрешения';
comment on column acl_entry.granting is 'Признак того, разрешение предоставления или отклонения';
comment on column acl_entry.audit_success is 'Признак того, будет ли проводится аудит успешной авторизации';
comment on column acl_entry.audit_failure is 'Признак того, будет ли проводится аудит неуспешной авторизации';

alter table acl_entry
    add constraint acl_entry_object_identity_fk
        foreign key (acl_object_identity) references acl_object_identity;

alter table acl_entry
    add constraint acl_entry_sid_fk
        foreign key (sid) references acl_sid;

alter table acl_object_identity
    add constraint acl_object_identity_parent_object_fk
        foreign key (parent_object) references acl_object_identity;

alter table acl_object_identity
    add constraint acl_object_identity_object_id_class_fk
        foreign key (object_id_class) references acl_class;

alter table acl_object_identity
    add constraint acl_object_identity_owner_sid_fk
        foreign key (owner_sid) references acl_sid;

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, false, 'ROLE_ADMIN'),
(2, false, 'ROLE_USER');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.spring.domain.Room'),
(2, 'ru.otus.spring.domain.Profile');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, '1', NULL, 1, false),
(2, 1, '2', NULL, 1, false);

INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
-- bookSource: 'Война и мир', sid: 'ROLE_ADMIN'
(1, 1, 1, 1, true, true, true),
(1, 5, 1, 5, true, true, true),
-- bookSource: 'Шерлок Холмс', sid: 'ROLE_ADMIN'
(2, 1, 1, 1, true, true, true),
(2, 5, 1, 5, true, true, true),
-- bookSource: 'Шерлок Холмс', sid: 'ROLE_FOREIGN_UPDATE'
(2, 6, 2, 1, true, true, true),
(2, 7, 2, 2, true, true, true),
(2, 8, 2, 3, true, true, true),
(2, 9, 2, 4, true, true, true);
