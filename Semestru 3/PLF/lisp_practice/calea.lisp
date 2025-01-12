
(defun exista (Elem Lista) 
  	(cond
	  	((null Lista) nil)
		((eq (car Lista) Elem) t)
		(t (exista Elem (cdr Lista)))
	)
)

(defun liniarizare (Lista) 
  	(cond
	  	((atom Lista) (list Lista))
		(t (apply #'append (mapcar #'liniarizare Lista)))
	)
)

(defun cale (Nod Arbore) 
  	(cond
	  	((and (listp Arbore) (eq Nod (car Arbore))) (list Nod))
		((exista Nod (liniarizare Arbore)) (cons (car Arbore) (apply #'append (mapcar (lambda (sub) (cale Nod sub)) (cdr Arbore)))))
		(t nil)
	)
)

(defun numara_nivel (Lista)
  	(cond
  		((atom Lista) 0)
		(t (+ 1 (apply #'+ (mapcar #'numara_nivel Lista))))
	)
)

