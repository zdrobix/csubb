;a) o functie care selecteaza al n-lea element al unei liste /nil daca nu exista

(defun select (Lista Poz N)
  	(cond
	  	((null Lista) nil)
		((= Poz N) (car Lista))
		(t (select (cdr Lista) (+ Poz 1) N))
	)
)

;b) o functie care verifica daca un atom e membru al unei liste (nu neaparat liniare)

(defun verifica (Lista A)
  	(cond
	  	((null Lista) nil)
		((listp (car Lista)) (or (verifica (car Lista) A) (verifica (cdr Lista) A)))
		((eql (car Lista) A) t)
		(t (verifica (cdr Lista) A))
	)
)

;c) o lista a tututri sublistelor unei lista


;d) o lista care transforma o lista liniara intr-o multime

(defun elimina (Lista A)
  	(cond
	  	((null Lista) nil)
		((eql (car Lista) A) (elimina (cdr Lista) A))
		(t (append (list (car Lista)) (elimina (cdr Lista) A)))
	)
)

(defun transforma (Lista) 
 	(cond 
	  	((null Lista) nil)
		(t (cons (car Lista) (transforma (elimina (cdr Lista) (car Lista)))))
	)
)
