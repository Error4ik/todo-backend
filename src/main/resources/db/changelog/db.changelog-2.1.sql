--liquibase formatted sql


--changeset voronin:1
CREATE TABLE IF NOT EXISTS revision
(
    id        SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset voronin:2
CREATE TABLE IF NOT EXISTS categories_aud
(
    id                UUID,
    rev               INT REFERENCES revision (id),
    revtype           SMALLINT,
    title             VARCHAR(255) NOT NULL,
    completed_count   INTEGER      NOT NULL,
    uncompleted_count INTEGER      NOT NULL,
    created_at        TIMESTAMP,
    modified_at       TIMESTAMP,
    created_by        VARCHAR(32),
    modified_by       VARCHAR(32)
);

--changeset voronin:3
CREATE TABLE IF NOT EXISTS priorities_aud
(
    id          UUID,
    rev         INT REFERENCES revision (id),
    revtype     SMALLINT,
    title       VARCHAR(255) NOT NULL,
    color       VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    created_by  VARCHAR(32),
    modified_by VARCHAR(32)
);

--changeset voronin:4
CREATE TABLE IF NOT EXISTS tasks_aud
(
    id          UUID,
    rev         INT REFERENCES revision (id),
    revtype     SMALLINT,
    title       VARCHAR(255) NOT NULL,
    completed   INTEGER      NOT NULL,
    date        DATE,
    priority    UUID,
    category    UUID,
    created_at  TIMESTAMP,
    modified_at TIMESTAMP,
    created_by  VARCHAR(32),
    modified_by VARCHAR(32)
);
