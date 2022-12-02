--liquibase formatted sql


--changeset voronin:1
CREATE
    EXTENSION pgcrypto;

--changeset voronin:2
CREATE TABLE statistics
(
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    completed_total   INTEGER NOT NULL default 0,
    uncompleted_total INTEGER NOT NULL default 0
);

--changeset voronin:3
CREATE TABLE priorities
(
    id    UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL default '',
    color VARCHAR(255) NOT NULL default 'white'
);

--changeset voronin:4
CREATE TABLE categories
(
    id                UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title             VARCHAR(255) NOT NULL default '',
    completed_count   INTEGER      NOT NULL default 0,
    uncompleted_count INTEGER      NOT NULL default 0
);

--changeset voronin:5
CREATE TABLE tasks
(
    id        UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title     VARCHAR(255) NOT NULL default '',
    completed INTEGER      NOT NULL default 0,
    date      DATE,
    priority  UUID,
    category  UUID,

    FOREIGN KEY (priority) REFERENCES priorities (id),
    FOREIGN KEY (category) REFERENCES categories (id)
);
