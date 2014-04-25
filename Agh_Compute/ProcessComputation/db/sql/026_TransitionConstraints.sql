ALTER TABLE computation_transitions ALTER COLUMN previous_output SET NOT NULL;
ALTER TABLE computation_transitions ALTER COLUMN next_input SET NOT NULL;