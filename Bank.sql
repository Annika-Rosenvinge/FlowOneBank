CREATE DATABASE IF NOT EXISTS ebberodbank;
USE ebberodbank; 

CREATE TABLE IF NOT EXISTS Customer(
	CusNumber int not null auto_increment primary key,
	CusName varchar (255) not null,
    CusPhone int not null,
    CusAdress varchar (255) not null
);

CREATE TABLE IF NOT EXISTS CusAccount(
	AccountNumber int not null auto_increment primary key,
	MoneyInAccount int not null default '0',
    CusNumber int not null,
    CusName varchar (255) not null
);

CREATE TABLE IF NOT EXISTS TransactionHistory (
    CusNumber int not null auto_increment primary key,
    CusName varchar (255) not null,
    TransactionHis varchar (255) not null
);

CREATE TABLE IF NOT EXISTS EmployeeAccount(
    EmID int not null auto_increment primary key,
    EmName varchar (255) not null,
    ePincode int not null
);

/* 3 temporary medarbejdere */
INSERT INTO `employeeaccount` VALUES (1,'Kurtverner',5534),(2,'Yvonne',4439),(3,'Niels',6478);