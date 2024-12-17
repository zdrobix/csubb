;5. Definiti o functie care testeaza apartenenta unui nod intr-un arbore n-ar reprezentat sub forma (radacina 
;(subarbore1) (subarbore2) .. (subarboren)) 

;model matematic:
;exista (X Ab) = Fals 		                       , daca n = 0, Ab =l1..ln
;		 Adevarat 			       , daca l1 = X, Ab = l1..ln
;		 exista(X l1) sau exista (X l2..ln)    , daca n /= 0, Ab = l1..ln 

(defun exista(Nod Arbore)
  	(cond
		((null Arbore) nil)
		((atom Arbore) (equal Nod Arbore))
		(t (or (exista Nod (car Arbore)) (exista Nod (cdr Arbore))))
	)
)


