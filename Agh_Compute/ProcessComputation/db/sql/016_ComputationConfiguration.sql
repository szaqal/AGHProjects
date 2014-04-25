CREATE TABLE computation_configurations(
	unique_id serial PRIMARY KEY,
	configuration_file serial REFERENCES file_contents(unique_id),
	configuration_id varchar(255),
	description varchar(255)
)