<h1>
  <span style="font-size: 2rem;">🐾 Pet Explorer</span>
  <span style="font-size: 1.2rem; font-style: italic;">– Server Java al aplicației destinate tuturor deținătorilor de animale de companie</span>
</h1>

[Link catre backend repo](github.com/mariusvicol/PetExplorerBackend)
[Link catre frontend repo](github.com/mariusvicol/PetExplorerFrontend)


## 📌 Descriere:
Acest repository conține o aplicație dezvoltată în Java ce reprezintă partea de **server** al aplicației interactive
cu locații de interes pentru deținătorii de animale de companie, precum:
- Farmacii veterinare
- Petshops
- Parcuri
- Pensiuni canine
- Saloane de toaletaj
- Animale pierdute
- Locații favorite

Datele sunt gestionate într-o bază de date PostgreSQL.

---

## 🛠️ Tehnologii folosite

- **Java 17+**
- **Spring Framework** (Spring Boot, Spring Web)
- **Hibernate** - pentru maparea obiect-relațională
- **WebSockets** - pentru notificarea clienților și comunicare în timp real
- **REST Services** - pentru manipularea și expunerea datelor către client
- **PostgreSQL** și **Supabase** - pentru baza de date relațională și hostarea acesteia
- **Gradle** - pentru build și gestionarea dependențelor

---

## 🗂️ Structura aplicației

- `PetExplorerModel`
    * `/configs` - clasele de configurări pentru Hibernate și hostarea pe Supabase
    * `/domain` - clasele care mapează entitățile din baza de date
    * `/utils` - utilități, precum inițializarea conexiunii la baza de date prin Hibernate, cât și clasele DTO utilizate la transmiterea datelor de la server la client


- `PetExplorerNetworking` - conține clasele ce implementează endpoint-uri REST și WebSocket pentru client
- `PetExplorerPersistence` - conține interfețele și implementările corespunzătoare pentru accesul la datele din baza de date
- `application.properties` – configurarea conexiunii la baza de date și alte setări

---

## 🧩 Entități principale

Fiecare entitate este mapată la o tabelă în baza de date:

| Entitate           | Descriere                                                       |
|--------------------|-----------------------------------------------------------------|
| `User`             | utilizatori ai aplicației                                       |
| `Farmacie`         | locații de tip farmacie                                         |
| `Magazin`          | magazine veterinare                                             |
| `Parc`             | parcuri pentru recreere                                         |
| `PensiuneCanina`   | locații pentru cazarea animalelor de companie                   |
| `CabinetVeterinar` | cabinete veterinare                                             |
| `Salon`            | saloane pentru animale                                          |
| `AnimalPierdut`    | raportări ale animalelor pierdute                               |
| `LocatieFavorita`  | locație existentă în celelalte tabele, asociată unui utilizator |

**Fiecare locație** include detalii precum:
- nume
- coordonate (latitudine, longitudine)
- telefon de contact
- status deschis/non-stop

Fiecare entitate are asociat un controller dedicat
pentru expunerea datelor (GET, POST etc).

--- 

## 📡 API-uri REST
Exemple de API-uri REST prin care se pot accesa datele:

| Metodă | Endpoint              | Descriere                                 |
|--------|-----------------------|-------------------------------------------|
| GET    | /api/cabinete         | Obține toate cabinetele veterinare        |
| POST   | /api/animale_pierdute | Raportează un animal pierdut              |
| GET    | /api/locatii/{id}     | Obține locația favorită cu ID-ul transmis |

---

## 🔄 WebSocket și Sistemul de notificări în timp real
Aplicația **PetExplorer** oferă un mecanism de **notificări în timp real** prin **WebSocket**, utilizând protocolul **STOMP**.
Acesta permite clientului să primească automat actualizări în momentul in care apar evenimente relevante (ex: modificarea statusului
unui animal pierdut, adăugarea unui animal pierdut/găsit), **fără a reîncărca pagina sau a face pooling repetat**.

### 🔧 Configurare
WebSocket-ul este configurat în aplicație prin clasa `WebSocketConfig.java`, folosind `@EnableWebSocketMessageBroker`.
Brokerul intern gestionează topicurile de tip:
- `/topic` - pentru **mesaje difuzate tuturor clienților conectați**
- `/queue` - pentru **mesaje private**, trimise unui anumit utilizator
- `/user` - prefixul pentru mesageria direcțională


### 📢 Exemple de notificări
Notificările sunt gestionate prin clasa `NotificationService`, care trimite evenimentele folosind `SimpMessagingTemplate`.

| Eveniment                         | Topic                                 | Descriere                                                                |
|-----------------------------------|---------------------------------------|--------------------------------------------------------------------------|
| 🔔 Animal pierdut **raportat**    | `/topic/animale-pierdute`             | notifică toți utilizatorii în legătură cu un nou animal pierdut raportat |
| ✔️ Animal marcat ca găsit         | `/topic/animale-pierdute/resolved`    | notifică toți utilizatorii în legătură cu găsirea unui animal pierdut    |
| 👤 Notificare către un utilizator | `/user/queue/notifications`           | trimitere privată                                                        |
