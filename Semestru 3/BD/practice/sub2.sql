--Restaurant Samsara

--Restaurantul Samsara doreste imbunatatirea aplicatie pentru comenzi. O comanda (adresa de livrare, data) este plasat de un client (nume, nr_telefon) si contine mai multe preparate. 
--Un preparat poate face parte din mai multe comenzi. Un preparat (denumire, cantitate, pret) are mai multe ingrediente. Un ingredient (denumire, calorii) poate fi folosit in mai multe preparate.

--1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele (4p)

--2. Un ingredient devine interzis in toata lumea. Samsara doreste sa para ca nu l-a folosit niciodata. 
--Creati o procedura stocata care sterge toate comenzile care contin un ingredient dat ca parametru prin nume. Procedura va returna numarul de comenzi sterse. (3p)

--3. Scrieti un view care afiseaza toate comenzile pentru clientul cu numele "Bogdan Ioan" prin data comenzii si suma pe care a achitat-o clientul pentru acea comanda. (2p)

CREATE DATABASE restaurant;
Go

USE restaurant;
Go

CREATE TABLE clienti (
	id_cl INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50) NOT NULL, 
	nr_telefon VARCHAR(11)
)

CREATE TABLE comenzi (
	id_c INT PRIMARY KEY IDENTITY (1, 1),
	id_client INT NOT NULL FOREIGN KEY REFERENCES clienti(id_cl),
	data_c DATE NOT NULL
)

CREATE TABLE preparate (
	id_p INT PRIMARY KEY IDENTITY (1, 1),
	denumire VARCHAR (50) NOT NULL,
	cantitate INT,
	pret MONEY
)

CREATE TABLE ingrediente (
	id_i INT PRIMARY KEY IDENTITY (1, 1),
	denumire VARCHAR (50) NOT NULL,
	calorii INT
)

CREATE TABLE comenzi_preparate (
	id INT PRIMARY KEY IDENTITY (1, 1),
	id_comanda INT NOT NULL FOREIGN KEY REFERENCES comenzi(id_c),
	id_preparat INT NOT NULL FOREIGN KEY REFERENCES preparate(id_p)
)

CREATE TABLE preparate_ingrediente (
	id INT PRIMARY KEY IDENTITY (1, 1),
	id_preparat INT NOT NULL FOREIGN KEY REFERENCES preparate(id_p),
	id_ingredient INT NOT NULL FOREIGN KEY REFERENCES ingrediente(id_i)
)

Go

CREATE TABLE ids_to_delete ( id INT PRIMARY KEY)
Go

CREATE OR ALTER PROCEDURE usp_sterge_comenzi_ingredient (@nume_ingredient VARCHAR(50), @numar INT OUTPUT)
	AS
		BEGIN
			DECLARE @id INT
			SELECT @id = MAX(id_i) FROM ingrediente WHERE denumire = @nume_ingredient

			INSERT INTO ids_to_delete 
				SELECT DISTINCT id_c FROM comenzi C
				INNER JOIN comenzi_preparate CP ON C.id_c = CP.id_comanda
				INNER JOIN preparate_ingrediente P ON P.id_preparat = CP.id_preparat
				WHERE P.id_ingredient = @id

			DELETE C 
			FROM comenzi_preparate C
			INNER JOIN ids_to_delete I ON I.id = C.id_comanda
			
			DELETE C
			FROM comenzi C
			INNER JOIN ids_to_delete I ON I.id = C.id_c

			SELECT @numar = COUNT(*) FROM ids_to_delete
			DELETE FROM ids_to_delete
		END
	Go

DECLARE @numar INT
EXEC usp_sterge_comenzi_ingredient @nume_ingredient = 'Ou', @numar = @numar OUTPUT

PRINT @numar

SELECT * FROM clienti

Go

CREATE OR ALTER VIEW vw_client_radu_andrei 
	AS
		SELECT CO.id_c, CO.data_c, SUM(P.pret) AS suma FROM comenzi CO
		INNER JOIN clienti CL ON Cl.id_cl = CO.id_c
		INNER JOIN comenzi_preparate CP ON CP.id_comanda = CO.id_c
		INNER JOIN preparate P ON P.id_p = CP.id_preparat
		WHERE CL.nume = 'Radu Andrei'
		GROUP BY CO.data_c, CO.id_c
	Go

SELECT * FROM vw_client_radu_andrei