class Gramatica:
    def __init__(self):
        self.neterminale = set()
        self.terminale = set()
        self.simbol_start = None
        self.productii = {}
    
    def citeste_din_fisier(self, nume_fisier):
        try:
            with open(nume_fisier, 'r', encoding='utf-8') as f:
                linii = [linie.strip() for linie in f.readlines() if linie.strip()]
                
                self.neterminale = set(linii[0].split())
                self.terminale = set(linii[1].split())
                self.simbol_start = linii[2].strip()
                for i in range(3, len(linii)):
                    self.adauga_productie(linii[i])
                
                return True
        except Exception as e:
            print(f"Eroare la citirea fisierului: {e}")
            return False
    
    def adauga_productie(self, productie_str):
        if '->' not in productie_str:
            return
        
        parts = productie_str.split('->')
        stanga = parts[0].strip()
        dreapta = parts[1].strip()
        
        alternative = [alt.strip() for alt in dreapta.split('|')]
        
        if stanga not in self.productii:
            self.productii[stanga] = []
        
        self.productii[stanga].extend(alternative)
    
    def este_regulara(self):
        if not self.productii:
            return False
        
        tip_dreapta = 0 
        tip_stanga = 0  
        productii_simple = 0  
        
        for neterminal, liste_productii in self.productii.items():
            if neterminal not in self.neterminale:
                return False
            
            for productie in liste_productii:
                if productie == '' or productie == 'eps':
                    productii_simple += 1
                    continue
                
                if len(productie) == 1:
                    if productie in self.terminale:
                        productii_simple += 1
                    else:
                        return False
                
                elif len(productie) == 2:
                    primul = productie[0]
                    al_doilea = productie[1]
                    
                    if primul in self.terminale and al_doilea in self.neterminale:
                        tip_dreapta += 1
                    elif primul in self.neterminale and al_doilea in self.terminale:
                        tip_stanga += 1
                    else:
                        return False
                
                else:
                    return False
        
        if tip_dreapta > 0 and tip_stanga > 0:
            return False
        
        if tip_dreapta > 0 or tip_dreapta > 0:
            return True
        elif tip_stanga > 0:
            return True
        elif productii_simple > 0:
            return True
        
        return False
    
    def afiseaza(self):
        print("-" * 50)
        print("GRAMATICA:")
        print("-" * 50)
        print(f"Neterminale: {', '.join(sorted(self.neterminale))}")
        print(f"Terminale: {', '.join(sorted(self.terminale))}")
        print(f"Simbol start: {self.simbol_start}")
        print("\nProductii:")
        for neterminal in sorted(self.productii.keys()):
            for productie in self.productii[neterminal]:
                prod_str = productie if productie else 'ε'
                print(f"  {neterminal} -> {prod_str}")
        print("-" * 50)


if __name__ == "__main__":
    g = Gramatica()
    
    if g.citeste_din_fisier('gramatica1.txt'):
        g.afiseaza()
        
        este_reg = g.este_regulara()
        
        if este_reg:
            print("Gramatica este regulara")
        else:
            print("Gramatica nu este regulara")
    
    