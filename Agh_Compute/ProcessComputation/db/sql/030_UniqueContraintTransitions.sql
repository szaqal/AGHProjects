ALTER TABLE computation_transitions ADD UNIQUE(computation, previous_output, next_input);