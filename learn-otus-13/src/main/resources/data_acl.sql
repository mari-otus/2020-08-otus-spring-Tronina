INSERT INTO acl_sid (id, principal, sid) VALUES
(1, false, 'ROLE_ADMIN'),
(2, false, 'ROLE_FOREIGN_UPDATE'),
(3, false, 'ROLE_FOREIGN_READ'),
(4, false, 'ROLE_HOME_UPDATE'),
(5, false, 'ROLE_HOME_READ');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.spring.domain.Book');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, '1', NULL, 1, false),
(2, 1, '2', NULL, 1, false);

INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
-- bookSource: 'Война и мир', sid: 'ROLE_ADMIN'
(1, 1, 1, 1, true, true, true),
(1, 5, 1, 5, true, true, true),
-- bookSource: 'Война и мир', sid: 'ROLE_HOME_UPDATE'
(1, 6, 4, 1, true, true, true),
(1, 7, 4, 2, true, true, true),
(1, 8, 4, 3, true, true, true),
(1, 9, 4, 4, true, true, true),
-- bookSource: 'Война и мир', sid: 'ROLE_HOME_READ'
(1, 10, 5, 1, true, true, true),

-- bookSource: 'Шерлок Холмс', sid: 'ROLE_ADMIN'
(2, 1, 1, 1, true, true, true),
(2, 5, 1, 5, true, true, true),
-- bookSource: 'Шерлок Холмс', sid: 'ROLE_FOREIGN_UPDATE'
(2, 6, 2, 1, true, true, true),
(2, 7, 2, 2, true, true, true),
(2, 8, 2, 3, true, true, true),
(2, 9, 2, 4, true, true, true),
-- bookSource: 'Шерлок Холмс', sid: 'ROLE_FOREIGN_READ'
(2, 10, 3, 1, true, true, true);
