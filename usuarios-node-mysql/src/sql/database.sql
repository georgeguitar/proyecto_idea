//CREATE DATABASE g3gcrh40646vhxdn;

USE g3gcrh40646vhxdn;

SHOW TABLES;

/*CREATE TABLE user (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  login VARCHAR(100),
  name TEXT,
  data_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/
DESCRIBE users;

INSERT INTO users (login, name) values ('clau@hotmail', 'claudia');

SELECT * FROM users;
