CREATE TABLE xsl_transforms(
	unique_id serial PRIMARY KEY,
	title varchar(255),
	file_content serial REFERENCES file_contents(unique_id)
)