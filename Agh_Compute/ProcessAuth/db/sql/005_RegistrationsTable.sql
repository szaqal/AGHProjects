CREATE TABLE Registrations (
	unique_id serial PRIMARY KEY,
	registration_date timestamp,
	complete_registration timestamp,
	usr serial REFERENCES Users(unique_id)
);