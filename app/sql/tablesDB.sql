CREATE TABLE site_user (
	id SERIAL PRIMARY KEY,
	username VARCHAR ( 50 ) UNIQUE NOT NULL,
	password VARCHAR ( 50 ) NOT NULL,
	email VARCHAR ( 255 ) UNIQUE NOT NULL,
	role varchar ( 50 ) NOT NULL
);

INSERT INTO site_user VALUES (DEFAULT, 'admin', 'admin', 'admin@yahoo.com', 'ADMIN');