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
