;se da o lista neliniara si se cere inlocuirea valorii numerice pare cu numarul natural succesor

(defun inlocuieste2 (Lista)
  	(cond
	  	((lisp (car Lista)) (append (inlocuire (car Lista)) (inlocuire (cdr Lista))))
		((and (numberp (car Lista)) (evenp (car Lista))) (append (list (+ (car Lista) 1)) (inlocuire (cdr Lista))))
		(t (append (list (car Lista)) (inlocuire (cdr Lista))))
	)
)
