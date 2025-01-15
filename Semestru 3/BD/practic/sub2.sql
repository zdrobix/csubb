CREATE OR ALTER PROCEDURE usp_inregistrare_vot (@id_cetatean INT, @id_sectie INT)
	AS
		BEGIN
			DECLARE @votat INT
			SELECT @votat = COUNT(*) FROM voturi WHERE id_cetatean = @id_cetatean

			IF @votat != 0
				BEGIN
					DECLARE @nume VARCHAR(50)
					DECLARE @prenume VARCHAR(50)
					SELECT @nume = nume FROM cetateni WHERE id = @id_cetatean
					SELECT @nume = prenume FROM cetateni WHERE id = @id_cetatean
					PRINT('Cetateanul ' + CAST(@nume AS VARCHAR) + ' ' + CAST(@prenume AS VARCHAR) + ' a votat deja.')
					RETURN 1
				END
			
			DECLARE @sectie_cetatean INT
			SELECT @sectie_cetatean = id_sectie FROM cetateni_sectii WHERE id_cetatean = @id_cetatean

			IF (@sectie_cetatean = @id_sectie)
				BEGIN
					INSERT INTO voturi (id_cetatean, ora, lista_suplimentara) 
						VALUES (@id_cetatean, GETDATE(), 0)
					RETURN 0;
				END

			INSERT INTO voturi (id_cetatean, ora, lista_suplimentara) 
						VALUES (@id_cetatean, GETDATE(), 1)
					RETURN 0;

		END
	GO

EXEC usp_inregistrare_vot @id_cetatean = 1, @id_sectie = 1



