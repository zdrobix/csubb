USE zdrobix;
GO

--1. procedura care modifica tipul pretului
CREATE PROCEDURE modificaTipPretMedicament
AS
	BEGIN
		ALTER TABLE MEDICAMENTE 
		ALTER COLUMN pret MONEY;
	END;
GO

EXEC modificaTipPretMedicament;
GO

--2. Adauga pretul default pentru obiectul de tip medicament
CREATE PROCEDURE adaugaConstrangereImplicitMedicamente
AS 
	BEGIN
		ALTER TABLE MEDICAMENTE
		ADD CONSTRAINT df_pret_min DEFAULT 10 FOR pret;
	END;
GO

EXEC adaugaConstrangereImplicitMedicamente;
GO

--3. Creaza un tabel de retete
CREATE PROCEDURE creazaTabelRetete
AS 
	BEGIN
		CREATE TABLE RETETE (
			id INT PRIMARY KEY IDENTITY,
			id_client INT,
			id_medicament INT,
			doctor VARCHAR(40)
			)
	END;
GO

EXEC creazaTabelRetete;
GO

--4. Adauga un camp nou in tabel
CREATE PROCEDURE adaugaColoanaDate 
AS
	BEGIN 
		ALTER TABLE RETETE
		ADD data_eliberarii DATE
	END;
GO

EXEC adaugaColoanaDate;
GO

--5. Adauga foreign key constraint
CREATE PROCEDURE adaugaFKConstraint 
AS
	BEGIN
		ALTER TABLE RETETE
			ADD CONSTRAINT fk_constraint_id_client 
			FOREIGN KEY (id_client) 
			REFERENCES CLIENTI(id);
		ALTER TABLE RETETE
			ADD CONSTRAINT fk_constraint_id_medicament
			FOREIGN KEY (id_medicament) 
			REFERENCES MEDICAMENTE(id);
	END;
GO

EXEC adaugaFKConstraint;
GO

