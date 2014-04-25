CREATE TABLE computation_transitions (
	unique_id serial PRIMARY KEY,
	start_task_id varchar(255),
	end_task_id varchar(255),
	computation serial REFERENCES Computations(unique_id)
)