﻿USE zdrobix;

INSERT INTO CLIENTI (nume, varsta) VALUES
('Alex Zdroba', 19),
('Georgescu Valentin', 28),
('Ionescu Maria', 42),
('Radu Radu Radu', 47),
('Mihaila Daniela', 33),
('Vasilescu Calificatu', 31),
('Marin Alexandru', 60),
('Copil Elena', 55),
('Cosmin Cristina', 25),
('Neagu Adi', 40);

INSERT INTO GRADE_SEVERITATE (grad) VALUES
('Usoara'),
('Moderata'),
('Grava'),
('Letala');

INSERT INTO CAUZE (cauza) VALUES
('Viroza'),
('Bacteriana'),
('Genetica'),
('Ereditara'),
('Decizii regretabile'),
('Iubire si amaraciune'),
('Autoimuna'),
('Necunoscuta');

INSERT INTO BOLI (nume, id_grad_severitate, id_cauza) VALUES
('Gripa', 1, 1),
('Pneumonie', 2, 2),
('Cancer', 3, 3),
('Diabet', 3, 3),
('Artrita reumatoida', 3, 4),
('COVID-19', 3, 1),
('Hepatita', 3, 2),
('Astm', 2, 4),
('Tuberculoza', 3, 2),
('Hipertensiune', 2, 3),
('Scleroza multipla', 3, 4),
('Alzheimer', 3, 3),
('Parkinson', 3, 3),
('Boala Crohn', 3, 4),
('Ebola', 4, 1),
('Malaria', 3, 2),
('Leucemie', 4, 3),
('SIDA', 4, 1),
('Migrena', 2, 3),
('Boala Lyme', 3, 2);


INSERT INTO BENEFICII (nume) VALUES
('Reduce febra'),
('Calmează durerea'),
('Scade tensiunea'),
('Intăaeste imunitatea'),
('Reduce inflamația'),
('Tratează infecțiile'),
('Îmbunătățește respirația'),
('Reglează glicemia'),
('Reduce anxietatea'),
('Accelerează vindecarea');

INSERT INTO TARI (nume) VALUES
('Romania'),
('Jermania'),
('Franta'),
('SUA'),
('China'),
('Japonia'),
('Marea Britanie'),
('India'),
('Italia'),
('Canada');

INSERT INTO PRODUCATORI (nume, id_tara) VALUES
('Pfizer', 4),
('Sanofi', 3),
('Bayer', 2),
('Roche', 2),
('AstraZeneca', 7),
('Merck', 4),
('Johnson & Johnson', 4),
('Novartis', 3),
('Takeda', 6),
('Cipla', 8);

INSERT INTO MEDICAMENTE (nume, pret, id_producator) VALUES
('Paracetamol', 15, 1),
('Ibuprofen', 20, 2),
('Aspirina', 10, 3),
('Metformin', 50, 4),
('Amoxicilina', 30, 5),
('Remdesivir', 200, 6),
('Ciprofloxacina', 40, 7),
('Insulina', 100, 8),
('Tamiflu', 80, 9),
('Ivermectina', 25, 10),
('Diclofenac', 18, 1),
('Naproxen', 22, 2),
('Azitromicina', 35, 3),
('Ranitidina', 12, 4),
('Claritromicina', 40, 5),
('Atorvastatina', 55, 6),
('Clopidogrel', 60, 7),
('Losartan', 65, 8),
('Amlodipina', 70, 9),
('Levotiroxina', 20, 10),
('Prednison', 25, 1),
('Dexametazona', 28, 2),
('Lisinopril', 30, 3),
('Omeprazol', 15, 4),
('Pantoprazol', 17, 5),
('Furosemid', 20, 6),
('Metoprolol', 35, 7),
('Salbutamol', 45, 8),
('Hydrocortizon', 50, 9),
('Enalapril', 55, 10);

INSERT INTO TRANZACTII (id_client, id_medicamente, data_tranzactie) VALUES
(1, 1, '2024-10-01'),
(2, 2, '2024-10-02'),
(3, 3, '2024-10-03'),
(4, 4, '2024-10-04'),
(5, 5, '2024-10-05'),
(6, 6, '2024-10-06'),
(7, 7, '2024-10-07'),
(8, 8, '2024-10-08'),
(9, 9, '2024-10-09'),
(10, 10, '2024-10-10'),
(1, 11, '2024-10-11'),
(2, 12, '2024-10-12'),
(3, 13, '2024-10-13'),
(4, 14, '2024-10-14'),
(5, 15, '2024-10-15'),
(6, 16, '2024-10-16'),
(7, 17, '2024-10-17'),
(8, 18, '2024-10-18'),
(9, 19, '2024-10-19'),
(10, 20, '2024-10-20'),
(1, 21, '2024-10-21'),
(2, 22, '2024-10-22'),
(3, 23, '2024-10-23'),
(4, 24, '2024-10-24'),
(5, 25, '2024-10-25'),
(6, 26, '2024-10-26'),
(7, 27, '2024-10-27'),
(8, 28, '2024-10-28'),
(9, 29, '2024-10-29'),
(10, 30, '2024-10-30');

INSERT INTO TRANZACTII(id_client, id_medicamente, data_tranzactie) VALUES
(1, 11, '2024-10-11');



INSERT INTO BENEFICII_MEDICAMENTE (id_medicament, id_beneficiu) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 8),
(5, 6),
(6, 7),
(7, 6),
(8, 8),
(9, 4),
(10, 5),
(11, 1),
(12, 2),
(13, 6),
(14, 3),
(15, 6),
(16, 5),
(17, 9),
(18, 3),
(19, 8),
(20, 4),
(21, 5),
(22, 6),
(23, 9),
(24, 2),
(25, 6),
(26, 4),
(27, 7),
(28, 9),
(29, 2),
(30, 5);

INSERT INTO BOLI_CLIENTI (id_client, id_boala, data_diagnostic) VALUES
(1, 16, '2024-01-05'),
(1, 18, '2024-02-12'),
(1, 19, '2024-03-15'),
(2, 17, '2024-01-22'),
(2, 21, '2024-03-01'),
(2, 23, '2024-04-10'),
(3, 20, '2024-01-18'),
(3, 22, '2024-02-28'),
(3, 25, '2024-03-12'),
(4, 18, '2024-01-25'),
(4, 26, '2024-02-15'),
(5, 27, '2024-01-10'),
(5, 19, '2024-02-22'),
(5, 30, '2024-03-30'),
(6, 16, '2024-01-05'),
(6, 18, '2024-02-15'),
(7, 24, '2024-01-12'),
(7, 25, '2024-02-18'),
(8, 29, '2024-01-20'),
(8, 28, '2024-02-25'),
(9, 21, '2024-03-01'),
(9, 31, '2024-03-12'),
(10, 32, '2024-03-22'),
(10, 33, '2024-04-01'),
(1, 34, '2024-04-05'),
(2, 35, '2024-04-10'),
(3, 17, '2024-04-15'),
(4, 27, '2024-04-20'),
(5, 30, '2024-04-25'),
(6, 22, '2024-04-30'),
(7, 18, '2024-05-01'),
(8, 25, '2024-05-05'),
(9, 19, '2024-05-10');

SELECT * FROM CLIENTI
SELECT * FROM MEDICAMENTE
SELECT * FROM TRANZACTII
