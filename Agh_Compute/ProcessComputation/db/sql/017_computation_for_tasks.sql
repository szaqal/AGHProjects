CREATE TABLE computation_for_tasks (
	unique_id serial PRIMARY KEY,
	computation serial REFERENCES computations(unique_id),
	computation_task serial REFERENCES computation_tasks(unique_id)
);

ALTER TABLE computation_tasks DROP COLUMN computation;
