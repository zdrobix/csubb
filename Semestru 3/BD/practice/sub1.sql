--Campionat de Table

--La firma Cloudflight, pentru a mentine oamenii fericiti fara a mari salariile, se organizeaza un campionat de table.
--Angajatii (nume, nr_contract) joaca meciuri cu alti angajati. Un meci se joaca intr-o data, pe o anume masa (numar, culoare) si trebuie sa fie disputat de 2 participanti. 
--Castigatorul meciului va trece mai departe in camptionat. Pentru fiecare meci se vor salva mutarile participantilor. O mutare are valoare zaruri (2 zaruri - valoare de la 1 la 6), 
--piesa_mutata_de_la, piesa_mutata_la. Meciurile pot vi vizionate de mai multi spectatori. Specatorii vor fi tot angajati.


--1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele (4p)

--2. Scrieti o procedura stocata care calculeaza premiul unui participant dat ca parametru. 
--Pentru fiecare meci castigat, angajatul va primi 100 de puncte de beneficii. 
--Pentru fiecare spectator la meciurile angajatului, va primi 10 puncte de beneficii.
--Pentru fiecare meci pe care angajatul NU l-a vazut, i se vor scadea 10 puncte de beneficii.
--Premiul nu poate fi mai mic decat 0! (3p)

--3. Scrieti un view care calculeaza cate duble a dat jucatorul cu nr_contract "CLF3215" in tot campionatul. O dubla inseamna ca valoare celor 2 zaruri este egala (e.g. 1-1, 3-3, 5-5 etc.). (2p)

CREATE DATABASE campionat_table;
Go

USE campionat_table;
Go

CREATE TABLE angajati (
	id INT PRIMARY KEY IDENTITY (1, 1),
	nr_contract VARCHAR(50),
	nume VARCHAR(50)
)

CREATE TABLE mese (
	numar INT PRIMARY KEY IDENTITY,
	culoare VARCHAR(30)
)

CREATE TABLE meci (
	id INT PRIMARY KEY IDENTITY,
	numar_masa INT FOREIGN KEY REFERENCES mese(numar),
	jucator1 INT NOT NULL,
	jucator2 INT NOT NULL,
	castigator INT,
	data_meci DATE,
	FOREIGN KEY(jucator1) REFERENCES angajati(id),
	FOREIGN KEY(jucator2) REFERENCES angajati(id),
	FOREIGN KEY(castigator) REFERENCES angajati(id)
)

CREATE TABLE mutari (
	id INT PRIMARY KEY IDENTITY,
	id_meci INT FOREIGN KEY REFERENCES meci(id),
	piesa_mutata_de_la INT,
	piesa_mutata_la INT,
)
--ar trebui sa aiba si id-ul jucatorului care executa mutarea

CREATE TABLE spectatori (
	id_meci INT FOREIGN KEY REFERENCES meci(id),
	id_spectator INT FOREIGN KEY REFERENCES angajati(id),
	PRIMARY KEY(id_meci, id_spectator)
)

Go


CREATE OR ALTER PROCEDURE usp_calculeaza_premiu (@id_jucator INT, @puncte INT OUTPUT)
	AS
		BEGIN
			SET @puncte = 0

			DECLARE @meciuri_castigate INT = 0
			SELECT @meciuri_castigate = COUNT(*) FROM meci WHERE castigator = @id_jucator

			DECLARE @numar_spectatori INT = 0
			SELECT @numar_spectatori = COUNT (DISTINCT S.id_spectator) FROM spectatori S
												INNER JOIN meci M ON M.jucator1 = @id_jucator OR M.jucator2 = @id_jucator

			DECLARE @numar_total_meciuri INT = 0
			SELECT @numar_total_meciuri = COUNT(*) FROM meci WHERE castigator IS NOT NULL

			DECLARE @numar_total_meciuri_jucate INT = 0
			SELECT @numar_total_meciuri_jucate = COUNT(*) FROM meci WHERE (jucator1 = @id_jucator OR jucator2 = @id_jucator) AND castigator IS NOT NULL

			DECLARE @numar_total_meciuri_vazute INT = 0
			SELECT @numar_total_meciuri_vazute = COUNT(*) FROM spectatori WHERE id_spectator = @id_jucator

			DECLARE @puncte_adunate INT
			SET @puncte_adunate = @meciuri_castigate * 100 + @numar_spectatori * 10 - (@numar_total_meciuri - @numar_total_meciuri_jucate - @numar_total_meciuri_vazute) * 10
			IF @puncte_adunate > 0
				SET @puncte = @puncte_adunate
		END
	Go

DECLARE @puncte INT
EXEC usp_calculeaza_premiu @id_jucator = 1, @puncte = @puncte OUTPUT

PRINT @puncte

Go

CREATE OR ALTER VIEW vw_duble_angajat_c001
	AS
		SELECT COUNT(*) AS numar_duble FROM mutari M
		INNER JOIN meci ME ON ME.id = M.id_meci
		INNER JOIN angajati A ON A.id = ME.jucator1 OR A.id = ME.jucator2
		WHERE A.nr_contract = 'C001' AND M.piesa_mutata_de_la = M.piesa_mutata_la
	GO

