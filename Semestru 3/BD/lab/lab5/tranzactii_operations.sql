USE zdrobix
GO

CREATE OR ALTER PROCEDURE usp_tranzactie_operations (@operation VARCHAR(20), @id INT = NULL, @id_client INT = NULL, @id_medicamente INT =  NULL, @data_tranzactie DATE = NULL)
	AS
		BEGIN
			IF (@operation = 'INSERT' OR @operation = 'insert')
				BEGIN
					IF (@id_client IS NULL OR @id_medicamente IS NULL OR @data_tranzactie IS NULL)
						BEGIN
							PRINT('Nu poti insera campuri goale.')
							RETURN;
						END
					DECLARE @result1 BIT
					DECLARE @result2 BIT
					DECLARE @result3 BIT
					EXEC usp_validate_id @id = @id_client, @table = 'CLIENTI', @result = @result1 OUTPUT
					EXEC usp_validate_id @id = @id_medicamente, @table = 'MEDICAMENTE', @result = @result2 OUTPUT
					EXEC usp_validate_date @date = @data_tranzactie, @result = @result3 OUTPUT
					IF (@result1 = 1 AND  @result2 = 1 AND @result3 = 1)
						INSERT INTO TRANZACTII(id_client, id_medicamente, data_tranzactie) VALUES (@id_client, @id_medicamente, @data_tranzactie)
					ELSE
						BEGIN
							IF (@result1 = 0)
								PRINT('Clientul este invalid.')
							IF (@result2 = 0)
								PRINT('Medicamentul este invalid.')
							IF (@result3 = 0)
								PRINT('Data este invalida.')
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
						EXEC usp_validate_id @id = @id, @table = 'TRANZACTII', @result = @result OUTPUT
						IF (@result = 1)
							SELECT C.nume AS Client, M.nume AS Medicament, M.pret AS PRET, T.data_tranzactie AS Data
							FROM TRANZACTII T
							INNER JOIN CLIENTI C ON C.id = T.id_client
							INNER JOIN MEDICAMENTE M ON M.id = T.id_medicamente
							WHERE T.id = @id;
						IF (@result = 0)
							PRINT('Nu exista nicio tranzactie cu id-ul dat.')
					END
				END
			IF (@operation = 'UPDATE' OR @operation = 'update')
				BEGIN
					IF (@id IS NULL OR @id_client IS NULL OR @id_medicamente IS NULL OR @data_tranzactie IS NULL)
						BEGIN
							PRINT('Pentru update nu poti avea campuri goale.')
							RETURN;
						END
					DECLARE @result4 BIT
					DECLARE @result5 BIT
					DECLARE @result6 BIT
					DECLARE @result7 BIT
					EXEC usp_validate_id @id = @id_client, @table = 'CLIENTI', @result = @result4 OUTPUT
					EXEC usp_validate_id @id = @id, @table = 'TRANZACTII', @result = @result5 OUTPUT
					EXEC usp_validate_id @id = @id_medicamente, @table = 'MEDICAMENTE', @result = @result6 OUTPUT
					EXEC usp_validate_date @date = @data_tranzactie, @result = @result7 OUTPUT
					IF (@result4 = 1 AND @result5 = 1 AND @result6 = 1 AND @result7 = 1)
						UPDATE TRANZACTII SET id_client = @id_client, id_medicamente = @id_medicamente, data_tranzactie = @data_tranzactie WHERE id = @id
					ELSE
						BEGIN
							IF (@result4 = 0)
								PRINT('Clientul este invalid.')
							IF (@result5 = 0)
								PRINT('Tranzactia este invalida.')
							IF (@result6 = 0)
								PRINT('Medicamentul este invalid.')
							IF (@result7 = 0)
								PRINT('Data este invalida.')
						END
				END
			IF (@operation = 'DELETE' OR @operation = 'delete')
				BEGIN
					IF (@id IS NULL)
						BEGIN
							PRINT('Nu exista tranzactii cu id-ul null.')
							RETURN;
						END
					BEGIN
						DECLARE @result8 BIT
						EXEC usp_validate_id @id = @id, @table = 'TRANZACTII', @result = @result8 OUTPUT
						IF (@result8 = 1)
							DELETE FROM TRANZACTII WHERE id = @id;
						IF (@result8 = 0)
							PRINT('Nu exista nicio tranzactie cu id-ul dat.')
					END
				END
		END
	GO

EXEC usp_tranzactie_operations @operation = 'insert', @id_client = 1, @id_medicamente = 1, @data_tranzactie = '2024-11-11';
EXEC usp_tranzactie_operations @operation = 'insert', @id_client = 12341, @id_medicamente = 1, @data_tranzactie = '2024-11-11';
EXEC usp_tranzactie_operations @operation = 'insert', @id_client = 12341, @id_medicamente = 3463461, @data_tranzactie = '2024-11-11';
EXEC usp_tranzactie_operations @operation = 'insert', @id_client = 12341, @id_medicamente = 3463461, @data_tranzactie = '3000-11-11';
EXEC usp_tranzactie_operations @operation = 'insert', @id_client = 20005, @id_medicamente = 20002, @data_tranzactie = '2004-11-11';

EXEC usp_tranzactie_operations @operation = 'select', @id = 1
EXEC usp_tranzactie_operations @operation = 'select', @id = 235

EXEC usp_tranzactie_operations @operation = 'update', @id = 1, @id_client = 1, @id_medicamente = 1, @data_tranzactie = '2024-10-09'
EXEC usp_tranzactie_operations @operation = 'update', @id = 1, @id_client = 1, @id_medicamente = 1, @data_tranzactie = '2024-10-01'
EXEC usp_tranzactie_operations @operation = 'update', @id = 1, @id_client = 1, @id_medicamente = 1, @data_tranzactie = '1900-10-01'
EXEC usp_tranzactie_operations @operation = 'update', @id = 1, @id_client = 1, @id_medicamente = 34576, @data_tranzactie = '1900-10-01'
EXEC usp_tranzactie_operations @operation = 'update', @id = 1, @id_client = 234235, @id_medicamente = 34576, @data_tranzactie = '1900-10-01'
EXEC usp_tranzactie_operations @operation = 'update', @id = 1355, @id_client = 234235, @id_medicamente = 34576, @data_tranzactie = '1900-10-01'

EXEC usp_tranzactie_operations @operation = 'delete', @id = 1002


SELECT * FROM TRANZACTII