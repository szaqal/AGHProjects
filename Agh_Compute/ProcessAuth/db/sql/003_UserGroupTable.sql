CREATE TABLE User_Groups(
	unique_id serial PRIMARY KEY,
	usr serial REFERENCES Users(unique_id),
	grp serial REFERENCES Groups(unique_id)
)