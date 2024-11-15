USE PARC_DISTRACTII;
GO

CREATE TABLE ATRACTII (
	cod_a INT PRIMARY KEY IDENTITY,
	nume VARCHAR(40),
	descriere VARCHAR(40),
	varsta_min INT,
	cod_s INT FOREIGN KEY REFERENCES SECTIUNI(cod_s)
)

CREATE TABLE SECTIUNI (
	cod_s INT PRIMARY KEY IDENTITY,
	nume VARCHAR(40),
	descriere VARCHAR(40)
)

CREATE TABLE CATEGORII (
	cod_c INT PRIMARY KEY IDENTITY,
	nume VARCHAR(40)
)

CREATE TABLE VIZITATORI (
	cod_v INT PRIMARY KEY IDENTITY,
	nume VARCHAR(30),
	email VARCHAR(30),
	cod_c INT FOREIGN KEY REFERENCES CATEGORII(cod_c)

)

CREATE TABLE NOTE (
	cod_a INT FOREIGN KEY REFERENCES ATRACTII(cod_a),
	cod_v INT FOREIGN KEY REFERENCES VIZITATORI(cod_v),
	nota INT,
	CONSTRAINT pk_note PRIMARY KEY(cod_a, cod_v)
)

GO

--1. functie care primeste ca parametru numele unei categorii si returneaza codul acesteia
CREATE FUNCTION CodCategorie(@nume VARCHAR(40)) 
RETURNS INT
AS
	BEGIN
		IF (EXISTS(SELECT * FROM CATEGORII C WHERE C.nume = @nume))
			RETURN (SELECT MAX(C.cod_c) FROM CATEGORII C WHERE C.nume = @nume);
		RETURN 0;
	END
GO;

PRINT(dbo.CodCategorie('Familie'))
GO

--2. un trigger care impiedica stergerea din tabelul Categorii
CREATE TRIGGER EliminareCategorie
ON CATEGORII
INSTEAD OF DELETE
AS 
BEGIN
	RAISERROR('Nu se poate efectua stergerea', 16, 1)
END;
GO

DELETE FROM CATEGORII;
GO

--3. view cu pensionari sau copii
CREATE VIEW CategoriiPensionarCopil
AS
	SELECT * FROM CATEGORII C WHERE C.nume = 'Pensionar' OR C.nume = 'Copil'
GO

--4.view sectiuni incep cu litera c
CREATE VIEW SectiuniLiteraC
AS
	SELECT * FROM SECTIUNI S WHERE S.nume LIKE 'C%'
GO

--5. functie inline care returneaza din tabelul sectiuni incepand cu o litera data ca parametru
CREATE FUNCTION SectiuniFunction(@ch VARCHAR(1)) 
RETURNS @SectiuniFunction TABLE (cod_s INT, nume VARCHAR(40), descriere VARCHAR(40))
AS
BEGIN
INSERT INTO @SectiuniFunction(cod_s, nume, descriere)
SELECT * FROM SECTIUNI S WHERE S.nume LIKE '%_' + @ch
RETURN;
END;
GO


SELECT * FROM SectiuniFunction('E')
SELECT * FROM SectiuniFunction('a')
GO

--6. view nume, nota, atractie
CREATE VIEW NumeNotaAtractie
AS
	SELECT V.nume AS nume_vizitator, N.nota , A.nume AS nume_atractie FROM NOTE N  
	INNER JOIN VIZITATORI V on V.cod_v = N.cod_v
	INNER JOIN ATRACTII A ON A.cod_a = N.cod_a
GO

SELECT * FROM NumeNotaAtractie

SELECT * FROM CategoriiPensionarCopil
SELECT * FROM ATRACTII;
SELECT * FROM CATEGORII;
SELECT * FROM NOTE;
SELECT * FROM VIZITATORI;
SELECT * FROM SECTIUNI;







