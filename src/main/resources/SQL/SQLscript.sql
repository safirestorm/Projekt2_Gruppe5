CREATE DATABASE givmig;

USE givmig;

CREATE TABLE users (username varchar(50) UNIQUE PRIMARY KEY, password varchar(50), firstname varchar(50), lastname varchar(50));

CREATE TABLE wishlists (id int AUTO_INCREMENT PRIMARY KEY,userID varchar(50), name varchar(100), date DATE, description LONGTEXT, CONSTRAINT fk_userID FOREIGN KEY (userID) REFERENCES users(username));

CREATE TABLE wishes (id int AUTO_INCREMENT PRIMARY KEY, wishlistID int, name varchar(200), link varchar(500), description LONGTEXT, CONSTRAINT fk_wishlistID FOREIGN KEY (wishlistID) REFERENCES wishlists(id));