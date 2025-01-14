; adancime(Arbore) -> returneaza adancimea arborelui
;
; model matematic recursiv
; Arbore = (Radacina Subarbore1 Subarbore2)
; 
; adancime(Arbore) = 0							, Subarbore1 = Subarbore2 = null
; 		     1 + max(adancime(Subarbore1), adancime(Subarbore2) , altfel


(defun adancime(Arbore) 
  	(cond 	
	  	((null Arbore) 0)
		(t (+ 1 (max (adancime (cadr Arbore)) (adancime (caddr Arbore)))))		
	)
)


; numara_noduri(Arbore K)
;
; model matematic recursiv
; 
; Arbore = Radacina Subarbore1 Subarbore2
; numara_noduri (Arbore, K) = 
;	1 									, daca K = 0
;	numara_noduri (Subarbore1, K - 1) + numara_noduri (Subarbore2, K - 1) 	,altfel

(defun numara_noduri (Arbore K)
  	(cond
  		((eq K 0) 1)
		(t (+ (numara_noduri (cadr Arbore) (- K 1)) (numara_noduri (caddr Arbore) (- K 1))))

	)
)

; verifica (Arbore K)
; model matematic recursiv
;
; verifica (Arbore K) = t 					, daca numara_noduri(Arbore, K) % 2 = 0
;								si |adancime(Subarbore1) - adancime(Subarbore2)| < 2
;			nil 					, altfel

(defun verifica (Arbore K)
  	(cond 
	  	((and 
		   (evenp (numara_noduri Arbore K))
		   (> 2 
		      (abs 
			(- 
			  (adancime (cadr Arbore)) 
			  (adancime (caddr Arbore))
			)
		      )
		  ) 
		)t)

		(t nil)
	)
)


