ALTER TABLE computation_task_inputs ADD COLUMN id VARCHAR(255) UNIQUE;
ALTER TABLE computation_task_outputs ADD COLUMN id VARCHAR(255) UNIQUE;