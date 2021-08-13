DROP TABLE IF EXISTS user;
CREATE TABLE user as SELECT * FROM CSVREAD('classpath:user.csv');