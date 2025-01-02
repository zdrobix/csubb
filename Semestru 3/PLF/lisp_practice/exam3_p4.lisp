;o lista neliniara. sa se inlocuiasca toti atomii de la nivelul k cu 0. nivelul superficial = 1

;model matematic 
;inlocuieste (l1..ln, k, nk) =
;	[] 								,daca n = 0
;	0 U inlocuieste (l2..ln, k, nk),  				,daca k = nk si l1 nu e lista
;	inlocuieste(l1, k, nk + 1) U inlocuieste(l2..ln, k, nk) 	,daca l1 e lista


(defun inlocuieste (Lista K Nivel) 
  	(cond 
	  	((null Lista) nil)
		((and (= K Nivel) (atom (car Lista))) (cons'0 (inlocuieste (cdr Lista) K Nivel)))	
		((atom (car Lista)) (cons (car Lista) (inlocuieste (cdr Lista) K Nivel)))
		(t (cons (inlocuieste (car Lista) K (+ 1 Nivel)) (inlocuieste (cdr Lista) K Nivel)))
	)
)
