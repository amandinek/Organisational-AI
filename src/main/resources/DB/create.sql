SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departements (
 id int PRIMARY KEY auto_increment,
 dept_name VARCHAR,
 dept_description VARCHAR,
 dept_size VARCHAR
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
title VARCHAR
 body VARCHAR
 dId int PRIMARY KEY auto_increment
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
user_name VARCHAR,
user_position VARCHAR,
 user_role VARCHAR
);

