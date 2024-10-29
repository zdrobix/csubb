USE zdrobix;

--1. Tarile care au mai mult de 1 producator, si numarul acestora
SELECT T.nume, COUNT(*) AS numar_prod FROM TARI T
INNER JOIN PRODUCATORI P on P.id_tara = T.id
GROUP BY T.nume
HAVING COUNT(*) > 1

--2. Clienti tineri (<30), si cate tranzactii au efectuat
SELECT C.nume, COUNT(*) AS numar_tranzactii, SUM(M.pret) AS cost_total FROM CLIENTI C
INNER JOIN TRANZACTII T on T.id_client = C.id
INNER JOIN MEDICAMENTE M on M.id = T.id_medicamente
WHERE C.varsta < 30
GROUP BY C.nume

--3. Clienti si suma tranzactiilor efectuate
SELECT C.nume, SUM(M.pret) AS total FROM CLIENTI C
INNER JOIN TRANZACTII T on T.id_client = C.id
INNER JOIN MEDICAMENTE M on T.id_medicamente = M.id
GROUP BY C.nume
ORDER BY total

--4. Cele mai intalnite cauze printre boli
SELECT C.cauza, COUNT(*) AS total_boli FROM CAUZE C
INNER JOIN BOLI B ON C.id = B.id_cauza
GROUP BY C.cauza

--5. Producatorii din SUA cu 3 sau mai multe medicamente
SELECT P.nume, COUNT(*) AS nr_medicamente FROM PRODUCATORI P
INNER JOIN MEDICAMENTE M ON M.id_producator = P.id
INNER JOIN TARI T ON T.id = P.id_tara
WHERE T.nume = 'SUA'
GROUP BY P.nume
HAVING COUNT(*) > 2

--6. Numele si cauza pentru toate bolile letale
SELECT B.nume, C.cauza FROM BOLI B
INNER JOIN CAUZE C ON C.id = B.id_cauza
INNER JOIN GRADE_SEVERITATE GS ON GS.id = B.id_grad_severitate
WHERE GS.grad = 'Letala'

--7. Tranzactiile distincte din data 10 noiembrie
SELECT DISTINCT C.nume AS nume_client, M.nume AS nume_medicament FROM CLIENTI C
INNER JOIN TRANZACTII T ON T.id_client = C.id
INNER JOIN MEDICAMENTE M ON M.id = T.id_medicamente
WHERE T.data_tranzactie = '2024-10-11'

--8. Tarile care au producatori
SELECT DISTINCT T.nume AS tara FROM TARI T
INNER JOIN PRODUCATORI P ON P.id_tara = T.id

--9. Arata numarul de medicamente pentru fiecare beneficiu
SELECT B.nume AS beneficiu, COUNT(*) AS nr_medicamente FROM MEDICAMENTE M
INNER JOIN BENEFICII_MEDICAMENTE BM ON BM.id_medicament = M.id
INNER JOIN BENEFICII B ON B.id = BM.id_beneficiu
GROUP BY B.nume

--10. Arata clientii mai in varsta de 40 de ani, si de cate boli sufera
SELECT C.nume, C.varsta, COUNT(*) AS nr_boli FROM CLIENTI C
INNER JOIN BOLI_CLIENTI BC ON BC.id_client = C.id
INNER JOIN BOLI B ON B.id = BC.id_boala
WHERE C.varsta > 40
GROUP BY C.varsta, C.nume