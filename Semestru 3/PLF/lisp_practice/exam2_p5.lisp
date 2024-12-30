;numarul de subliste de la orice nivel pentru care atomul numeric maxim de pe nivelurile impare este par

(defun liniarizare (Lista) 
  	(cond
		((numberp Lista) (list Lista))
		((atom Lista) nil)
		(t (apply #'append(mapcar #'liniarizare Lista)))
	)
)

