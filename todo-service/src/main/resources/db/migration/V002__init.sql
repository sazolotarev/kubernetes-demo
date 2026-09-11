CREATE TABLE IF NOT EXISTS todo_lists (
    id UUID PRIMARY KEY NOT NULL DEFAULT uuidv7(),
    created_at TIMESTAMPTZ NOT NULL,
    user_id VARCHAR NOT NULL,
    name VARCHAR UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS todo_items (
    id UUID PRIMARY KEY NOT NULL DEFAULT uuidv7(),
    created_at TIMESTAMPTZ NOT NULL,
    user_id VARCHAR NOT NULL,
    todo_list_id UUID NOT NULL REFERENCES todo_lists(id),
    description VARCHAR NOT NULL
);
