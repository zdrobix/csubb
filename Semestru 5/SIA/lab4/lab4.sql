CREATE DATABASE SIA_Magazin
GO

USE SIA_Magazin

CREATE TABLE InfoProduse (					-- 1. Sa se creeze tabela InfoProduse cu campurile aferente
	cod INT PRIMARY KEY IDENTITY(1, 1),		-- 2. Sa se stabileasca cheia primara
	denumire VARCHAR(20),
	cod_categorie INT FOREIGN KEY REFERENCES Categorii(cod),
	pret MONEY,
	stoc INT CHECK (stoc >= 0),				-- 3. Conditie valori negative
	stoc_minim INT,
	data_adaugare DATE,
	link_imagine NVARCHAR(MAX),
	descriere NVARCHAR(MAX)
)

CREATE TABLE Categorii (					 -- 4. Se pot introduce doar valoriile din categorii
	cod INT PRIMARY KEY IDENTITY(1, 1),
	denumire VARCHAR(20)
)

INSERT INTO Categorii (denumire)
VALUES ('Produse alimentare'), ('Bauturi'), ('Produse cosmetice');


INSERT INTO InfoProduse						 -- 6. Sa se introduca 6 inregistrari
VALUES
('Sandvis', 1, 20, 10, 0, GETDATE(), 
'https://m.bucataras.ro/uploads/modules/news/37536/300x200_sandvis-cu-sunca-184586.jpg', 
'Sandvis cu paine de secara si branza de casa, muschiulet de porc si salata'),

('Apa plata', 2, 5, 200, 0, GETDATE(), 
'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSpL_2pJVcRiylG4bjWjCXDD4Rmvnrs4N5X1JoOWeNPRZmdF8KEkSfWBbQxWy15Z92DBP0NjzF9nQBh_KfIXlu8dvycxg7Q_In9od_eXytAog', 
'Apa plata imbuteliata, minerale tot ce vrei'),

('Crema de intinerire', 3, 100, 5, 0, GETDATE(), 
'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcQbauqK35o12EudzQMvfm95GLLXSusqBqSwiPbMBXrsULgnLXjBywWNIL4pBkatfwfyJIaZUAyEMtKJzjxBHMbjSTcfAbhzYX47gQV5tLwkW-KVDObL4g8M', 
'Crema de intinerire de la nivea cu extract natural de xiliofenacina.'),

('Burger', 1, 35, 10, 0, GETDATE(), 
'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQs1xehHhGQgaeNqIW_Bl-fnQRGg4Nf9EcDBOwX2CjB6FxdYmOZVPLqAwFjMVXTkiEz5BiuJPlIDLkibTLBGUqSc0i5NukhhjHeTinJ_zWR', 
'Burger cu paine de secara si branza cheddar, vita black angus'),

('Suc pepsi', 2, 7, 29, 0, GETDATE(), 
'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ4UK0tg0OWkyqip0jz_jA12HmFnLkvg_hNdiN8y-UA54r85XimtPUEPfFWJoqhh9F6WMuCyPzArN5XF19pRN1XjQIWblEUwwv-39G5NyRD', 
'Suc pepsi 2L zahar blana'),

('Crema de maini', 3, 55, 19, 0, GETDATE(), 
'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcQ61q7cACUrbHmTdcc7MEYUGxIVVDDaQMvx2LOdbpTAqBfB9dcZfNFH6trzpbchlGUEN1Lz9QWBEz2dsCwbY0N_7fr1E4x3FKu2BMKmSIya', 
'Crema de maini, ti le face fine.')

SELECT * FROM InfoProduse					 -- 7. Sa se ordoneze tabelul dupa denumire
ORDER BY denumire

SELECT* FROM InfoProduse					 -- 8. Sa se ordoneze descrescator dupa pret
ORDER BY pret DESC

INSERT INTO InfoProduse 
VALUES ('Napolitana', 1, 10, 100, 0, GETDATE(), 'https://auchan.vtexassets.com/arquivos/ids/247997-800-800?v=638387541820600000&width=800&height=800&aspect=true', 'Napolitana premium' )					 -- 9. Sa se introduca un nou produs

DELETE FROM InfoProduse WHERE cod = 3		 --10. Sa se stearga produsul cu cod = 3

ALTER TABLE InfoProduse						 --11. Modificare tabel, adaugarea coloanei cod furnizor
ADD cod_furnizor INT

UPDATE InfoProduse SET cod_furnizor = 1

SELECT cod, denumire, cod_categorie, pret, stoc, data_adaugare, link_imagine, descriere, cod_furnizor
FROM InfoProduse							 --12. Ascundeti campul stoc_minim

SELECT * FROM InfoProduse					 --13. Reafisati campul stoc_minim



