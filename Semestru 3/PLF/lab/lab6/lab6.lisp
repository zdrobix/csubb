;5. Definiti o functie care testeaza apartenenta unui nod intr-un arbore n-ar reprezentat sub forma (radacina 
;(subarbore1) (subarbore2) .. (subarboren)) 

;model matematic:
;liniarizare(l1..ln) = l1 				      ,daca l1 - atom
;		       liniarizare(l1) U liniarizare(l2)
; 				U .. liniarizare(ln)          , altfel

(defun liniarizare (Lista) 
  	(cond
		((atom Lista) (list Lista))
		(t (apply #'append(mapcar #'liniarizare Lista)))
	)
)

;model matematic:
;exista(l1..ln, E) = false 			,daca n = 0
;		     true 			,daca l1 = E
;		     exista(ln..ln, E) 		,altfel

(defun exista (Lista Elem) 
  	(cond 
	  	((null Lista) nil)
		((eq (car Lista) Elem) t)
		(t (exista (cdr Lista) Elem))
	)
)

;apel functie
(defun exista_in_arbore (Arbore Nod) 
  	(cond
	  	((exista (liniarizare Arbore) Nod) t)
		(t nil)
	)
	
)
