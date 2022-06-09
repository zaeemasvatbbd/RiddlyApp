USE master;
GO

-- ALTER DATABASE coktailDB SET AUTO_CLOSE OFF

DROP DATABASE IF EXISTS RiddlyDB;

CREATE DATABASE RiddlyDB;
GO

USE RiddlyDB;
GO

CREATE TABLE [User] (
	username VARCHAR(20) PRIMARY KEY ,
	email VARCHAR(100) NOT NULL,
	points INT
);

CREATE TABLE Riddles (
	riddleID INT PRIMARY KEY IDENTITY(1,1),
	question VARCHAR(MAX) NOT NULL,
	answer VARCHAR(50) NOT NULL,
	username VARCHAR(20) NOT NULL,
	FOREIGN KEY (username) REFERENCES [User] (username)
);

CREATE TABLE Answered_Riddles (
	riddleID INT,
	username VARCHAR(20),
	PRIMARY KEY(riddleID, username),
	FOREIGN KEY (riddleID) REFERENCES [Riddles] (riddleID),
	FOREIGN KEY (username) REFERENCES [User] (username)
);


INSERT INTO [User] (username, email, points)
VALUES  ('Amanda', 'amanda@gmail.com', 0),
		('John', 'john@gmail.com', 20),
		('Jack', 'jack@gmail.com',20)

INSERT INTO [Riddles] (question, answer, username)
Values('I’m tall when I’m young, and I’m short when I’m old. What am I?','Candle','Amanda'), 
	  ('The more of this there is, the less you see. What is it?','Darkness','John'), 
	  ('David’s parents have three sons: Snap, Crackle, and what’s the name of the third son?','David','Amanda'), 
	  ('It stalks the countryside with ears that can’t hear. What is it?', 'Corn', 'John'), 
	  ('I am an odd number. Take away a letter and I become even. What number am I?','Seven','John'), 
	  ('If two’s company, and three’s a crowd, what are four and five?','Nine','Amanda')

INSERT INTO [Answered_Riddles] (riddleID, username)
VALUES	(1, 'Jack'),
		(2, 'Jack')
