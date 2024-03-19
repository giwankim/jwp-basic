DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    userId   varchar(12) NOT NULL,
    password varchar(12) NOT NULL,
    name     varchar(20) NOT NULL,
    email    varchar(50),
    PRIMARY KEY (userId)
);

INSERT INTO users VALUES ('admin', 'password', '자바지기', 'admin@slipp.net');
