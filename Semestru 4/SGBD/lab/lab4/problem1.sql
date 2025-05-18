USE zdrobix

--Dirty reads

BEGIN TRANSACTION
UPDATE BENEFICII 
SET nume='Reduce febra'
WHERE id = 1
WAITFOR DELAY '00:00:05'
ROLLBACK TRANSACTION

SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRAN
SELECT * FROM BENEFICII
WAITFOR DELAY '00:00:10'
SELECT * FROM BENEFICII
COMMIT TRAN

-- Solution ->
SET TRANSACTION ISOLATION LEVEL READ COMMITTED

------------------------------------------------

-- Non-repeatable reads
delete from BENEFICII 
where nume = 'Energie sporita si chef de viata'

INSERT INTO BENEFICII (nume)
VALUES ('Energie sporita')
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE BENEFICII
SET nume='Energie sporita si chef de viata'
WHERE nume='Energie sporita'
COMMIT TRAN

SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM BENEFICII
WAITFOR DELAY '00:00:10'
SELECT * FROM BENEFICII
COMMIT TRAN

-- Solution ->
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ

------------------------------------------------

-- Phantom reads
BEGIN TRAN
WAITFOR DELAY '00:00:05'
INSERT INTO BENEFICII (nume)
VALUES ('/////////////')
COMMIT TRAN

SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
BEGIN TRAN
SELECT * FROM BENEFICII
WAITFOR DELAY '00:00:10'
SELECT * FROM BENEFICII
COMMIT TRAN	

-- Solution ->
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE

------------------------------------------------


-- Deadlock
INSERT INTO CLIENTI(nume, varsta) 
VALUES ('Petrica Georgica 5', 50)
INSERT INTO BENEFICII(nume)
VALUES ('Vedere imbunatatita 5')

BEGIN TRAN
UPDATE CLIENTI SET nume='Petrica Georgica 6'
WHERE nume= 'Petrica Georgica 5'
WAITFOR DELAY '00:00:05'
UPDATE BENEFICII SET nume= 'Vedere imbunatatita 6'
WHERE nume= 'Vedere imbunatatita 5'
COMMIT TRAN

BEGIN TRAN
UPDATE BENEFICII SET nume= 'Vedere imbunatatita 7'
WHERE nume= 'Vedere imbunatatita 5'
WAITFOR DELAY '00:00:05'
UPDATE CLIENTI SET nume='Petrica Georgescu 7'
WHERE nume= 'Petrica Georgica 5'
COMMIT TRAN

SELECT * FROM CLIENTI
SELECT * FROM BENEFICII

-- Solution ->
-- set priorities for transaction
-- update the tables in the same order to avoid locking resources at the same time