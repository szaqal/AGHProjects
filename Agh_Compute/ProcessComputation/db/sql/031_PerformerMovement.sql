ALTER TABLE computation_for_tasks ADD COLUMN performer varchar(255) NOT NULL;
ALTER TABLE computation_tasks DROP COLUMN performer;