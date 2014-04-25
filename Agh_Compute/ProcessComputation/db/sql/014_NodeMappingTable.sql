CREATE TABLE node_mappings(
	unique_id serial PRIMARY KEY,
	node varchar(255),
	owner_id integer,
	mapping varchar(255),
	UNIQUE (owner_id, mapping),
	UNIQUE (owner_id, node)
);