--Tabele de test
INSERT INTO Tables(Name) VALUES
('CLIENTI'),
('MEDICAMENTE'),
('BENEFICII_MEDICAMENTE');
GO

--View cu clienti
CREATE OR ALTER VIEW Vw_clienti
AS 
SELECT nume, varsta FROM CLIENTI
GO;

--View cu medicamente si beneficiul fiecaruia
CREATE OR ALTER VIEW Vw_medicamente_beneficii
AS
SELECT M.nume AS medicament, B.nume AS beneficiu FROM MEDICAMENTE M
INNER JOIN BENEFICII_MEDICAMENTE BM ON BM.id_medicament = M.id
INNER JOIN BENEFICII B ON B.id = BM.id_beneficiu
GO;

--View clienti si ce medicamente au cumparat
CREATE OR ALTER VIEW Vw_clienti_medicamente
AS
SELECT C.nume AS client, M.nume AS medicament
FROM CLIENTI C 
INNER JOIN TRANZACTII T ON T.id_client = C.id
INNER JOIN MEDICAMENTE M ON M.id = T.id_medicamente
GROUP BY C.nume, M.nume
GO;

INSERT INTO Views(Name) VALUES
('Vw_clienti'),
('Vw_medicamente_beneficii'),
('Vw_clienti_medicamente');
GO

INSERT INTO Tests(Name) VALUES
('Usp_insert100'),
('Usp_insert10000'),
('Usp_insert100000'),
('Usp_delete100'),
('Usp_delete10000'),
('Usp_delete100000'),
('Usp_run_view')
GO

INSERT INTO TestTables(TestID, TableID, NoOfRows, Position) VALUES
(14 ,1 , 100, 1),
(14 ,2 , 100, 2),
(14 ,3 , 100, 3),
(17 ,1 , 100, 1),
(17 ,2 , 100, 2),
(17 ,3 , 10000, 3),
(15 ,1 , 10000, 1),
(15 ,2 , 10000, 2),
(15 ,3 , 10000, 3),
(18 ,1 , 10000, 1),
(18 ,2 , 10000, 2),
(18 ,3 , 10000, 3),
(16 ,1 , 100000, 1),
(16 ,2 , 100000, 2),
(16 ,3 , 100000, 3),
(19 ,1 , 100000, 1),
(19 ,2 , 100000, 2),
(19 ,3 , 100000, 3)
GO

INSERT INTO TestViews(TestId, ViewId) VALUES
(20, 4),
(20, 5),
(20, 6)
GO

CREATE OR ALTER PROCEDURE Usp_insert_clienti @size INT
	AS 
		BEGIN
			DECLARE @id INT
			DECLARE @nume VARCHAR(100)
			DECLARE @varsta INT
			DECLARE @index INT
			DECLARE @last INT

			SET @nume = 'ClientName'
			SET @varsta = 30
			SET @id = 10000
			SET @index = 1

			WHILE @index <= @size 
				BEGIN
					SET @id = 10000 + @index
					SELECT @last = MAX(C.id) FROM dbo.CLIENTI C
					IF @last > 10000
						SET @id = @last + 1
					SET IDENTITY_INSERT CLIENTI ON
						INSERT INTO CLIENTI(id, nume, varsta) 
						VALUES(@id, @nume, @varsta)
					SET IDeNTITY_INSERT CLIENTI OFF
					SET @index = @index + 1
				END
		END
	GO

CREATE OR ALTER PROCEDURE Usp_delete_clienti @size INT
	AS
		BEGIN
			DECLARE @id INT
			DECLARE @index INT
			DECLARE @last INT
			SET @id = 10000
			SET @index = @size
			WHILE @index > 0
				BEGIN
					SET @id = 10000 + @index
					SELECT @last = MAX(C.id) FROM dbo.CLIENTI C
					IF @id < @last
						SET @id = @last
					DELETE FROM Clienti WHERE id = @id
					SET @index = @index - 1
				END
		END
	GO

CREATE OR ALTER PROCEDURE Usp_insert_medicamente @size INT
	AS 
		BEGIN
			DECLARE @id INT
			DECLARE @nume VARCHAR(100)
			DECLARE @pret MONEY
			DECLARE @id_producator INT
			DECLARE @index INT
			DECLARE @last INT
			
			SET @nume = 'MedicamentName'
			SET @pret = 100
			SET @id_producator = 1
			SET @id = 10000
			SET @index = 1

			WHILE @index <= @size 
				BEGIN
					SET @id = 10000 + @index
					SELECT @last = MAX(M.id) FROM dbo.MEDICAMENTE M
					IF @last > 10000
						SET @id = @last + 1
					SET IDENTITY_INSERT MEDicAMENTE ON
						INSERT INTO MEDICAMENTE(id, nume, pret, id_producator) 
						VALUES(@id, @nume, @pret, @id_producator)
					SET IDeNTITY_INSERT MEDicAMENTE OFF
					SET @index = @index + 1
				END
		END
	GO

CREATE OR ALTER PROCEDURE Usp_delete_medicamente @size INT
	AS
		BEGIN
			DECLARE @id INT
			DECLARE @index INT
			DECLARE @last INT
			SET @id = 10000
			SET @index = @size
			WHILE @index > 0
				BEGIN
					SET @id = 10000 + @index
					SELECT @last = MAX(M.id) FROM dbo.MEDICAMENTE M
					IF @id < @last
						SET @id = @last
					DELETE FROM MEDICAMENTE WHERE id = @id
					SET @index = @index - 1
				END
		END
	GO


CREATE OR ALTER PROCEDURE Usp_insert_beneficii_medicamente @size INT
	AS
		BEGIN
			DECLARE @index INT
			SET @index = @size
			EXEC Usp_insert_medicamente @size
			
			DECLARE @idMedicament INT
			DECLARE @numeMedicament VARCHAR(100)
			DECLARE @pretMedicament MONEY
			DECLARE @id_producatorMedicament INT
			
			

			DECLARE CursorMedicament CURSOR SCROLL FOR
				SELECT id, nume, pret, id_producator FROM MEDICAMENTE;
			OPEN CursorMedicament;

			FETCH LAST FROM CursorMedicament INTO @idMedicament, @numeMedicament, @pretMedicament, @id_producatorMedicament;

			WHILE @index > 0 AND @@FETCH_STATUS = 0
				BEGIN
					INSERT INTO BENEFICII_MEDICAMENTE(id_medicament, id_beneficiu) VALUES
						(@idMedicament, 1)
					FETCH PRIOR FROM CursorMedicament INTO @idMedicament, @numeMedicament, @pretMedicament, @id_producatorMedicament;
					SET @index = @index - 1
				END
			CLOSE CursorMedicament
			DEALLOCATE CursorMedicament;
		END
	GO

CREATE OR ALTER PROCEDURE Usp_delete_beneficii_medicamente @size INT
	AS
		BEGIN
			DECLARE @id INT
			DECLARE @index INT
			DECLARE @last INT
			SET @id = 10000
			SET @index = @size
			WHILE @index > 0
				BEGIN
					SET @id = 10000 + @index
					SELECT @last = MAX(BM.id_medicament) FROM dbo.BENEFICII_MEDICAMENTE BM
					IF @id < @last
						SET @id = @last
					DELETE FROM BENEFICII_MEDICAMENTE WHERE id_medicament = @id AND id_beneficiu = 1;
					SET @index = @index - 1
				END
		END
	GO

CREATE OR ALTER PROCEDURE Usp_insert100 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_insert_clienti 100
			if @Table = 'MEDICAMENTE'
				EXEC Usp_insert_medicamente 100
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_insert_beneficii_medicamente 100
		END
	GO

CREATE OR ALTER PROCEDURE Usp_insert10000 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_insert_clienti 10000
			if @Table = 'MEDICAMENTE'
				EXEC Usp_insert_medicamente 10000
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_insert_beneficii_medicamente 10000
		END
	GO

CREATE OR ALTER PROCEDURE Usp_insert100000 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_insert_clienti 100000
			if @Table = 'MEDICAMENTE'
				EXEC Usp_insert_medicamente 100000
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_insert_beneficii_medicamente 100000
		END
	GO


CREATE OR ALTER PROCEDURE Usp_delete100 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_delete_clienti 100
			if @Table = 'MEDICAMENTE'
				EXEC Usp_delete_medicamente 100
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_delete_beneficii_medicamente 100
		END
	GO

CREATE OR ALTER PROCEDURE Usp_delete10000 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_delete_clienti 10000
			if @Table = 'MEDICAMENTE'
				EXEC Usp_delete_medicamente 10000
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_delete_beneficii_medicamente 10000
		END
	GO

CREATE OR ALTER PROCEDURE Usp_delete100000 @Table VARCHAR(50)
	AS
		BEGIN
			if @Table = 'CLIENTI'
				EXEC Usp_delete_clienti 100000
			if @Table = 'MEDICAMENTE'
				EXEC Usp_delete_medicamente 100000
			if @Table = 'BENEFICII_MEDICAMENTE'
				EXEC Usp_delete_beneficii_medicamente 100000
		END
	GO

CREATE OR ALTER PROCEDURE Usp_run_view @View VARCHAR(50)
	AS
		BEGIN
			IF @View='CLIENTI'
				SELECT * FROM Vw_clienti
			IF @View='MEDICAMENTE'
				SELECT * FROM Vw_medicamente_beneficii
			IF @View='BENEFICII_MEDICAMENTE'
				SELECT * FROM Vw_clienti_medicamente
		END	
	GO


CREATE OR ALTER PROCEDURE TestAll @Table VARCHAR(50) 
	AS
		BEGIN
			DECLARE @date1 DATETIME
			DECLARE @date2 DATETIME
			DECLARE @date3 DATETIME
			DECLARE @text NVARCHAR(500)

			DECLARE @insert VARCHAR(20)
			DECLARE @delete VARCHAR(20)
			DECLARE @size INT
			DECLARE @id INT

			SET @size = 10000
			SET @insert = CASE @size
							 WHEN 100 THEN 'Usp_insert100'
							 WHEN 10000 THEN 'Usp_insert10000'
							 WHEN 100000 THEN 'Usp_insert100000'
						  END
			SET @delete = CASE @size
							 WHEN 100 THEN 'Usp_delete100'
							 WHEN 10000 THEN 'Usp_delete10000'
							 WHEN 100000 THEN 'Usp_delete100000'
						  END
			
			IF @Table = 'CLIENTI'
				BEGIN
					SET @date1 = GETDATE()
					EXEC (@insert + ' CLIENTI')
					EXEC (@delete + ' CLIENTI')
					SET @date2 = GETDATE()
					EXEC Usp_run_view CLIENTI
					SET @date3 = GETDATE()
					SET @text = @insert + N' ' + @delete + ', and view: ' + @Table 
					INSERT INTO TestRuns VALUES (@text, @date1, @date3)
					SELECT @id = MAX(T.TestRunID) FROM dbo.TestRuns T
					INSERT INTO TestRunTables VALUES (@id,1,@date1,@date2)
					INSERT INTO TestRunViews VALUES (@id,4,@date2,@date3)
				END
			IF @Table = 'MEDICAMENTE'
				BEGIN
					SET @date1 = GETDATE()
					EXEC (@insert + ' MEDICAMENTE')
					EXEC (@delete + ' MEDICAMENTE')
					SET @date2 = GETDATE()
					EXEC Usp_run_view MEDICAMENTE
					SET @date3 = GETDATE()
					SET @text = @insert + N' ' + @delete + ', and view: ' + @Table 
					INSERT INTO TestRuns VALUES (@text, @date1, @date3)
					SELECT @id = MAX(T.TestRunID) FROM dbo.TestRuns T
					INSERT INTO TestRunTables VALUES (@id, 2,@date1,@date2)
					INSERT INTO TestRunViews VALUES (@id,5,@date2,@date3)
				END
			IF @Table = 'BENEFICII_MEDICAMENTE'
				BEGIN
					SET @date1 = GETDATE()
					EXEC (@insert + ' BENEFICII_MEDICAMENTE')
					EXEC (@delete + ' BENEFICII_MEDICAMENTE')
					SET @date2 = GETDATE()
					EXEC Usp_run_view MEDICAMENTE
					SET @date3 = GETDATE()
					SET @text = @insert + N' ' + @delete + ', and view: ' + @Table 
					INSERT INTO TestRuns VALUES (@text, @date1, @date3)
					SELECT @id = MAX(T.TestRunID) FROM dbo.TestRuns T
					INSERT INTO TestRunTables VALUES (@id,3, @date1,@date2)
					INSERT INTO TestRunViews VALUES (@id, 6,@date2,@date3)
				END
		END
	GO


CREATE OR ALTER PROCEDURE Main 
	AS
		BEGIN
			DECLARE @count INT
			SELECT @count = COUNT(*) FROM Tables
			DECLARE @name VARCHAR(20)
			
			DECLARE CursorTable CURSOR SCROLL FOR
				SELECT Name FROM Tables;

			OPEN CursorTable;
				FETCH LAST FROM CursorTable INTO @name

			 WHILE @count > 0 AND @@FETCH_STATUS = 0
				BEGIN
					EXEC TestAll @name
					print @name
					FETCH PRIOR FROM CursorTable INTO @name;
					SET @count = @count - 1
				END

			CLOSE CursorTable;
			DEALLOCATE CursorTable;
		END
	GO

EXEC Main


select * from TestRuns
select * from TestRunTables
select * from TestRunViews


delete from TestRuns
delete from TestRunTables
delete from TestRunViews

