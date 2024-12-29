;4. un arbore n-ar. sa se inlocuiasca nodurile de pe nivelul k din arbore cu o valoare e data. nivelul radacinii = 0

(defun inlocuire (Arbore K E) 
  	(cond 
	  	((and (atom Arbore) (= K -1)) (list E))
		((atom Arbore) (list Arbore))
		(t (apply #'append (mapcar #'(lambda (L) (inlocuire L (- K 1) E)) Arbore)))
	)
)
