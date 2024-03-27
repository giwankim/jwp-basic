DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id  varchar(12) NOT NULL,
    password varchar(12) NOT NULL,
    name     varchar(20) NOT NULL,
    email    varchar(50),
    PRIMARY KEY (user_id)
);
