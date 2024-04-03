DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id  varchar(12) NOT NULL,
    password varchar(12) NOT NULL,
    name     varchar(20) NOT NULL,
    email    varchar(50),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS question;

CREATE TABLE question
(
    question_id     bigint auto_increment,
    writer          varchar(30)   NOT NULL,
    title           varchar(50)   NOT NULL,
    contents        varchar(5000) NOT NULL,
    created_date    timestamp     NOT NULL,
    count_of_answers int,
    PRIMARY KEY (question_id)
);

DROP TABLE IF EXISTS answer;

CREATE TABLE answer
(
    answer_id    bigint auto_increment,
    writer       varchar(30)   NOT NULL,
    contents     varchar(5000) NOT NULL,
    created_date timestamp     NOT NULL,
    question_id  bigint        NOT NULL,
    PRIMARY KEY (answer_id)
);
