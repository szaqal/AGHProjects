CREATE TABLE computation_log_entries(
	unique_id serial PRIMARY KEY,
	create_time timestamp DEFAULT now(),
	message varchar(255),
	performed_computation serial REFERENCES performed_computations(unique_id)
);