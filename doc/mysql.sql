USE mydbtest;

CREATE TABLE IF NOT EXISTS mydbtest.userwebs (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  age INT(3) NOT NULL,
  email CHARACTER VARYING(45) NOT NULL,
  role CHARACTER VARYING(45) NOT NULL,
  PRIMARY KEY (id), UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
);

INSERT INTO mydbtest.userwebs (name, last_name, age, email, role)
VALUES
	('Zaur', 'Tregulov', 33, 'zaur@yandex.ru', 'admin'),
	('Oleg', 'Ivanov', 25, 'oleg@mail.ru', 'user'),
	('Nina', 'Sidorova', 20, 'nina@google.com', 'user');

