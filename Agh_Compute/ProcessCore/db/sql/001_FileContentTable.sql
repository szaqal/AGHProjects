CREATE TABLE file_contents(
	unique_id serial PRIMARY KEY,
	file_name varchar(255),
	mime_type varchar(255),
	content oid
)