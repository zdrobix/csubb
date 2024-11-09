USE zdrobix;
GO

--1. procedura care modifica tipul pretului
CREATE PROCEDURE modificaTipPretMedicamentInapoiInt
AS
	BEGIN
		ALTER TABLE MEDICAMENTE 
		ALTER COLUMN pret INT;
	END;
GO

EXEC modificaTipPretMedicamentInapoiInt;
GO

--2. Adauga pretul default pentru obiectul de tip medicament
CREATE PROCEDURE stergeConstrangereImplicitMedicamente
AS 
	BEGIN
		ALTER TABLE MEDICAMENTE
		DROP CONSTRAINT df_pret_min
	END;
GO

EXEC stergeConstrangereImplicitMedicamente;
GO

--3. Creaza un tabel de retete
CREATE PROCEDURE stergeTabelRetete
AS 
	BEGIN
		DROP TABLE RETETE
	END;
GO

EXEC stergeTabelRetete;
GO

--4. Adauga un camp nou in tabel
CREATE PROCEDURE stergeColoanaDate 
AS
	BEGIN 
		ALTER TABLE RETETE
		DROP COLUMN data_eliberarii 
	END;
GO

EXEC stergeColoanaDate;
GO

--5. Adauga foreign key constraint
CREATE PROCEDURE stergeFKConstraint 
AS
	BEGIN
		ALTER TABLE RETETE
			DROP CONSTRAINT fk_constraint_id_client 
		ALTER TABLE RETETE
			DROP CONSTRAINT fk_constraint_id_medicament
	END;
GO

EXEC stergeFKConstraint;
GO

