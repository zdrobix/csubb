CREATE OR ALTER VIEW vw_numar_de_voturi
	AS
		SELECT DATEPART(HOUR, ora) AS [Ora], COUNT(*) AS [Numar de voturi] 
		FROM voturi
		WHERE DATEPART(HOUR, ora) >= 12 AND DATEPART(HOUR, ora) < 15
		GROUP BY ora
	GO

SELECT * FROM vw_numar_de_voturi



