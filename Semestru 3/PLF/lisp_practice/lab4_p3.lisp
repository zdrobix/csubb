;a) produsul a doi vectori

(defun produs (V W)
  	(cond
	  	((or (null V) (null W)) 0)
		(t (+ (* (car V) (car W)) (produs (cdr V) (cdr W))))
	)
)

;b) adancimea unei liste

(defun adancime (Lista) 
  	(cond 
	  	((null Lista) 1)
		((listp (car Lista)) (+ 1 (adancime (car Lista))))
		(t (adancime (cdr Lista)))
	)
)

;c) o functie care sorteaza fara pastrarea dublurilor o lista liniara

(defun elimina2 (Lista A) 
  	(cond 
	  	((null Lista) nil)
		((eq (car Lista) A) (elimina2 (cdr Lista) A))
		(t (cons (car Lista) (elimina2 (cdr Lista) A)))
	)
)

(defun sorteaza (Lista) 
  	(cond 
	  	((null Lista) nil)
		(t (cons (apply #'min Lista) (sorteaza (elimina2 (cdr Lista) (apply #'min Lista)))))
	)
)

;d) intersectia a doua multimi


