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

; model matematic
; produs_scalar2(l1..ln, v1..vn) 
;     0,           daca n = 0
;     l1 * v1 + produs_scalar2(l2..ln, v2..vn)
;
(defun produs_scalar2 (v1 v2) 
  	(cond 
	  ((null v1) 0)
	  ((null v2) 0)
	  ((+ (* (car v1) (car v2)) (produs_scalar2 (cdr v1) (cdr v2))))
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
; permutari(l1..ln) = []                           - daca n = 0
; 		      insert(l1, X)                - daca n > 0	  
;                        X : permutari(l2..ln)
(defun permutari (lista)
	(cond
		((null lista) '(()))
		((null (cdr lista)) (list (car lista)))
		(T	
			


		)
	)
)


;model matematic
; insert(e, l1..ln, p) = [e]                        - daca n = 0
;		        
;

(defun insert(x lista nr) 
	(cond 
		((= nr 0) (list lista))
		(T
			(cons x (insert x (cons (cdr lista) (car lista)) (- nr 1))				
		  
		)
	)
)


;12.d) o functie care intoarce T daca o lista are nr par de elem, si NIL in caz contrar. fara sa se numere elementele listei
;model matematic
; functie(l1..ln) = T                  - daca n = 0
;                   NIL                - daca n = 1
;                   functie(l3..ln)    - daca n > 1
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
