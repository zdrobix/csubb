
(defun liniarizare (Lista)
  	(cond
		((numberp Lista) (list Lista))
		((atom Lista) nil)
		(t (apply #'append(mapcar #'liniarizare Lista)))
	)
)

(defun minim (Lista M)
  	(cond 
	  	((null Lista) M)
		((and (numberp (car Lista)) (< (car Lista) M)) (minim (cdr lista) (car Lista)))
		(t (minim (cdr Lista) M))
	)
)

(defun numara (Lista) 
  	(cond
	  	((null Lista) 0)
		((and (listp (car Lista)) (oddp (minim (liniarizare Lista) 9999))) (+ 1 (numara (cdr Lista))))
		(t (numara (cdr Lista)))
	)
)

