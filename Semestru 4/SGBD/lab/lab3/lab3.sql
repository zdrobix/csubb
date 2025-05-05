CREATE OR ALTER PROCEDURE usp_validate_id (@id INT, @table VARCHAR(30), @result BIT OUTPUT)
	AS
		BEGIN
			SET @result = 0
			DECLARE @sql NVARCHAR(MAX)
			SET @sql = 'IF EXISTS( 
							SELECT * FROM ' + QUOTENAME(@table) + ' WHERE id = ' + CAST(@id AS NVARCHAR) + '
						)
							BEGIN
								SET @result = 1
							END'	
			EXEC sp_executesql @sql, N'@result BIT OUTPUT', @result OUTPUT
		END
	GO

CREATE OR ALTER PROCEDURE usp_validate_name (@name VARCHAR(100), @table VARCHAR(100) = NULL, @result BIT OUTPUT) 
	AS
		BEGIN
			SET @result = 1
			IF (@name NOT LIKE '[A-Z]% [A-Z]%')
				SET @result = 0
			IF (@name LIKE '%[0-9]%')
				SET @result = 0
			DECLARE @sql NVARCHAR(MAX)
			IF (@table IS NULL)
				RETURN;
			SET @sql = 'IF EXISTS( 
							SELECT * FROM ' + QUOTENAME(@table) + ' WHERE nume = @name
						)
							BEGIN
								SET @result = 0
							END'	
			EXEC sp_executesql @sql, N'@name NVARCHAR(MAX), @result BIT OUTPUT',@name, @result OUTPUT
		END
	GO

CREATE OR ALTER PROCEDURE usp_validate_age (@varsta INT, @result BIT OUTPUT) 
	AS
		BEGIN
			SET @result = 1
			IF (@varsta < 0)
				SET @result = 0
			IF (@varsta > 100)
				SET @result = 0
		END
	GO

CREATE OR ALTER PROCEDURE usp_validate_pret (@pret MONEY, @result BIT OUTPUT) 
	AS
		BEGIN
			SET @result = 1
			IF (@pret < 0)
				SET @result = 0
			IF (@pret > 1000)
				SET @result = 0
		END
	GO

CREATE OR ALTER PROCEDURE usp_validate_date (@date DATE, @result BIT OUTPUT)
	AS
		BEGIN
			SET @result = 1;
			IF (@date > GETDATE())
			BEGIN
				SET @result = 0;
				RETURN;
			END
			IF (@date < '1999-01-01')
				BEGIN
					SET @result = 0;
					RETURN;
				END
		END
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
			DECLARE @nume_client_valid BIT
			DECLARE @varsta_client_valid BIT
			DECLARE @nume_medicament_valid BIT
			DECLARE @pret_valid BIT
			DECLARE @id_producator_valid BIT
			DECLARE @data_tranzactie_valid BIT
			EXEC usp_validate_name @name = @nume_client, @table = 'CLIENTI', @result = @nume_client_valid OUTPUT
			EXEC usp_validate_age @varsta = @varsta_client, @result = @varsta_client_valid OUTPUT
			EXEC usp_validate_name @name = @nume_medicament_valid, @table = 'MEDICAMENTE', @result = @nume_medicament_valid OUTPUT
			EXEC usp_validate_pret @pret = @pret_valid, @result = @pret_valid OUTPUT
			EXEC usp_validate_id @id = @id_producator, @table = 'PRODUCATORI', @result = @id_producator_valid OUTPUT
			EXEC usp_validate_date @date = @data_tranzactie, @result = @data_tranzactie_valid OUTPUT
			
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
	@nume_client = 'Alex Alexasdasd',
	@varsta_client = 20,
	@nume_medicament = 'Metoclopramid',
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
				DECLARE @nume_client_valid BIT
				DECLARE @varsta_client_valid BIT
				EXEC usp_validate_name @name = @nume_client, @table = 'CLIENTI', @result = @nume_client_valid OUTPUT
				EXEC usp_validate_age @varsta = @varsta_client, @result = @varsta_client_valid OUTPUT
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
				DECLARE @nume_medicament_valid BIT
				DECLARE @pret_valid BIT
				DECLARE @id_producator_valid BIT
				EXEC usp_validate_name @name = @nume_medicament, @table = 'MEDICAMENTE', @result = @nume_medicament_valid OUTPUT
				EXEC usp_validate_pret @pret = @pret_valid, @result = @pret_valid OUTPUT
				EXEC usp_validate_id @id = @id_producator, @table = 'PRODUCATORI', @result = @id_producator_valid OUTPUT
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
			DECLARE @data_tranzactie_valid BIT

			EXEC usp_validate_date @date = @data_tranzactie, @result = @data_tranzactie_valid OUTPUT
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
	@nume_client = 'Ppopopo Popopo',
	@varsta_client = 20,
	@nume_medicament = 'Metoclopramid1',
	@pret = 10.50,
	@id_producator = 1,
	@data_tranzactie = '2025-05-05'


SELECT * FROM _Log