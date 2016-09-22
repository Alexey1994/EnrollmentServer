CREATE DATABASE Enrollment;


CREATE TABLE sql7136706.Exams 
(
	id       LONG,
	name     VARCHAR(255),
    prefix   VARCHAR(10),
    language VARCHAR(100),
    type     VARCHAR(4)
);

CREATE TABLE sql7136706.Forms 
(
	id        LONG,
    num_tests long,
    num_exams long
);

CREATE TABLE sql7136706.Links
(
	id             LONG,
	first_prefix   VARCHAR(10),
    second_prefix  VARCHAR(10),
	third_prefix   VARCHAR(10)
);

CREATE TABLE sql7136706.Specialities
(
	id          LONG,
	specialty   VARCHAR(255),
    score       LONG,
    competition VARCHAR(255),
    university  VARCHAR(255),
    faculty     VARCHAR(255)
);


INSERT INTO sql7136706.Exams VALUES
(
	1,
	'Физика',
    'phys',
    'ru',
    'exam'
);

INSERT INTO sql7136706.Forms VALUES
(
	1,
    0,
    2
);

INSERT INTO sql7136706.Specialities VALUES
(
	1,
    'Математика и информационные технологии',
    '289',
    'Общий',
    'Белорусский государственный университет',
    'Механико-математический факультет'
);


DELETE FROM sql7136706.Exams;
DELETE FROM sql7136706.Forms;
DELETE FROM sql7136706.Links;
DELETE FROM sql7136706.Specialities;


DROP TABLE sql7136706.Exams;
DROP TABLE sql7136706.Links;
DROP TABLE sql7136706.Forms;
DROP TABLE sql7136706.Specialities;


UPDATE sql7136706.Exams
SET name='Математика',
    prefix='math',
    type='test'
WHERE id=1;


SELECT * FROM sql7136706.Exams;
SELECT * FROM sql7136706.Forms;
SELECT * FROM sql7136706.Links;
SELECT * FROM sql7136706.Specialities;


SELECT distinct prefix FROM sql7136706.Exams;