CREATE TABLE validation_schemas (
	unique_id serial PRIMARY KEY,
	file_content serial REFERENCES file_contents(unique_id),
	title varchar(255)
)