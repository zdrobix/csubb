CREATE DATABASE SIA
GO

USE SIA

--I. Să se creeze o baza de date şi să se salveze cu numele CLIENTI. Baza de date va conţine
--un tabel InfoClienti în care se vor stoca informaţiile referitoare la clienţii unei firme.
--Structura tabelului este următoarea:

CREATE TABLE InfoClienti (									--1. Să se creeze tabelul InfoClienti cu câmpurile aferente
	id INT PRIMARY KEY IDENTITY(1, 1),						--2. Să se stabilească cheia primară pentru tabelul InfoClienti.
	nume VARCHAR(30),
	prenume VARCHAR(30),
	data_nasterii DATE,
	locul_nasterii VARCHAR(30),
	cetatenie VARCHAR(50),
	sex VARCHAR(1) CHECK (sex = 'M' or sex = 'F'),			--5. Câmpul SEX va conţine valoarea M pentru masculin sau F pentru feminin. Să se introducă regula de validare corespunzătoare.
	adresa NVARCHAR(MAX),
	localitate VARCHAR(50),
	id_tara INT FOREIGN KEY REFERENCES Tari(id),			--3. Câmpul CodTara să permită introducerea unor valori cuprinse intre 1 şi 6. 
	id_judet INT FOREIGN KEY REFERENCES Judete(id),			--4. Creați o lista pentru indicativul județului (SB – Sibiu, BN – Bistriţa Năsăud, CJ- Cluj etc.). Valorile vor fi preluate dintr-un tabel cu denumirea Judete. Adăugați în tabel 6
	telefon VARCHAR(15) CHECK (telefon LIKE '0264/%'),		--7. Numerele de telefon vor fi introduse în următorul format (004) 0264/158963.	id_tip_act INT FOREIGN KEY REFERENCES Acte(id),			--6. Câmpul TipActID să permită introducerea unor valori cuprinse între 1 şi 4.
	numar_act VARCHAR(15),
	emitent VARCHAR(30),
	data_expirarii DATE,
	email NVARCHAR(MAX) CHECK (email LIKE '%@%.%')			--8. Introduceţi o regulă de validare pentru adresa de email. Adresa de email trebuie să conţină caracterul @ şi caracterul punct.
)

CREATE TABLE Tari (
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50)
)

INSERT INTO Tari VALUES 
('Romania'), 
('Ungaria'), 
('Moldova'), 
('Bulgaria'), 
('Germania'), 
('Italia')

CREATE TABLE Judete (
	id INT PRIMARY KEY IDENTITY(1, 1),
	indicativ VARCHAR(2),
	nume VARCHAR(50)
)

INSERT INTO Judete VALUES
('-', '-'),					-- in afara tarii
('CJ', 'Cluj'),
('BN', 'Bistrita-Nasaud'),
('SB', 'Sibiu'),
('SJ', 'Salaj'),
('MM', 'Maramures'),
('AR', 'Arad')

CREATE TABLE Acte (
	id INT PRIMARY KEY IDENTITY(1, 1),
	nume VARCHAR(50)
)

INSERT INTO Acte VALUES
('Buletin identitate'),
('Carte de identitate'),
('Pasaport'),
('Carnet de sofer')

INSERT INTO InfoClienti										--9. Să se introducă 3 clienţi din Romania şi 2 din afara ţării. Clienţii vor fi din localităţi diferite. 
(nume, prenume, data_nasterii, locul_nasterii, cetatenie, sex, adresa, localitate, id_tara, id_judet, telefon, id_tip_act, numar_act, emitent, data_expirarii, email) 
VALUES
('Zdroba', 'Alexandru', '2004-11-10', 'Cluj-Napoca', 'romana', 'M', 'str. Strada nr. 1', 'Cluj-Napoca', 1, 1, '0264/191919', 1, '999888', 'SPCLEP Cluj-Napoca', '2028-10-10', 'alexandruzdroba@gmail.com'),
('Vasile', 'Dumitru', '1997-05-04', 'Salaj', 'romana', 'M', 'str. Bulevard nr. 99', 'Cluj-Napoca', 1, 1, '0264/898989', 2, '888777', 'SPCLEP Cluj-Napoca', '2025-09-09', 'vasiledumitru@gmail.com'),
('Popescu', 'Andra', '2000-01-20', 'Maramures', 'romana', 'F', 'str. Maramures nr. 2', 'Borsa', 1, 5, '0264/557755', 4, '130023', 'DRPCIV Maramures', '2040-05-05', 'andraa@hotmail.com'),
('Kiss', 'Kelemen', '2005-06-19', 'Budapesta', 'maghiara', 'M', 'str. Fechete nr. 4', 'Budapesta', 2, 7, '0264/554433', 3, '887766', 'KSD Maghiar', '2030-04-04', 'kisskelemen@gmail.com'),
('Calzone', 'Francesca', '2001-07-15', 'Napoli', 'italiana', 'F', 'str. Cetti Grande nr. 99', 'Napoli', 6, 7, '0264/443322', 3, '998288', 'PAC Italiano', '2025-10-27', 'francesca88@yahoo.com')

SELECT * FROM InfoClienti									--10. Să se ordoneze înregistrările crescător după câmpul Nume. 
	ORDER BY nume

SELECT * FROM InfoClienti									--11. Să se ordoneze înregistrările descrescător după câmpul CodTara.
	ORDER BY id_tara

	
ALTER TABLE InfoClienti										--12. Modificaţi structura bazei de date prin adăugarea unui nou câmp Cod_postal – text (20).
	ADD cod_postal VARCHAR(20)
	
UPDATE InfoClienti											--13. Să se completeze câmpul introdus cu informaţiile corespunzătoare pentru fiecare client.
SET InfoClienti.cod_postal = '400220' WHERE id = 2

UPDATE InfoClienti
SET InfoClienti.cod_postal = '124839' WHERE id = 3

UPDATE InfoClienti
SET InfoClienti.cod_postal = '324643' WHERE id = 4

UPDATE InfoClienti
SET InfoClienti.cod_postal = '234236' WHERE id = 5

UPDATE InfoClienti
SET InfoClienti.cod_postal = '235266' WHERE id = 6


INSERT INTO InfoClienti
(nume, prenume, data_nasterii, locul_nasterii, cetatenie, sex, adresa, localitate, id_tara, id_judet, telefon, id_tip_act, numar_act, emitent, data_expirarii, email, cod_postal) 
VALUES
('Pop', 'Ion', '2003-11-10', 'Cluj-Napoca', 'romana', 'M', 'str. Strada nr. 2', 'Cluj-Napoca', 1, 1, '0264/191922', 1, '994888', 'SPCLEP Cluj-Napoca', '2028-05-10', 'popion@gmail.com', '240915')									--14. Introduceţi la final o înregistrare cu datele dumneavoastră. (datele mele sunt deja introduse, deci am pus altceva)



--II. Să se creeze un nou tabel cu numele Plăţi în baza de date CLIENTI. Tabelul va avea următoarea structură:CREATE TABLE Plati (	id INT PRIMARY KEY IDENTITY(1, 1),						--a) Să se stabilească cheia primară.					id_client INT FOREIGN KEY REFERENCES InfoClienti(id),	--b) Să se creeze o relaţie de tipul one-to-many între tabelul Infoclienţi şi tabelul Plăţi. Să se forţeze integritatea referenţială.	data_platii DATE,	suma MONEY,	data_scadenta DATE)INSERT INTO Plati											--c) Să se introducă 8 înregistrări în tabelul Plăţi.(id_client, data_platii, suma, data_scadenta)VALUES(2, '2025-08-05', 1000, GETDATE()),(3, '2025-09-05', 26, GETDATE()),(4, '2025-02-05', 3456, GETDATE()),(5, '2025-04-05', 769, GETDATE()),(6, '2025-02-05', 100, GETDATE()),(7, '2025-03-05', 253, GETDATE()),(2, '2025-04-05', 2346, GETDATE()),(3, '2025-05-05', 235, GETDATE())--III. Să se adauge un nou tabel - Rezervari care să conţină date referitoare la rezervările camerelor unui hotel.CREATE TABLE Rezervari (	id INT PRIMARY KEY IDENTITY(1, 1),									--b) Să se stabilească cheia primară pentru tabelul Rezervări. 	id_client INT FOREIGN KEY REFERENCES InfoClienti(id),				--d) Să se creeze relaţia dintre cele două tabele şi să se impună integritatea referenţială.	data_rezervare DATE,	data_sosire DATE,	data_plecare DATE,	numar_camere INT,	numar_adulti INT,	numar_copii INT,	tip_camera VARCHAR(15) 	CHECK (tip_camera in ('single', 'double', 'twin', 'apartament')),	--a) Câmpul de date TipCamera să permită introducerea următoarelor valori: single, double, twin, apartament. 	status_anulare BIT)ALTER TABLE Rezervari													--c) Adăugați două câmpuri calculate: numărul de nopți și numărul de persoane	ADD numar_nopti AS (DATEDIFF(DAY, data_plecare, data_sosire)), 	numar_persoane AS (numar_adulti + numar_copii);INSERT INTO Rezervari (id_client, data_rezervare, data_sosire, data_plecare, numar_camere, numar_adulti, numar_copii, tip_camera, status_anulare)VALUES(2, '2025-01-01', '2025-01-25', '2025-02-06', 1, 1, 0, 'single', 0),(3, '2025-01-01', '2025-02-25', '2025-03-03', 1, 1, 0, 'single', 0),(4, '2025-01-01', '2025-04-27', '2025-05-02', 1, 1, 0, 'single', 0),(5, '2025-01-01', '2025-06-26', '2025-07-03', 1, 1, 0, 'double', 0),(6, '2025-01-01', '2025-08-26', '2025-09-03', 1, 1, 0, 'double', 0),(7, '2025-01-01', '2025-10-26', '2025-11-03', 1, 2, 0, 'double', 0),(2, '2025-01-01', '2025-11-26', '2025-12-03', 1, 1, 1, 'twin', 0),(3, '2025-01-01', '2025-03-26', '2025-04-03', 1, 2, 0, 'twin', 0),(4, '2025-01-01', '2025-05-26', '2025-06-03', 1, 2, 2, 'apartament', 0),(5, '2025-01-01', '2025-01-26', '2025-02-03', 1, 4, 0, 'apartament', 0)												--e) Să se adauge 10 de rezervări					