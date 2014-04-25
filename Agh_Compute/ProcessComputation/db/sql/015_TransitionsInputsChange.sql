ALTER TABLE computation_transitions DROP COLUMN start_task_id;
ALTER TABLE computation_transitions DROP COLUMN end_task_id;
ALTER TABLE computation_transitions ADD COLUMN previous_output serial;
ALTER TABLE computation_transitions ADD COLUMN next_input serial;
ALTER TABLE computation_transitions ADD CONSTRAINT fk_inputs FOREIGN KEY (next_input) REFERENCES computation_task_inputs(unique_id);
ALTER TABLE computation_transitions ADD CONSTRAINT fk_outputs FOREIGN KEY (previous_output) REFERENCES computation_task_outputs(unique_id);