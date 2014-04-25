CREATE TABLE computation_settings(
	unique_id serial PRIMARY KEY,
	name varchar(255),
	val varchar(255),
	computation serial REFERENCES Computations(unique_id)
);