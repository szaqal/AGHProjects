ALTER TABLE computation_task_inputs ADD COLUMN value_name VARCHAR(255) NOT NULL;
ALTER TABLE computation_task_outputs ADD COLUMN value_name VARCHAR(255) NOT NULL;