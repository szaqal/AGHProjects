CREATE TABLE computation_packages (
	unique_id serial PRIMARY KEY,
	content serial REFERENCES file_contents(unique_id),
	add_date timestamp,
	title varchar(255)
)