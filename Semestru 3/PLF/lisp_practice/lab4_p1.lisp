;a) sa se insereze intr-o lista liniara un atom A dat dupa al 2-lea, 4-lea, 6-lea element

(defun inserare (Lista A Poz)
  	(cond
		((null Lista) nil)
		((oddp Poz) (append (list (car Lista)) (inserare (cdr Lista) A (+ Poz 1))))
		(t (append (list A) (inserare Lista A (+ Poz 1))))
	)
)

;b) o functie care obtine dintr-o lista data lista tututor atomilor care apar, pe orice nivel, dar in ordine inversa

(defun atomi (Lista) 
  	(cond 
		((null Lista) nil)
		((atom (car Lista)) (append (atomi (cdr Lista)) (list (car Lista))))
		(t (append (atomi (cdr Lista)) (atomi (car Lista))))
	)
)

;c) o functie care intoarce cel mai mare divizor comun ar numerelor dintr-o lista neliniara

(defun divizor (A B)
  	(cond 
	  	((= B 0) A)
		(t (divizor B (mod A B)))
	)
)
	  	

(defun divizor_lista (Lista Divizor)
  	(cond 
	  	((null Lista) Divizor)
		((= Divizor 1) 1)
		((numberp(car Lista)) (divizor_lista (cdr Lista) (divizor Divizor (car Lista))))
		(t (divizor Divizor (divizor_lista (car Lista) 0)))
	)
)

;d) o functie care determina numarul de aparitii ale unui atom dat intr-o lista neliniara

(defun aparitie (Lista A)
  	(cond
		((null Lista) 0)
		((eql (car Lista) A) (+ 1 (aparitie (cdr Lista) A)))
		((listp (car Lista)) (+ (aparitie (car Lista) A) (aparitie (cdr Lista) A)))
		(t (aparitie (cdr Lista) A))
	)
)
