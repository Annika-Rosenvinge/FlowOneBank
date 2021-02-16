CREATE DATABASE Bank;
CREATE TABLE Customer(
	CusNumber int not null auto_increment primary key,
	CusName varchar (255) not null,
    CusPhone int not null,
    CusAdress varchar (255) not null
);

CREATE TABLE CusAccount(
	AccountNumber int not null auto_increment primary key,
	MoneyInAccount int not null default '0',
    CusNumber int not null,
    CusName varchar (255) not null
)
