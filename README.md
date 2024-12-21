<<<<<<< Updated upstream
TODOS

de implementat FinalPage

de facut enemyfight
=======
# Proiect Java: Joc de Aventură RPG

## Descriere generală
Acest proiect reprezintă un joc de tip RPG (Role-Playing Game) dezvoltat în Java. Jocul include mecanisme de luptă, gestionarea personajelor, inamicilor, vrăjilor, precum și un sistem de gestionare a utilizatorilor prin conturi și acreditive. Proiectul este structurat pe mai multe pachete pentru a organiza funcționalitățile și componentele principale ale jocului.

## Structura proiectului

### 1. Pachetul `account`
- **Fișiere:**
    - `Account.java`: Clasă ce gestionează informațiile contului unui utilizator, inclusiv acreditivele și personajele asociate.
    - `Credentials.java`: Clasă pentru gestionarea datelor de autentificare (email și parolă).

### 2. Pachetul `entities`
- **Fișiere:**
    - `Battle.java`: Interfață pentru entități implicate în lupte.
    - `Character.java`: Clasă abstractă pentru personajele controlate de utilizator.
    - `Entity.java`: Clasă abstractă pentru entități generice din joc (personaje și inamici).
    - `Enemy.java`: Clasă pentru inamicii din joc.
    - `Mage.java`, `Rogue.java`, `Warrior.java`: Clase ce reprezintă tipurile de personaje controlabile (Mag, Hoț, Războinic).

### 3. Pachetul `exceptions`
- **Fișiere:**
    - `InvalidChooseOption.java`: Excepție aruncată pentru opțiuni invalide.
    - `InvalidMoveException.java`: Excepție aruncată pentru mișcări incorecte.
    - `InvalidSpellException.java`: Excepție pentru vrăji invalide.
    - `NotEnoughManaException.java`: Excepție pentru lipsa manei suficiente pentru vrăji.

### 4. Pachetul `fileio`
- **Fișiere:**
    - `Input.java`: Clasă pentru gestionarea datelor de intrare, inclusiv încărcarea conturilor utilizatorilor.

### 5. Pachetul `game`
- **Fișiere:**
    - `Game.java`: Clasă principală pentru gestionarea jocului, inclusiv grila și logica jocului.
    - `Grid.java`: Clasă pentru reprezentarea grilei jocului.
    - `Cell.java`: Clasă pentru celulele grilei.
    - `CallEntityType.java`: Enum pentru tipurile de entități din grilă (PLAYER, ENEMY, etc.).
    - `Directions.java`: Enum pentru direcții (North, East, South, West).
    - `EnemyFight.java`: Clasă ce gestionează luptele dintre personajul principal și inamici.

### 6. Pachetul `spells`
- **Fișiere:**
    - `Spell.java`: Clasă abstractă pentru vrăji.
    - `Fire.java`, `Ice.java`, `Earth.java`: Clase ce implementează diferite tipuri de vrăji.

### 7. Fișiere principale
- `Main.java`: Punctul de intrare în aplicație.
- `Test.java`: Clasă pentru testarea funcționalităților jocului.

## Funcționalități
- Gestionarea conturilor utilizatorilor (autentificare, salvarea progresului).
- Crearea și evoluția personajelor (Mag, Războinic, Hoț).
- Generarea și lupta cu inamici.
- Utilizarea vrăjilor în timpul luptelor.
- Grilă dinamică de joc cu celule și mecanici de navigare.

## Cerințe pentru rulare
- Java Development Kit (JDK) 8 sau mai recent.
- O bibliotecă JSON precum `Jackson` pentru gestionarea datelor de intrare.

## Instrucțiuni de rulare
1. Clonați sau descărcați proiectul.
2. Compilați fișierele Java utilizând comanda:
   ```bash
   javac -d bin $(find . -name "*.java")
   ```
3. Rulați aplicația principală utilizând comanda:
   ```bash
   java -cp bin org.game.Main
   ```

## Structura grilei
Grila jocului este compusă din celule, fiecare având un tip specific (VOID, PLAYER, ENEMY, SANCTUARY, PORTAL). Acestea definesc logica de joc și posibilele acțiuni în funcție de poziția curentă a jucătorului.

## Extensibilitate
- Adăugarea de noi tipuri de vrăji sau personaje este ușor de realizat extinzând clasele existente.
- Logica de joc poate fi extinsă prin adăugarea unor noi mecanici în clasa `Game` sau prin implementarea unor noi entități în pachetul `entities`.

## Contribuitori
Acest proiect a fost dezvoltat pentru a demonstra conceptele de bază din programarea orientată pe obiecte și utilizarea excepțiilor personalizate.
>>>>>>> Stashed changes
