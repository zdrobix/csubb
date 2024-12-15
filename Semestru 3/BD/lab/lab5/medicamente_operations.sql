USE zdrobix
GO


CREATE OR ALTER PROCEDURE usp_medicamente_operations (@operation VARCHAR(20), @id INT = NULL, @nume VARCHAR(40) = NULL, @pret MONEY = NULL, @id_producator INT = NULL)
	AS
		BEGIN
			IF (@operation = 'INSERT' OR @operation = 'insert')
				BEGIN
					IF (@nume IS NULL OR @pret IS NULL)
						BEGIN
							PRINT('Nu poti insera campuri goale.')
							RETURN;
						END
					DECLARE @result1 BIT
					DECLARE @result2 BIT
					DECLARE @result3 BIT
					EXEC usp_validate_name @name = @nume, @table = 'MEDICAMENTE', @result = @result1 OUTPUT
					EXEC usp_validate_pret @pret = @pret, @result = @result2 OUTPUT
					EXEC usp_validate_id @id = @id_producator, @table = 'PRODUCATORI', @result = @result3 OUTPUT
					IF (@result1 = 1 AND  @result2 = 1 AND @result3 = 1)
						INSERT INTO MEDICAMENTE(nume, pret, id_producator) VALUES (@nume, @pret, @id_producator)
					ELSE
						BEGIN
							IF (@result1 = 0)
								PRINT('Numele este invalid.')
							IF (@result2 = 0)
								PRINT('Pretul este invalid.')
							IF (@result3 = 0)
								PRINT('Id-ul producatorului este invalid.')
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
						EXEC usp_validate_id @id = @id, @table = 'MEDICAMENTE', @result = @result OUTPUT
						IF (@result = 1)
							SELECT * FROM MEDICAMENTE WHERE id = @id;
						IF (@result = 0)
							PRINT('Nu exista niciun medicament cu id-ul dat.')
					END
				END
			IF (@operation = 'UPDATE' OR @operation = 'update')
				BEGIN
					IF (@id IS NULL OR @nume IS NULL OR @pret IS NULL OR @id_producator IS NULL)
						BEGIN
							PRINT('Pentru update nu poti avea campuri goale.')
							RETURN;
						END
					DECLARE @result7 BIT
					DECLARE @result6 BIT
					DECLARE @result4 BIT
					DECLARE @result5 BIT
					EXEC usp_validate_name @name = @nume, @result = @result6 OUTPUT
					EXEC usp_validate_pret @pret = @pret, @result = @result4 OUTPUT
					EXEC usp_validate_id @id = @id, @table = 'MEDICAMENTE', @result = @result5 OUTPUT
					EXEC usp_validate_id @id = @id_producator, @table = 'PRODUCATORI', @result = @result7 OUTPUT
					IF (@result6 = 1 AND @result4 = 1 AND @result5 = 1 AND @result7 = 1)
						UPDATE MEDICAMENTE SET nume = @nume, pret = @pret, id_producator = @id_producator WHERE id = @id
					ELSE
						BEGIN
							IF (@result6 = 0)
								PRINT('Numele este invalid.')
							IF (@result4 = 0)
								PRINT('Pretul este invalida.')
							IF (@result5 = 0)
								PRINT('Id-ul dat nu exista.')
							IF (@result7 = 0)
								PRINT('Id-ul de producator dat nu exista.')
						END
				END
			IF (@operation = 'DELETE' OR @operation = 'delete')
				BEGIN
					IF (@id IS NULL)
						BEGIN
							PRINT('Nu exista medicamente cu id-ul null.')
							RETURN;
						END
					BEGIN
						DECLARE @result8 BIT
						EXEC usp_validate_id @id = @id, @table = 'MEDICAMENTE', @result = @result8 OUTPUT
						IF (@result8 = 1)
							DELETE FROM MEDICAMENTE WHERE id = @id;
						IF (@result8 = 0)
							PRINT('Nu exista niciun Medicament cu id-ul dat.')
					END
				END
		END
	GO

EXEC usp_medicamente_operations @operation = 'insert', @nume = 'Metoclopramid A', @pret = 10.50, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'insert', @nume = 'MetoclopramidA', @pret = 10.50, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'insert', @nume = 'MetoclopramidA', @pret = 1050, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'insert', @nume = 'MetoclopramidA', @pret = 1050, @id_producator = 119824

EXEC usp_medicamente_operations @operation = 'select', @id = 1
EXEC usp_medicamente_operations @operation = 'select', @id = 1245

EXEC usp_medicamente_operations @operation = 'update', @id = 1, @nume = 'Paracetamol A', @pret = 15.00, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'update', @id = 1, @nume = 'Paracetamol B', @pret = 15.00, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'update', @id = 1, @nume = 'Paracetamol', @pret = 15.00, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'update', @id = 1, @nume = 'Paracetamol', @pret = 1500, @id_producator = 1
EXEC usp_medicamente_operations @operation = 'update', @id = 1, @nume = 'Paracetamol', @pret = 1500, @id_producator = 1135325

EXEC usp_medicamente_operations @operation = 'delete', @id = 20001

SELECT * FROM MEDICAMENTE