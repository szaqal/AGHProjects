CREATE TABLE computation_task_outputs(
	unique_id serial PRIMARY KEY,
	name varchar(255),
	computation_task serial REFERENCES computation_tasks(unique_id)
)