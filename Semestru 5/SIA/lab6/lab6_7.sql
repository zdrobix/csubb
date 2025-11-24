USE SIA

-- Avand tabelele InfoClienti si Rezervari din laboratorul trecut.


-- 1. o listă cu numele, prenumele şi numărul de telefon al clienţilor din judeţul Cluj, 
-- ordonată crescător după câmpul nume.
SELECT I.nume, I.prenume, I.telefon FROM InfoClienti I
INNER JOIN Judete J ON J.id = I.id_judet
WHERE J.nume = 'Cluj'
ORDER BY I.nume


-- 2. o listă cu numele, prenumele şi localitatea clienţilor care nu domiciliază în judeţul
-- Cluj, ordonaţi crescător după câmpul localitate.
SELECT I.nume, I.prenume, I.localitate FROM InfoClienti I
INNER JOIN Judete J ON J.id = I.id_judet
WHERE J.nume != 'Cluj'
ORDER BY I.localitate

-- 3. o listă cu numele, prenumele clienţilor, data sosirii şi data plecării – pentru acei
-- clienţi care au rezervat camere de tip Double, ordonată crescător după data sosirii.
-- Să se afişeze data sosiri si data plecării în format Long Date.
SELECT I.nume, I.prenume, R.data_sosire, R.data_plecare FROM InfoClienti I
INNER JOIN Rezervari R ON R.id_client = I.id
WHERE R.tip_camera = 'double'
ORDER BY R.data_sosire

-- 4. o listă cu numele şi prenumele clienţilor (concatenate intr-un singur câmp),
-- ordonată crescător.
SELECT nume + ' ' + prenume AS Clienti FROM InfoClienti ORDER BY nume

-- 5. o listă cu adresele de corespondenţă ale clienţilor. Se va concatena într-un singur
-- câmp adresa, localitatea şi judeţul.
SELECT I.adresa + ', ' + I.localitate + ', ' + J.nume AS adresa FROM InfoClienti I
INNER JOIN Judete J ON J.id = I.id_judet

-- 6. clienţii a căror nume începe cu litera B;
SELECT nume from InfoClienti 
WHERE nume LIKE 'B%'

-- 7. clienţii a căror nume începe cu litera R si este format din 4 caractere;
SELECT nume from InfoClienti 
WHERE nume LIKE 'R___'

-- 8. numele si prenumele clienţilor care trebuie să sosească între 10 – 31 decembrie
-- 2019.
SELECT DISTINCT I.nume, I.prenume FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id
WHERE R.data_sosire >= '2019-12-10' AND R.data_sosire <= '2019-12-31'

-- 9. o listă cu numele, prenumele clienţilor şi numărul de zile pentru care au făcut
-- rezervarea. Sortaţi lista după numarul de zile.
SELECT I.nume, I.prenume, -1 * R.numar_nopti as numar_nopti FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id
ORDER BY R.numar_nopti

-- 10. numele şi prenumele clienţilor care au rezervat camere pentru o perioadă cuprinsă
-- între 3 şi 5 zile.
SELECT I.nume, I.prenume, -1 * R.numar_nopti as numar_nopti FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id
WHERE -1 * R.numar_nopti BETWEEN 3 AND 5

-- 11. o listă cu numele şi prenumele clienţilor de sex masculin.
SELECT nume, prenume FROM InfoClienti 
WHERE sex = 'M'

-- 12. numele si prenumele clienţilor născuţi în anul 1985.
SELECT nume, prenume FROM InfoClienti 
WHERE YEAR(data_nasterii) = 1985


-- 13. numele si prenumele clienţilor născuţi în luna octombrie.
SELECT nume, prenume FROM InfoClienti 
WHERE MONTH(data_nasterii) = 10

-- 14. lista clienţilor (nume, prenume) care au făcut rezervarea înainte de luna mai 2019
-- sau după luna octombrie 2019.
SELECT DISTINCT I.nume, I.prenume FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id
WHERE R.data_rezervare <= '2019-05-01' OR R.data_rezervare >= '2019-10-31'

-- 15. o listă cu numele, prenumele şi vârsta clienţilor.
SELECT nume, prenume,YEAR(GETDATE()) - YEAR(data_nasterii) AS varsta FROM InfoClienti

-- 16. numele, prenumele, ziua, luna, anul naşterii, data naşterii.
SELECT nume, prenume, 
DAY(data_nasterii) AS ziua_nasterii,
MONTH(data_nasterii) AS luna_nasterii,
YEAR(data_nasterii) AS anul_nasterii,
data_nasterii
FROM InfoClienti


-- 17. lista rezervarilor anulate.
SELECT id, id_client FROM Rezervari
WHERE status_anulare = 1

-- 18. numele, prenumele si numarul de telefon al clientilor care au anulat rezervarea.
SELECT DISTINCT I.nume, I.prenume, I.telefon FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id 
WHERE R.status_anulare = 1

-- 19. numele clientilor care au rezervat camere de tip apartament, twin sau double.
SELECT DISTINCT I.nume, I.prenume FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id 
WHERE R.tip_camera != 'single'

-- 20. numele clientilor care trebuie sa soseasca in week-end in perioada 1 Noiembrie
-- 2019 – 30 Noiembrie 2019.
SELECT DISTINCT I.nume, I.prenume FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id 
WHERE R.data_sosire BETWEEN '2019-11-01' AND '2019-11-30'

-- 21. lista cu toate campurile si toate inregistrarile din tabelul infoclienti;
SELECT * FROM InfoClienti

-- 22. Numarul de persoane/camera rezervata.
SELECT numar_persoane / numar_camere AS nr_de_persoane_per_camera FROM Rezervari

-- 23. lista clienților români dintr-un anumit judeţ. Indicativul judeţului se va introduce
-- ca şi parametru.
SELECT * FROM InfoClienti I
INNER JOIN Judete J ON J.id = I.id_judet
WHERE J.indicativ = 'CJ'

-- 24. lista clienților al căror nume începe cu o anumită literă. Litera se va introduce ca
-- si parametru.
SELECT * FROM InfoClienti
WHERE nume LIKE 'A%'

-- 25. lista clienţilor care au făcut rezervare pentru un anumit interval.
SELECT DISTINCT I.nume, I.prenume FROM InfoClienti I
INNER JOIN REZERVARI R ON R.id_client = I.id
WHERE R.data_rezervare <= '2019-05-01' OR R.data_rezervare >= '2019-10-31'

-- 26. Vârsta medie, minimă şi maximă a clienților.
SELECT MIN(YEAR(GETDATE()) - YEAR(data_nasterii)) AS varsta_minima,
AVG(YEAR(GETDATE()) - YEAR(data_nasterii)) AS varsta_medie,
MAX(YEAR(GETDATE()) - YEAR(data_nasterii)) AS varsta_maxima 
FROM InfoClienti

-- 27. Numărul clienților cu cetăţenie străină.
SELECT COUNT(*) as numar_straini FROM InfoClienti
WHERE cetatenie != 'romana'

-- 28. Numărul de clienți din fiecare tară.
SELECT T.nume, COUNT(I.id) AS numar_clienti FROM Tari T
INNER JOIN InfoClienti I ON I.id_tara = T.id
GROUP BY T.nume

-- 29. Numărul clienților din Ungaria.
SELECT T.nume, COUNT(I.id) AS numar_clienti FROM Tari T
INNER JOIN InfoClienti I ON I.id_tara = T.id
WHERE T.nume = 'Ungaria'
GROUP BY T.nume

-- 30. Numărul rezervărilor anulate.
SELECT COUNT(*) AS numar_anulate FROM Rezervari
WHERE status_anulare = 1

-- 31. Perioada medie pentru care s-au făcut rezervări.
SELECT -1 * AVG(numar_nopti) AS timp_mediu FROM Rezervari 

-- 32. Numărul de rezervări pentru fiecare tip de cameră și numărul de camere rezervate.
SELECT DISTINCT R.tip_camera, COUNT(R.id) AS numar_rezervari, SUM(R.numar_camere) AS total_camere FROM Rezervari R
INNER JOIN InfoClienti I ON I.id = R.id_client
GROUP BY R.tip_camera

