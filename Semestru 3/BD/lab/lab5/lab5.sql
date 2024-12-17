USE zdrobix;
GO;

CREATE OR ALTER PROCEDURE usp_validate_id (@id INT, @table VARCHAR(30), @result BIT OUTPUT)
	AS
		BEGIN
			SET @result = 0
			DECLARE @sql NVARCHAR(MAX)
			SET @sql = 'IF EXISTS( 
							SELECT * FROM ' + QUOTENAME(@table) + ' WHERE id = ' + CAST(@id AS NVARCHAR) + '
						)s
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


CREATE OR ALTER VIEW vw_tranzactii AS
	SELECT T.id AS id, C.nume AS Client, M.nume AS Medicament, M.pret AS Pret, T.data_tranzactie AS Data
	FROM TRANZACTII T
	INNER JOIN CLIENTI C ON C.id = T.id_client
	INNER JOIN MEDICAMENTE M ON M.id = T.id_medicamente
GO

CREATE NONCLUSTERED INDEX nc_index_client_tranzaction ON TRANZACTII(id_client)
GO

CREATE OR ALTER VIEW vw_medicamente AS
	SELECT M.nume AS Medicament, M.pret AS Pret, P.nume AS Producator, T.nume AS Tara
	FROM MEDICAMENTE M
	INNER JOIN PRODUCATORI P ON P.id = M.id_producator 
	INNER JOIN TARI T ON P.id_tara = T.id

GO

CREATE NONCLUSTERED INDEX nc_index_nume_medicamente ON MEDICAMENTE(nume)

SELECT * FROM TRANZACTII ORDER BY id_client
SELECT * FROM MEDICAMENTE ORDER BY nume

SELECT id FROM MEDICAMENTE WHERE nume LIKE 'Vitamina%'
SELECT id FROM TRANZACTII WHERE id_client = 1