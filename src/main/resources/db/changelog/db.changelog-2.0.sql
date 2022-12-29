--liquibase formatted sql


--changeset voronin:1
ALTER TABLE categories
    ADD COLUMN created_at TIMESTAMP;

--changeset voronin:2
ALTER TABLE categories
    ADD COLUMN modified_at TIMESTAMP;

--changeset voronin:3
ALTER TABLE categories
    ADD COLUMN created_by VARCHAR(32);

--changeset voronin:4
ALTER TABLE categories
    ADD COLUMN modified_by VARCHAR(32);

--changeset voronin:5
ALTER TABLE priorities
    ADD COLUMN created_at TIMESTAMP;

--changeset voronin:6
ALTER TABLE priorities
    ADD COLUMN modified_at TIMESTAMP;

--changeset voronin:7
ALTER TABLE priorities
    ADD COLUMN created_by VARCHAR(32);

--changeset voronin:8
ALTER TABLE priorities
    ADD COLUMN modified_by VARCHAR(32);

--changeset voronin:9
ALTER TABLE tasks
    ADD COLUMN created_at TIMESTAMP;

--changeset voronin:10
ALTER TABLE tasks
    ADD COLUMN modified_at TIMESTAMP;

--changeset voronin:11
ALTER TABLE tasks
    ADD COLUMN created_by VARCHAR(32);

--changeset voronin:12
ALTER TABLE tasks
    ADD COLUMN modified_by VARCHAR(32);
