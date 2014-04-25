CREATE TABLE configuration_items (
	unique_id serial PRIMARY KEY,
	config_key varchar(255),
	config_value varchar(255)
)