CREATE TABLE Login_Entries(
	unique_id serial PRIMARY KEY,
	login_from varchar(255),
	login_date timestamp,
	logged_user serial REFERENCES Users(unique_id) 
)