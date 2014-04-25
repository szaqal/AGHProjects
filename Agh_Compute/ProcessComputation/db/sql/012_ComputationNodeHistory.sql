CREATE TABLE computation_node_histories(
	unique_id serial PRIMARY KEY,
	node_ip varchar(255),
	arch varchar(255),
	memory_free varchar(255),
	processor_load varchar(255),
	record_date timestamp DEFAULT now()
);