USE zdrobix;
GO

--1. procedura care modifica tipul pretului
CREATE PROCEDURE procedura_1
AS
	BEGIN
		ALTER TABLE MEDICAMENTE 
		ALTER COLUMN pret MONEY;
	END;
GO

--2. Adauga pretul default pentru obiectul de tip medicament
CREATE PROCEDURE procedura_2
AS 
	BEGIN
		ALTER TABLE MEDICAMENTE
		ADD CONSTRAINT df_pret_min DEFAULT 10 FOR pret;
	END;
GO

--3. Creaza un tabel de retete
CREATE PROCEDURE procedura_3
AS 
	BEGIN
		CREATE TABLE RETETE (
			id INT PRIMARY KEY IDENTITY,
			id_client INT,
			id_medicament INT,
			doctor VARCHAR(40)
			)
	END;
GO

--4. Adauga un camp nou in tabel
CREATE PROCEDURE procedura_4 
AS
	BEGIN 
		ALTER TABLE RETETE
		ADD data_eliberarii DATE
	END;
GO

--5. Adauga foreign key constraint
CREATE PROCEDURE procedura_5 
AS
	BEGIN
		ALTER TABLE RETETE
			ADD CONSTRAINT fk_constraint_id_client 
			FOREIGN KEY (id_client) 
			REFERENCES CLIENTI(id);
		ALTER TABLE RETETE
			ADD CONSTRAINT fk_constraint_id_medicament
			FOREIGN KEY (id_medicament) 
			REFERENCES MEDICAMENTE(id);
	END;
GO


--Tabela pentru versiunea structurii bazei de date.
CREATE TABLE VersiuneBazaDeDate (
	nr_versiune INT
);

INSERT INTO VersiuneBazaDeDate VALUES(0)
GO

CREATE PROCEDURE UpdateDB 
@versiune INT
AS
	BEGIN
		DECLARE @current_version AS INT;
		SET @current_version = (SELECT nr_versiune FROM VersiuneBazaDeDate);
		IF @versiune = @current_version
			RETURN;
		
		DELETE FROM VersiuneBazaDeDate;
		INSERT INTO VersiuneBazaDeDate(nr_versiune) VALUES (@versiune);

		DECLARE @count AS INT;
		SET @count = 0;
		DECLARE @procedure VARCHAR(15);
		DECLARE @procedure_undo VARCHAR(25);

		WHILE (@current_version < @versiune) 
		BEGIN
			SET @count = @count + 1;
			SET @current_version = @current_version + 1;
			SET @procedure = 'procedura_' + CAST(@current_version AS VARCHAR(1));
			PRINT('exec procedure ' + CAST(@current_version AS VARCHAR(1)));
			EXEC @procedure
		END;

		IF (@count > 0)
			RETURN;

		WHILE (@current_version > @versiune) 
		BEGIN
			SET @count = @count + 1;
			SET @current_version = @current_version - 1;
			SET @procedure_undo = 'procedura_undo_' + CAST(@current_version AS VARCHAR(1));
			PRINT('exec undo procedure ' + CAST(@current_version AS VARCHAR(1)));
			EXEC @procedure_undo
		END;
	END;
GO



EXEC UpdateDB @versiune = 1
EXEC UpdateDB @versiune = 2
EXEC UpdateDB @versiune = 3
EXEC UpdateDB @versiune = 4
EXEC UpdateDB @versiune = 5
GO



