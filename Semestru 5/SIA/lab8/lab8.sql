USE SIA;

-- II. Să se creeze un nou tabel cu numele CHECKIN_info, pe baza datelor din tabelul
--Rezervări folosind interogările Make Table. Tabelul va conţin următoarele câmpuri
--RezervareID, DataSosire, DataPlecare, NumarAdulti şi NumărCopii.

CREATE TABLE CheckIn_Info (
	id INT PRIMARY KEY IDENTITY(1, 1),
	data_sosire DATE,
	data_plecare DATE,
	numar_adulti INT, 
	numar_copii INT
)

--III. Să se creeze un tabel cu numele Copie care să conţină toate înregistrările din tabelul
--Rezervări.
CREATE TABLE Copie_Rezervari (
	id INT PRIMARY KEY IDENTITY(1, 1),									 
	id_client INT FOREIGN KEY REFERENCES InfoClienti(id),				
	data_sosire DATE,
	data_plecare DATE,
	data_rezervare DATE,
	numar_camere INT,
	numar_adulti INT,
	numar_copii INT,
	tip_camera VARCHAR(15) 
	CHECK (tip_camera in ('single', 'double', 'twin', 'apartament')),	
	status_anulare BIT
)


ALTER TABLE Copie_Rezervari													
	ADD numar_nopti AS (DATEDIFF(DAY, data_plecare, data_sosire)), 
	numar_persoane AS (numar_adulti + numar_copii);

INSERT INTO Copie_Rezervari 
(id_client, data_rezervare, data_sosire, data_plecare, numar_camere, numar_adulti, numar_copii, tip_camera, status_anulare)
VALUES
(2, '2025-01-01', '2025-01-25', '2025-02-06', 1, 1, 0, 'single', 0),
(3, '2025-01-01', '2025-02-25', '2025-03-03', 1, 1, 0, 'single', 0),
(4, '2025-01-01', '2025-04-27', '2025-05-02', 1, 1, 0, 'single', 0),
(5, '2025-01-01', '2025-06-26', '2025-07-03', 1, 1, 0, 'double', 0),
(6, '2025-01-01', '2025-08-26', '2025-09-03', 1, 1, 0, 'double', 0),
(7, '2025-01-01', '2025-10-26', '2025-11-03', 1, 2, 0, 'double', 0),
(2, '2025-01-01', '2025-11-26', '2025-12-03', 1, 1, 1, 'twin', 0),
(3, '2025-01-01', '2025-03-26', '2025-04-03', 1, 2, 0, 'twin', 0),
(4, '2025-01-01', '2025-05-26', '2025-06-03', 1, 2, 2, 'apartament', 0),
(5, '2025-01-01', '2025-01-26', '2025-02-03', 1, 4, 0, 'apartament', 0)	


--IV. Să se şteargă din tabelul Copie toate rezervările anulate
DELETE FROM Copie_Rezervari WHERE status_anulare = 1

--V. Să se şteargă din tabelul Copie rezervările care au fost făcute în luna august
DELETE FROM Copie_Rezervari WHERE MONTH(data_rezervare) = 8

--VI. In tabelul Copie să se dubleze numărul de camere rezervate pentru tipul de camera
--apartament.
UPDATE Copie_Rezervari SET numar_camere = numar_camere * 2 WHERE tip_camera = 'apartament'

--VII. In tabelul Copie să se anuleze rezervările făcute în luna ianuarie pentru tipul de camera
--single.UPDATE Copie_Rezervari SET status_anulare = 1 WHERE MONTH(data_rezervare) = 1 AND tip_camera = 'single'


--IX. Repartiţia clienţilor după vârsta, sex şi cetăţenie
SELECT YEAR(GETDATE()) - YEAR(data_nasterii) AS varsta, sex, cetatenie FROM InfoClienti

--X. Lista clienţilor care au mai mult de o rezervare.SELECT DISTINCT I.id as id, I.nume + ' ' + I.prenume as nume FROM InfoClienti IINNER JOIN Rezervari R ON I.id = R.id_clientGROUP BY I.nume, I.prenume, I.idHAVING COUNT(*) > 1--XI. Lista clienţilor care nu au rezervări.SELECT DISTINCT I.id as id, I.nume + ' ' + I.prenume as nume FROM InfoClienti IINNER JOIN Rezervari R ON I.id = R.id_clientGROUP BY I.nume, I.prenume, I.idHAVING COUNT(*) = 0