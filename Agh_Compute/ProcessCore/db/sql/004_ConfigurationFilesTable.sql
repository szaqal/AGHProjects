CREATE TABLE application_configuration_files(
	unique_id serial PRIMARY KEY,
	kkey varchar(255),
	file_content serial REFERENCES file_contents(unique_id)
);