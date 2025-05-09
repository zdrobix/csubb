CREATE OR ALTER FUNCTION uf_validate_id_producator (@id INT)
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 0
		IF EXISTS (SELECT 1 FROM PRODUCATORI WHERE id = @id)
			SET @result = 1
		RETURN @result
	END
GO

CREATE OR ALTER FUNCTION uf_validate_name_client (@name VARCHAR(100))
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 1
		IF (@name NOT LIKE '[A-Z]% [A-Z]%') OR (@name LIKE '%[0-9]%')
			SET @result = 0
		IF EXISTS (SELECT 1 FROM CLIENTI WHERE nume = @name)
			SET @result = 0
		RETURN @result
	END
GO

CREATE OR ALTER FUNCTION uf_validate_name_medicament (@name VARCHAR(100))
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 1
		IF EXISTS (SELECT 1 FROM MEDICAMENTE WHERE nume = @name)
			SET @result = 0
		RETURN @result
	END
GO

CREATE OR ALTER FUNCTION uf_validate_age (@varsta INT)
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 1;
    
		IF (@varsta < 0 OR @varsta > 100)
			SET @result = 0;
    
		RETURN @result;
	END;
GO

CREATE OR ALTER FUNCTION uf_validate_pret (@pret MONEY)
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 1;
    
		IF (@pret < 0 OR @pret > 1000)
			SET @result = 0;
    
		RETURN @result;
	END;
GO

CREATE OR ALTER FUNCTION uf_validate_date (@date DATE)
RETURNS BIT
AS
	BEGIN
		DECLARE @result BIT = 1;
    
		IF (@date > GETDATE() OR @date < '1999-01-01')
			SET @result = 0;
    
		RETURN @result;
	END;
GO

CREATE TABLE _Log (
	id INT PRIMARY KEY IDENTITY,
	tableName VARCHAR(100),
	typeOp VARCHAR(100),
	dateOp DATETIME
);
GO;

CREATE OR ALTER PROCEDURE usp_insert_tranzactii (
	@nume_client VARCHAR(40), @varsta_client INT,
	@nume_medicament VARCHAR(40), @pret MONEY, @id_producator INT,
	@data_tranzactie DATE
) AS
	BEGIN
		BEGIN TRANSACTION
		BEGIN TRY
			DECLARE @nume_client_valid BIT = dbo.uf_validate_name_client(@nume_client);
			DECLARE @varsta_client_valid BIT = dbo.uf_validate_age(@varsta_client);
			DECLARE @nume_medicament_valid BIT = dbo.uf_validate_name_medicament(@nume_medicament);
			DECLARE @pret_valid BIT = dbo.uf_validate_pret(@pret);
			DECLARE @id_producator_valid BIT = dbo.uf_validate_id_producator(@id_producator);
			DECLARE @data_tranzactie_valid BIT = dbo.uf_validate_date(@data_tranzactie);
			
			IF (@nume_client_valid <> 1)
				BEGIN
					RAISERROR('Nume client invalid', 16, 1)
				END
			IF (@varsta_client_valid <> 1)
				BEGIN
					RAISERROR('Varsta client invalid', 16, 1)
				END
			IF (@nume_medicament_valid <> 1)
				BEGIN
					RAISERROR('Nume medicament invalid', 16, 1)
				END
			IF (@pret_valid <> 1)
				BEGIN
					RAISERROR('Pret invalid', 16, 1)
				END
			IF (@id_producator_valid <> 1)
				BEGIN
					RAISERROR('ID producator invalid', 16, 1)
				END
			IF (@data_tranzactie_valid <> 1)
				BEGIN
					RAISERROR('Data tranzactie invalid', 16, 1)
				END

			INSERT INTO CLIENTI(nume, varsta) VALUES (@nume_client, @varsta_client)
			DECLARE @id_client INT = SCOPE_IDENTITY()

			INSERT INTO MEDICAMENTE(nume, pret, id_producator) VALUES (@nume_medicament, @pret, @id_producator)
			DECLARE @id_medicament INT = SCOPE_IDENTITY()

			INSERT INTO TRANZACTII(id_client, id_medicamente, data_tranzactie) VALUES (@id_client, @id_medicament, @data_tranzactie)
			COMMIT

			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('TRANZACTII', 'INSERT', GETDATE());

			PRINT('Insert done.')
		END TRY
		BEGIN CATCH
			IF @@TRANCOUNT > 0
				ROLLBACK TRAN

			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('CLIENTI, MEDICAMENTE, TRANZACTII', 'ERROR', GETDATE());

			PRINT(ERROR_MESSAGE())
		END CATCH
	END
GO;

EXEC usp_insert_tranzactii 
	@nume_client = 'Alex Paulllllllll',
	@varsta_client = 20,
	@nume_medicament = 'Metoclopramiddddddddd',
	@pret = 10.50,
	@id_producator = 1,
	@data_tranzactie = '2025-05-04'


SELECT * FROM CLIENTI
SELECT * FROM MEDICAMENTE
SELECT * FROM TRANZACTII

SELECT * FROM _Log
GO;

CREATE OR ALTER PROCEDURE usp_insert_tranzactii_rollback_selectiv (
	@nume_client VARCHAR(40), @varsta_client INT,
	@nume_medicament VARCHAR(40), @pret MONEY, @id_producator INT,
	@data_tranzactie DATE
) AS
	BEGIN
		DECLARE @id_client INT
		DECLARE @id_medicament INT
		BEGIN TRY
			BEGIN TRAN
				DECLARE @nume_client_valid BIT = dbo.uf_validate_name_client(@nume_client);
				DECLARE @varsta_client_valid BIT = dbo.uf_validate_age(@varsta_client);
				IF (@nume_client_valid <> 1)
					BEGIN
						RAISERROR('Nume client invalid', 16, 1)
					END
				IF (@varsta_client_valid <> 1)
					BEGIN
						RAISERROR('Varsta client invalid', 16, 1)
					END
				INSERT INTO CLIENTI(nume, varsta) VALUES (@nume_client, @varsta_client)
				SET @id_client = SCOPE_IDENTITY()

				INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('CLIENTI', 'INSERT', GETDATE());
				COMMIT TRAN
		END TRY
		BEGIN CATCH 
			IF @@TRANCOUNT > 0
				ROLLBACK TRAN
			PRINT('Error inserting client.' + ERROR_MESSAGE())
			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('CLIENTI', 'ERROR', GETDATE())
			RETURN
		END CATCH

		BEGIN TRY
			BEGIN TRAN
				DECLARE @nume_medicament_valid BIT = dbo.uf_validate_name_medicament(@nume_medicament);
				DECLARE @pret_valid BIT = dbo.uf_validate_pret(@pret);
				DECLARE @id_producator_valid BIT = dbo.uf_validate_id_producator(@id_producator);
				IF (@nume_medicament_valid <> 1)
					BEGIN
						RAISERROR('Nume medicament invalid', 16, 1)
					END
				IF (@pret_valid <> 1)
					BEGIN
						RAISERROR('Pret invalid', 16, 1)
					END
				IF (@id_producator_valid <> 1)
					BEGIN
						RAISERROR('ID producator invalid', 16, 1)
					END
				INSERT INTO MEDICAMENTE(nume, pret, id_producator) VALUES (@nume_medicament, @pret, @id_producator)
				SET @id_medicament = SCOPE_IDENTITY()

				INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('MEDICAMENTE', 'INSERT', GETDATE());
				COMMIT TRAN
		END TRY
		BEGIN CATCH 
			IF @@TRANCOUNT > 0
				ROLLBACK TRAN
			PRINT('Error inserting medicament.' + ERROR_MESSAGE())
			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('MEDICAMENTE', 'ERROR', GETDATE())
			RETURN
		END CATCH

		BEGIN TRY
			BEGIN TRAN
			DECLARE @data_tranzactie_valid BIT = dbo.uf_validate_date(@data_tranzactie);
			IF (@data_tranzactie_valid <> 1)
				BEGIN
					RAISERROR('Data tranzactie invalid.', 16, 1)
				END
			INSERT INTO TRANZACTII(id_client, id_medicamente, data_tranzactie) VALUES (@id_client, @id_medicament, @data_tranzactie)

			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('TRANZACTII', 'INSERT', GETDATE());
			COMMIT TRAN
		END TRY
		BEGIN CATCH 
			IF @@TRANCOUNT > 0
				ROLLBACK TRAN
			PRINT('Error inserting tranzactie.' + ERROR_MESSAGE())
			INSERT INTO _Log(tableName, typeOp, dateOp) VALUES ('TRANZACTII', 'ERROR', GETDATE())
			RETURN
		END CATCH
	END
GO


EXEC usp_insert_tranzactii_rollback_selectiv
	@nume_client = 'Mihail Popaaaaaaaaaaa',
	@varsta_client = 20,
	@nume_medicament = 'Algocaminnnnnnnnnn1234n',
	@pret = 10.50,
	@id_producator = 2,
	@data_tranzactie = '2025-05-09'


SELECT * FROM _Log

