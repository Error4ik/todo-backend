CREATE EXTENSION pgcrypto;

CREATE TABLE statistics (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  completed_total INTEGER NOT NULL default 0,
  uncompleted_total INTEGER NOT NULL default 0
);

CREATE TABLE priorities (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(255) NOT NULL default '',
  color VARCHAR(255) NOT NULL default 'white'
);

CREATE TABLE categories (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(255) NOT NULL default '',
  completed_count INTEGER NOT NULL default 0,
  uncompleted_count INTEGER NOT NULL default 0
);

CREATE TABLE tasks (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  title VARCHAR(255) NOT NULL default '',
  completed INTEGER NOT NULL default 0,
  date TIMESTAMP DEFAULT now() NOT NULL,
  priority UUID,
  category UUID,

  FOREIGN KEY (priority) REFERENCES priorities (id),
  FOREIGN KEY (category) REFERENCES categories (id)
);

CREATE OR REPLACE FUNCTION changes_tasks() RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        IF (old.completed = 1) THEN
            UPDATE statistics SET completed_total = completed_total - 1;
            IF (old.category IS NOT NULL) THEN
                UPDATE categories SET completed_count = completed_count - 1 where old.category = id;
            end if;
        ELSE
            UPDATE statistics SET uncompleted_total = uncompleted_total - 1;
            IF (old.category IS NOT NULL) THEN
                UPDATE categories SET uncompleted_count = uncompleted_count - 1 where old.category = id;
            end if;
        end if;
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        IF (old.completed <> new.completed AND new.completed = 1 AND old.category = new.category) THEN
            UPDATE statistics SET completed_total = completed_total + 1, uncompleted_total = uncompleted_total - 1;
            UPDATE categories
            SET completed_count   = completed_count + 1,
                uncompleted_count = uncompleted_count - 1
            where new.category = id;
        ELSEIF (old.completed <> new.completed AND new.completed = 0 AND old.category = new.category) THEN
            UPDATE statistics SET completed_total = completed_total - 1, uncompleted_total = uncompleted_total + 1;
            UPDATE categories
            SET completed_count   = completed_count - 1,
                uncompleted_count = uncompleted_count + 1
            where new.category = id;
        ELSEIF (old.completed <> new.completed AND new.completed = 1 AND old.category IS NULL) THEN
            UPDATE statistics SET completed_total = completed_total + 1, uncompleted_total = uncompleted_total - 1;
        ELSEIF (old.completed <> new.completed AND new.completed = 0 AND old.category IS NULL) THEN
            UPDATE statistics SET completed_total = completed_total - 1, uncompleted_total = uncompleted_total + 1;
        ELSEIF (old.completed = new.completed AND new.completed = 1 AND old.category <> new.category) THEN
            UPDATE categories SET completed_count = completed_count + 1 where new.category = id;
            UPDATE categories SET completed_count = completed_count - 1 where old.category = id;
        ELSEIF (old.completed = new.completed AND new.completed = 0 AND old.category <> new.category) THEN
            UPDATE categories SET uncompleted_count = uncompleted_count + 1 where new.category = id;
            UPDATE categories SET uncompleted_count = uncompleted_count - 1 where old.category = id;
        ELSEIF (old.completed <> new.completed AND new.completed = 1 AND old.category <> new.category) THEN
            UPDATE categories SET completed_count = completed_count + 1 where new.category = id;
            UPDATE categories SET uncompleted_count = uncompleted_count - 1 where old.category = id;
            UPDATE statistics SET completed_total = completed_total + 1, uncompleted_total = uncompleted_total - 1;
        ELSEIF (old.completed <> new.completed AND new.completed = 0 AND old.category <> new.category) THEN
            UPDATE categories SET uncompleted_count = uncompleted_count + 1 where new.category = id;
            UPDATE categories SET completed_count = completed_count - 1 where old.category = id;
            UPDATE statistics SET completed_total = completed_total - 1, uncompleted_total = uncompleted_total + 1;
        end if;
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        IF (new.completed = 1) THEN
            UPDATE statistics SET completed_total = completed_total + 1;
            IF (new.category IS NOT NULL) THEN
                UPDATE categories SET completed_count = completed_count + 1 where new.category = id;
            end if;
        ELSE
            UPDATE statistics SET uncompleted_total = uncompleted_total + 1;
            IF (new.category IS NOT NULL) THEN
                UPDATE categories SET uncompleted_count = uncompleted_count + 1 where new.category = id;
            end if;
        end if;
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER changes
    AFTER INSERT OR UPDATE OR DELETE
    ON tasks
    FOR EACH ROW
EXECUTE PROCEDURE changes_tasks();
