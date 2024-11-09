USE zdrobix;
GO

--1. procedura care modifica tipul pretului
CREATE PROCEDURE procedura_undo_1
AS
	BEGIN
		ALTER TABLE MEDICAMENTE 
		ALTER COLUMN pret INT;
	END;
GO

--2. Adauga pretul default pentru obiectul de tip medicament
CREATE PROCEDURE procedura_undo_2
AS 
	BEGIN
		ALTER TABLE MEDICAMENTE
		DROP CONSTRAINT df_pret_min
	END;
GO

--3. Creaza un tabel de retete
CREATE PROCEDURE procedura_undo_3
AS 
	BEGIN
		DROP TABLE RETETE
	END;
GO

--4. Adauga un camp nou in tabel
CREATE PROCEDURE procedura_undo_4 
AS
	BEGIN 
		ALTER TABLE RETETE
		DROP COLUMN data_eliberarii 
	END;
GO

--5. Adauga foreign key constraint
CREATE PROCEDURE procedura_undo_5 
AS
	BEGIN
		ALTER TABLE RETETE
			DROP CONSTRAINT fk_constraint_id_client 
		ALTER TABLE RETETE
			DROP CONSTRAINT fk_constraint_id_medicament
	END;
GO

