--Caleidoscop

--Caleidoscop este un serial Netflix. Are 9 episoade. Fiecare episod are durata,
--nume si ordine cronologica. Fiecare utilizator (nume, email) vizioneaza epsisoadele
--intr-o ordine aleatorie, mai putin episdolu 9 care va fi vizionat ultimul.
--Intr-un episod joaca mai multi actori. Fiecare actor are un nume si mai multe replici
--intr-un episod. O replica contine un text si e spus la un moment dat in episod.
--Un actor poate sa apara in mai multe episoade.

--1. Scrieti un script SQL care creeaza un model relational pentru a reprezenta si stoca datele specifice
--serialului Caleidoscop (4p)

--2. Creati o procedura care "da play" la urmatorul episod pentru un utilizator dat. (3p)
--Puteti folosii functiile RAND() (intoarce un nr in (0,1)) si FLOOR(x) (intoarce cel mai mic intreg fata de x)

--3. Creati un view care genereaza scriptul serialului (toate replicile) in format "NUME_ACTOR: TEXT_REPLICA" (2p)

CREATE DATABASE caleidoscop;
Go

USE caleidoscop;
Go

CREATE TABLE episoade (
	id_e INT PRIMARY KEY IDENTITY (1, 1),
	durata INT NOT NULL,
	nume VARCHAR(50) NOT NULL
)

CREATE TABLE utilizatori (
	id_u INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50) NOT NULL,
	email VARCHAR(50)
)	

CREATE TABLE actori (
	id_a INT PRIMARY KEY IDENTITY (1, 1),
	nume VARCHAR(50) NOT NULL
)

CREATE TABLE replici (
	id_r INT PRIMARY KEY IDENTITY (1, 1),
	id_episod INT NOT NULL FOREIGN KEY REFERENCES episoade(id_e),
	id_actor INT NOT NULL FOREIGN KEY REFERENCES actori(id_a),
	text_r VARCHAR(100) NOT NULL
)

CREATE TABLE aparitii_actori (
	id_actor INT NOT NULL FOREIGN KEY REFERENCES actori(id_a),
	id_episod INT NOT NULL FOREIGN KEY REFERENCES episoade(id_e),
	PRIMARY KEY(id_actor, id_episod)
)

CREATE TABLE vizionare_episoade (
	id_episod INT NOT NULL FOREIGN KEY REFERENCES episoade(id_e),
	id_utilizator INT NOT NULL FOREIGN KEY REFERENCES utilizatori(id_u),
	ordine INT NOT NULL
)

