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
	SELECT id 
	FROM TRANZACTII 
	WHERE id_client = 1
GO

CREATE NONCLUSTERED INDEX nc_index_client_tranzaction ON TRANZACTII(id_client)
GO

CREATE OR ALTER VIEW vw_medicamente AS
	SELECT id 
	FROM MEDICAMENTE 
	WHERE nume LIKE 'Vitamina%'
GO

CREATE NONCLUSTERED INDEX nc_index_nume_medicamente ON MEDICAMENTE(nume)

SELECT * FROM vw_medicamente;
SELECT * FROM vw_tranzactii;

SELECT id FROM TRANZACTII ORDER BY id_client
SELECT id, nume FROM MEDICAMENTE ORDER BY nume
