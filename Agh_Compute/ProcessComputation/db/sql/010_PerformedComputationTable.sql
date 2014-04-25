CREATE TABLE performed_computations(
	unique_id serial PRIMARY KEY,
	start_date timestamp,
	end_date timestamp,
	computation serial REFERENCES computations(unique_id)
);