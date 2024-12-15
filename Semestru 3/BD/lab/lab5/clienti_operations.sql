USE zdrobix
GO

CREATE OR ALTER PROCEDURE usp_client_operations (@operation VARCHAR(20), @id INT = NULL, @nume VARCHAR(40) = NULL, @varsta INT = NULL)
	AS
		BEGIN
			IF (@operation = 'INSERT' OR @operation = 'insert')
				BEGIN
					IF (@nume IS NULL OR @varsta IS NULL)
						BEGIN
							PRINT('Nu poti insera campuri goale.')
							RETURN;
						END
					DECLARE @result1 BIT
					DECLARE @result2 BIT
					EXEC usp_validate_name @name = @nume, @table = 'CLIENTI', @result = @result1 OUTPUT
					EXEC usp_validate_age @varsta = @varsta, @result = @result2 OUTPUT
					IF (@result1 = 1 AND  @result2 = 1)
						INSERT INTO CLIENTI(nume, varsta) VALUES (@nume, @varsta)
					ELSE
						BEGIN
							IF (@result1 = 0)
								PRINT('Numele este invalid.')
							IF (@result2 = 0)
								PRINT('Varsta este invalida.')
						END
				END
			IF (@operation = 'SELECT' OR @operation = 'select')
				BEGIN
					IF (@id IS NULL)
						BEGIN
							PRINT('Nu exista clienti cu id-ul null.')
							RETURN;
						END
					BEGIN
						DECLARE @result BIT
						EXEC usp_validate_id @id = @id, @table = 'CLIENTI', @result = @result OUTPUT
						IF (@result = 1)
							SELECT * FROM CLIENTI WHERE id = @id;
						IF (@result = 0)
							PRINT('Nu exista niciun Client cu id-ul dat.')
					END
				END
			IF (@operation = 'UPDATE' OR @operation = 'update')
				BEGIN
					IF (@id IS NULL OR @nume IS NULL OR @varsta IS NULL)
						BEGIN
							PRINT('Pentru update nu poti avea campuri goale.')
							RETURN;
						END
					DECLARE @result3 BIT
					DECLARE @result4 BIT
					DECLARE @result5 BIT
					EXEC usp_validate_name @name = @nume, @result = @result3 OUTPUT
					EXEC usp_validate_age @varsta = @varsta, @result = @result4 OUTPUT
					EXEC usp_validate_id @id = @id, @table = 'CLIENTI', @result = @result5 OUTPUT
					IF (@result3 = 1 AND @result4 = 1 AND @result5 = 1)
						UPDATE CLIENTI SET nume = @nume, varsta = @varsta WHERE id = @id
					ELSE
						BEGIN
							IF (@result3 = 0)
								PRINT('Numele este invalid.')
							IF (@result4 = 0)
								PRINT('Varsta este invalida.')
							IF (@result5 = 0)
								PRINT('Id-ul dat nu exista.')
						END
				END
			IF (@operation = 'DELETE' OR @operation = 'delete')
				BEGIN
					IF (@id IS NULL)
						BEGIN
							PRINT('Nu exista clienti cu id-ul null.')
							RETURN;
						END
					BEGIN
						DECLARE @result6 BIT
						EXEC usp_validate_id @id = @id, @table = 'CLIENTI', @result = @result6 OUTPUT
						IF (@result6 = 1)
							BEGIN
								DELETE FROM TRANZACTII WHERE id_client = @id
								DELETE FROM CLIENTI WHERE id = @id;
							END
						IF (@result6 = 0)
							PRINT('Nu exista niciun Client cu id-ul dat.')
					END
				END
		END
	GO

EXEC usp_client_operations @operation = 'insert', @nume = 'Alex5Zdroba', @varsta = 1000
EXEC usp_client_operations @operation = 'insert', @nume = 'Alex Zdroba', @varsta = 1000
EXEC usp_client_operations @operation = 'insert', @nume = 'George Pop', @varsta = 24

EXEC usp_client_operations @operation = 'select', @id = 12398
EXEC usp_client_operations @operation = 'select', @id = 1

EXEC usp_client_operations @operation = 'update', @id = 1, @nume = 'Alex Zdroba', @varsta = 20
EXEC usp_client_operations @operation = 'update', @id = 1, @nume = 'Alex5 Zdroba', @varsta = 20
EXEC usp_client_operations @operation = 'update', @id = 1, @nume = 'Alex5 Zdroba', @varsta = -1
EXEC usp_client_operations @operation = 'update', @id = 129, @nume = 'Alex Zdroba', @varsta = 20

EXEC usp_client_operations @operation = 'delete', @id = 20004

SELECT * FROM CLIENTI
