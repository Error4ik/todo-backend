--liquibase formatted sql


--changeset voronin:1
ALTER TABLE categories_aud
    ALTER COLUMN title DROP NOT NULL;
ALTER TABLE categories_aud
    ALTER COLUMN completed_count DROP NOT NULL;
ALTER TABLE categories_aud
    ALTER COLUMN uncompleted_count DROP NOT NULL;

--changeset voronin:2
ALTER TABLE priorities_aud
    ALTER COLUMN title DROP NOT NULL;
ALTER TABLE priorities_aud
    ALTER COLUMN color DROP NOT NULL;

--changeset voronin:3
ALTER TABLE tasks_aud
    ALTER COLUMN title DROP NOT NULL;
ALTER TABLE tasks_aud
    ALTER COLUMN completed DROP NOT NULL;
