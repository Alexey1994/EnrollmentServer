CREATE DATABASE Enrollment;

/*
CREATE TABLE Enrollment.Users 
(
	login    VARCHAR(255),
    password VARCHAR(255),
    role     VARCHAR(5)
);*/

CREATE TABLE Enrollment.Exams 
(
	name     VARCHAR(255),
    prefix   VARCHAR(10),
    language VARCHAR(100),
    type     VARCHAR(4)
    /*region  VARCHAR(255)*/
);

CREATE TABLE Enrollment.Forms 
(
    num_tests long,
    num_exams long
);

CREATE TABLE Enrollment.Links 
(
	prefix  VARCHAR(10),
    num_CT   long,
    num_exam long
);

CREATE TABLE Enrollment.Specialities 
(
	specialty   VARCHAR(255),
    score       VARCHAR(255),
    competition VARCHAR(255),
    university  VARCHAR(255),
    faculty     VARCHAR(255)
);


INSERT INTO Enrollment.Users VALUES
(
	'Admin',
    '123',
    'admin'
);

INSERT INTO Enrollment.Exams VALUES
(
	'Физика',
    'phys',
    'ru',
    'exam'
);

INSERT INTO Enrollment.Forms VALUES
(
    0,
    2
);

INSERT INTO Enrollment.Specialities VALUES
(
    'Математика и информационные технологии',
    '289',
    'Общий',
    'Белорусский государственный университет',
    'Механико-математический факультет'
);


/*DELETE FROM Enrollment.Users;*/
DELETE FROM Enrollment.Exams;
DELETE FROM Enrollment.Forms;
DELETE FROM Enrollment.Specialities;


DROP TABLE Enrollment.Users;
DROP TABLE Enrollment.Exams;
DROP TABLE Enrollment.Forms;


UPDATE Enrollment.Exams
SET name='Математика',
    prefix='math',
    type='test'
WHERE id=1;


/*SELECT * FROM Enrollment.Users;*/
SELECT * FROM Enrollment.Exams;
SELECT * FROM Enrollment.Forms;
SELECT * FROM Enrollment.Specialities;