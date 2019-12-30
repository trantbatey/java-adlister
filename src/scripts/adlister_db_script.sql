/*
    Script to create the adlister_db database.
 */

-- if it does not exist, create the 'test' database
CREATE DATABASE IF NOT EXISTS adlister_db;

use adlister_db;

-- Drop the 'users' table if it exists
DROP TABLE IF EXISTS users;

-- Create the 'users' table
CREATE TABLE users (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    email VARCHAR(80),
    password VARCHAR(40),
    PRIMARY KEY (id),
    CONSTRAINT uc_users UNIQUE (username, email)
);


-- Drop the 'ads' table if it exists
DROP TABLE IF EXISTS ads;

-- Create the 'ads' table
CREATE TABLE ads (
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    title VARCHAR(40) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT uc_users UNIQUE (user_id, title)
);

INSERT INTO users (username, email, password)
    VALUES ('adl_user1', 'us1@codeup.com', 'user1'),
           ('adl_user2', 'us2@yahoo.com', 'user2'),
           ('adl_user3', 'us3@aol.com', 'user3'),
           ('adl_user4', 'us4@gmail.com', 'user4');
