USE practic_sgbd;

create table clienti (
	id INT PRIMARY KEY IDENTITY,
	nume VARCHAR(50),
	prenume VARCHAR(50),
	data_nastere DATE,
	gen VARCHAR(50)
)

create table facturi (
	id INT PRIMARY KEY IDENTITY,
	data_emitere DATE,
	data_scadenta DATE,
	suma INT,
	adresa VARCHAR(50),
	id_client INT FOREIGN KEY REFERENCES clienti(id)
)

insert into clienti (nume, prenume, data_nastere, gen) VALUES
('alex', 'zdroba', '2004-10-11', 'masculin'),
('marius', 'zdroba', '2004-10-11', 'masculin'),
('pop', 'zdroba', '2004-10-11', 'masculin'),
('ioan', 'zdroba', '2004-10-11', 'masculin'),
('elena', 'zdroba', '2004-10-11', 'feminin'),
('alexa', 'zdroba', '2004-10-11', 'feminin'),
('alexia', 'zdroba', '2004-10-11', 'feminin')

insert into facturi (data_emitere, data_scadenta, suma, adresa, id_client) VALUES
('2024-10-11', '2025-10-11', 400, 'Cluj-Napoca', 1),
('2024-10-11', '2025-10-11', 1000, 'Cluj-Napoca', 2),
('2024-10-11', '2025-10-11', 1000, 'Cluj-Napoca', 3),
('2024-10-11', '2025-10-11', 1000, 'Cluj-Napoca', 4),
('2024-10-11', '2025-10-11', 1000, 'Cluj-Napoca', 5),
('2024-10-11', '2025-10-11', 1000, 'Cluj-Napoca', 6),
('2024-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 7),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 1),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 2),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 3),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 4),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 5),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 6),
('2025-09-11', '2025-10-11', 1000, 'Cluj-Napoca', 7)

SELECT id, data_emitere, data_scadenta, suma, adresa FROM facturi 



-- 2) clienti pentru care media facturilor e intre 300 si 500

SELECT c.id, c.nume, c.prenume FROM clienti c
INNER JOIN facturi f ON f.id_client = c.id
GROUP BY c.nume, c.prenume, c.id
HAVING AVG(f.suma) BETWEEN 300 AND 500

-- 3) factura cu plata cea mai mare si clientul caruia ii apartine
SELECT top 1 f.id, f.suma, c.nume, c.prenume
FROM facturi f
INNER JOIN clienti c ON f.id_client = c.id
ORDER BY f.suma DESC

-- 4) creare index 
CREATE NONCLUSTERED INDEX nc_index_clienti_name ON clienti(nume)

SELECT nume, id FROM clienti ORDER BY nume

SELECT nume, prenume, id FROM clienti ORDER BY nume

