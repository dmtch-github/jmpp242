#USE mydbtest;

#PostgreSQL
CREATE TABLE IF NOT EXISTS userwebs (
  id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(45) NOT NULL,
  last_name CHARACTER VARYING(45) NOT NULL,
  age INTEGER NOT NULL,
  email CHARACTER VARYING(45) NOT NULL,
  role CHARACTER VARYING(45) NOT NULL,
);


INSERT INTO userwebs (name, last_name, age, email, role) VALUES
	('Zaur', 'Tregulov', 33, 'zaur@yandex.ru', 'admin'),
	('Oleg', 'Ivanov', 25, 'oleg@mail.ru', 'user'),
	('Nina', 'Sidorova', 20, 'nina@google.com', 'user');

