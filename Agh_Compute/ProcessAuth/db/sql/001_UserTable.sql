CREATE TABLE Users(
	unique_id serial PRIMARY KEY,
 	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL
)