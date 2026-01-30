# TBO - projekt Blue Team

## Cel projektu

Celem projektu jest zasymulowanie bezpiecznego środowiska wytwórczego poprzez zaprojektowanie procesu CI/CD,
który przed zbudowaniem obrazu aplikacji automatycznie wymusza realizację testów bezpieczeństwa typu SAST,
SCA oraz DAST. Następnie należy udowodnić skuteczność wdrożonych mechanizmów poprzez próbę wstrzyknięcia
do nowej gałęzi kodu rzeczywistych podatności, co powinno skutkować wykryciem zagrożenia i zablokowaniem
wdrożenia wadliwej aplikacji.

---

## Opis Procesu CI/CD

Proces jest definiowany w pliku `.github/workflows/workflow.yml` i składa się z pięciu etapów, które muszą zostać zaliczone, aby obraz trafił do rejestru.

### 1. Testy Jednostkowe (Unit Tests)

* **Narzędzie:** Maven (JUnit/AssertJ).

Weryfikuje poprawność logiki biznesowej. Pipeline uruchamia testy przed skanami bezpieczeństwa, aby zapewnić, że bazowa funkcjonalność jest stabilna.

### 2. SCA (Software Composition Analysis)

* **Narzędzie:** Trivy.

Skanowanuje zależności (plik `pom.xml`) w poszukiwaniu znanych podatności (CVE). Wykorzystujemy plik `.github/config/.trivyignore` do zarządzania znanymi wyjątkami.

### 3. SAST (Static Application Security Testing)

* **Narzędzie:** Semgrep.

Analizuje kod źródłowy "w spoczynku". Skaner poszukuje wzorców niebezpiecznego kodu (np. użycia surowych zapytań SQL). Wykorzystujemy `.semgrepignore` w celu uniknięcia wykrywania znanych podatności w kodzie.

### 4. DAST (Dynamic Application Security Testing)

* **Narzędzie:** OWASP ZAP.

Testuje działanie aplikacji. Pipeline buduje tymczasowy obraz Docker, uruchamia go w izolowanej sieci i przeprowadza atak typu *Baseline Scan*. 
Wykorzystujemy plik `zap-ignore.conf` do wyciszenia znanych błędów konfiguracyjnych.

### 5. Build & Push

* **Cel:** Jeśli wszystkie powyższe kroki zakończą się sukcesem, obraz jest budowany i wypychany do GitHub Container Registry (GHCR).



---

## Zadanie 2: Weryfikacja Bezpieczeństwa (Branch: `security-test`)

Na gałęzi `feature/security-test` wprowadzono celowo złośliwy kod w klasie `VulnerabilityController.java`, aby sprawdzić skuteczność potoku.

### Wykryte Podatności:

1. **SQL Injection (`10202` / Semgrep: `java.sql.Statement`)**:

Kod wykorzystuje konkatenację ciągów znaków do budowania zapytań SQL. Pozwala to napastnikowi na przejęcie kontroli nad bazą danych poprzez podanie spreparowanego parametru `name`.



2. **Command Injection (Semgrep: `java.lang.Runtime`)**:

Metoda `pingHost` przekazuje nieoczyszczone dane wejściowe bezpośrednio do powłoki systemu operacyjnego (`Runtime.getRuntime().exec`). Pozwala to na zdalne wykonanie dowolnych komend na serwerze (RCE).



### Podsumowanie testu:

Zgodnie z załączonym zrzutem ekranu, **Pipeline poprawnie przerwał budowanie obrazu**. Kod nie trafił do rejestru, co dowodzi, że system automatycznie chroni środowisko przed wdrożeniem krytycznych podatności.
<img width="1051" height="632" alt="Image" src="https://github.com/user-attachments/assets/e43bac29-9845-47c0-a875-99a593a7c85c" />

Dodatkowo, w wyniku działania procesu CI/CD do Pull requesta dopisywane są komentarze, które wskazują miejsca zawierające podatności. Można je zobaczyć [tutaj](https://github.com/MalkowskiM/Projekt-Blue-team/pull/6).


## Credits
* Java application - [GitHub Repo](https://github.com/pedrohenriquelacombe/spring-thymeleaf-crud-example)
