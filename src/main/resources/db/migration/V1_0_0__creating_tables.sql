CREATE TABLE role_table
(
    id   SERIAL8 PRIMARY KEY NOT NULL,
    name VARCHAR(20)         NOT NULL
);

CREATE TABLE user_table
(
    id       SERIAL8 PRIMARY KEY NOT NULL,
    login    VARCHAR(50)         NOT NULL,
    password VARCHAR(500),
    role_id  BIGINT
);

ALTER TABLE user_table
    ADD CONSTRAINT user_table_role_table_id_fk
        FOREIGN KEY (role_id)
            references role_table (id);

CREATE UNIQUE INDEX user_table_login_uindex
    ON user_table (login);

INSERT INTO role_table(name) values ('ROLE_ADMIN');
INSERT INTO role_table(name) values ('ROLE_USER');
