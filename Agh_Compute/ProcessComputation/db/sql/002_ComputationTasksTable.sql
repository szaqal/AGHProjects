CREATE TABLE computation_tasks(
	unique_id serial PRIMARY KEY,
	task_id varchar(255),
	task_name varchar(255),
	performer varchar(255),
	description varchar(255),
	computation serial REFERENCES Computations(unique_id)
)
