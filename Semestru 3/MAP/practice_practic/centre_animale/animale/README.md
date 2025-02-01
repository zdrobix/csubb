## Se doreste o aplicatie care sa permita gestionarea anmalelor din centrele de adoptii.
la pornirea aplicatiei se va deschide cate o fereasta pentru fiecare centru de adoptie. fiecare fereastra afiseaza
numele locatia si capacitatea centrului, o lista cu animalele din acel centru

### se vor defini clasele:
AdoptionCentre : {id : int, name: String, location:String, capacity:int}
Animal : {id :int, name : String, centreId:int, type: Enum {DOG, CAT, etc.}

### Fiecare fereastra afiseaza procentul actual de ocupare al centrului:
Daca un centru are 10 animale si o capacitate de 20, se afiseaza occupancy : 50%

### Fiecare fereastra contine un combobox "Animal Type" prin care permite utilizatorului sa filtreze animalele afisate in functie de tipul lor.
selectarea unui tip de animal actualizeaza tabelul pentru a afisa doar animalele cu tipul dat din acel centru. Filtrarea se va face la nivel de sql query
Selectarea optiunii "all types" reafiseaza lista completa de animale din acel centru.

### Fiecare centru poate solicita transferul unui animal catre un alt centru din aceeasi locatie
un buton Request Transfer apare langa fiecare animal
La apasarea butonului, o cerere de transfer este trimisa tuturor centrelor din aceeasi locatie cu cea a centrului curent.
filtrarea se face la nivel de sql query
in aceste centre apare o notificare "Center name requested to transfer Animal name" cu doua butoane Accept si Ignore

### Centrele pot alege sa accepte sau sa ignore cererile de transfer primite.
Daca se apasa butonul Accept animalului este relocat in noul centru ( i se schimba centreId)
Centrul din care s a facut transferul si cel in care s a facut transferul isi actualizeaza in timp real lista de animale si rata de ocupare
Daca se apasa butonul Ignore, notificarea dispare doar din centrul curent.