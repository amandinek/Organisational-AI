SET MODE PostgreSQL;
CREATE DATABASE organisationAPI;
\c
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
 dept_Id int PRIMARY KEY auto_increment
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
user_name VARCHAR,
user_position VARCHAR,
 user_role VARCHAR
);
CREATE TABLE IF NOT EXISTS user_in_departements (
 id int PRIMARY KEY auto_increment,
userId INTEGER,
departementsId INTEGER,
);
 CREATE DATABASE organisationAPI_Test WITH TEMPLATE organisationAPI;



