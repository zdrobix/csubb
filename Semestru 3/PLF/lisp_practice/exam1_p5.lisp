;5. se da o lista neliniaraa.
;determinati numarul de subliste de la orice nivel pentru care primul atom numeric la orice nivel este numar par

;model matematic:
;liniarizare(l1..ln) = l1 				      ,daca l1 - numeric
;		       liniarizare(l1) U liniarizare(l2..ln)  ,daca l2 - lista

(defun liniarizare (Lista) 
  	(cond
		((numberp Lista) (list Lista))
		((atom Lista) nil)
		(t (apply #'append(mapcar #'liniarizare Lista)))
	)
)

(defun subliste (Lista) 
	  (cond 
	    	((atom Lista) 0)
		((oddp (car (liniarizare Lista))) (apply '+ (mapcar #'subliste Lista)))
		(t (+ 1 (apply '+ (mapcar #'subliste Lista))))
	   )
)

