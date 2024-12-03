;12.a) functie care intoarce produsul scalar a doi vectori
;v1 v2 - vectori
;daca vectorii nu au aceasi lungime -> eroare
(defun produs_scalar(v1 v2)
  	(if (/= (length v1) (length v2))
    		(error "Format invalid!")
    		(reduce #'+ 
			(mapcar #'* v1 v2)
		)
	)
)


;12.b) maximul atomilor numerici dintr-o lista, de la orice nivel
;model matematic 
; maxim(l1..ln) = 0                            - daca n = 0
;                 max(l1, maxim(l2..ln)        - daca l1 atom numeric
; 	          maxim(l2..ln)                - daca l1 este atom
;                 max(maxim(l1), maxim(l2..ln) - altfel
(defun maxim_lista(lista)
	(if (null lista)
	  -10000
	  (if (listp (car lista))
		     (max 
		      		(maxim_lista 
				  	(car lista)
				)
			   	(maxim_lista 
				  	(cdr lista)
				)
			)
		      (max 
				(car lista)
			   	(maxim_lista 
				  	(cdr lista)
				)
			)
			)
	  )
)

(defun maxim_lista2(lista) 
  	(cond 
		((null lista) -1000)
		((listp (car lista)) 
			 (max (maxim_lista2(car lista)) (maxim_lista2(cdr lista))))
		((numberp (car lista))
			  (max (car lista) (maxim_lista2 (cdr lista))))
		(T(maxim_lista2(cdr lista)))
		
	  )
	)

;12.c) o functie care intoarce lista permutarilor unei liste date
;model matematic
; permutari(l1..ln) = []                          - daca n = 0
; 		      [l1 U permutari(l2..ln)] U  
;                     permutari(l2..ln)           - daca n > 0
(defun permutari(lista)
  	(if (null lista)
		'(())
		(append 
		  	(insert-all 
			  	(car lista)
				(cdr lista)
			)
			(permutari
			  	(cdr lista)
			)
		)
	)
)

;insereaza elementul x pe fiecare pozitie din lista lista
;model matematic
; insert-all(x, l1..ln) = (x)                                - daca n = 0
;                         [xl1..ln] U insert-all(x, l2..ln)  - daca n > 0
(defun insert-all (x lista) 
  	(if (null lista) 
	  	(list 
		  	(list x)
		)
		(cons 
		  	(cons x lista)
		      	(insert-all x 
				    (cdr lista)
			)
		)
	)
)
		


;12.d) o functie care intoarce T daca o lista are nr par de elem, si NIL in caz contrar. fara sa se numere elementele listei
;model matematic
; functie(l1..ln) = T                  - daca n = 0
;                   NIL                - daca n = 1
;                   functie(l2..ln)    - daca n > 1
(defun functie (lista)
	(if (null lista)
		'T
		(if (null (cdr lista))
			'NIL
			(functie(cdr 
				  	(cdr lista)
				)
					
		  	)
	  	)
 	)
)
