CREATE DATABASE	practic

USE practic;
Go

CREATE TABLE cetateni (
	id INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50),
	prenume VARCHAR(50),
	cnp VARCHAR(13)
)

CREATE TABLE sectii (
	id INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50)
)

CREATE TABLE cetateni_sectii (
	id_cetatean INT UNIQUE FOREIGN KEY REFERENCES cetateni(id),
	id_sectie INT FOREIGN KEY REFERENCES sectii(id),
	PRIMARY KEY (id_cetatean, id_sectie)
)

CREATE TABLE voturi (
	id_cetatean INT FOREIGN KEY REFERENCES cetateni(id),
	ora DATETIME,
	lista_suplimentara BIT,
	PRIMARY KEY (id_cetatean)
)

CREATE TABLE firme (
	id INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50),
	cif VARCHAR(10)
)

CREATE TABLE livrari_mancare (
	id_livrare INT PRIMARY KEY IDENTITY (1, 1),
	id_sectie INT FOREIGN KEY REFERENCES sectii(id),
	id_firma INT FOREIGN KEY REFERENCES firme(id)
)


	
	select * from voturi