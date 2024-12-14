;reprezentarea arbor: (nod (lista-subarbore-1) (lista-subarbore-2))
;
;13) Sa se afiseze calea de la radacina pana la un nod x dat.
;
; model matematic:
; afiseaza_cale(X, L1..Ln) =
;	() 					 ,daca n = 0
;	X  					 ,daca L1 = X
;	L1 U afiseaza_cale(X, L2)	         ,daca gaseste(X, L2) = true
;	L1 U afiseaza_cale(X, L3)                ,daca gaseste(X, L2) = false


;cautare la toate nivelele a unui element intr-o lista
; model matematic
; gaseste(X, L1..Ln) = 
;	Fals					,daca n = 0
;	gaseste(X, L1) sau gaseste(X, L2..Ln) 	,daca L1 e lista
;	True					,daca L1 = X

; 
; afiseaza_cale (K (A (B (C D) (E F)) (G (H J) (K O))))
; A u afiseaza_cale(K (G (H J) (K O)))
; A u G u afiseaza_cale(K (K O))
; A u G u K

(defun gaseste (X L) 
  	(cond
	  	((null L) nil)
		((not(listp L)) (equal X L))
		((listp (car L)) 
		 	(or 
			  (gaseste X (car L)) 
			    (gaseste X (cdr L))
			  )
			)
		((equal X (car L)) t)
		(t(gaseste X (cdr L)))
	)
)


(defun afiseaza_cale (X L) 
	(cond
	  	((null L) NIL)
		((and (not (listp L)) (equal X L)) (list X))
	  	((equal X (car L)) (list X))
		((gaseste X (cadr L)) (cons (car L) (afiseaza_cale X (cadr L))))
		(t (cons (car L) (afiseaza_cale X (caddr L))))
	)
		
)

