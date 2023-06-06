# Solarsimulator

Der Solarsimulator ist ein interaktives Lernspiel, das den Einsatz verschiedener privater Elektrogeräte simuliert und
deren Auswirkungen auf die Stromproduktion einer privaten Fotovoltaikanlage optimiert. Das Spiel berücksichtigt
realistische Stromkonsumbedürfnisse eines Privathaushalts und bietet den Spielern die Möglichkeit, ihr Wissen über
erneuerbare Energien, Fotovoltaikanlagen und den Einfluss von Elektrogeräten auf den Energieverbrauch spielerisch zu
erweitern.

## Team Solar Impact:

William Cruz (WC): william.cruz@students.fhnw.ch

Leonida Gjidoda (LG): leonida.gjidoda@students.fhnw.ch

Fabian Studer (FS): fabian.studer@students.fhnw.ch

Kanusjan Paramarajah (KP): kanusjan.paramarajah@students.fhnw.ch

Nick Koch (NC): nick.koch@students.fhnw.ch

[Team-Website](https://web0.fhnw.ch/ht/informatik/ip12/23vt/solaranlage/index.html)

## Maven Site Report anzeigen:

[Maven Site Reports anzeigen](https://ip12-22vt.pages.fhnw.ch/ip12-22vt_solaranlage/solaranlage_prod)

## Installation:

Um das Projekt zu installieren, befolgen Sie bitte diese Schritte:

1. Klonen Sie das Repository auf Ihren lokalen Computer:

   ```shell
   git clone https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_solaranlage/solaranlage_prod.git
   ```

2. Öffnen Sie das Projekt in Ihrer bevorzugten Java-IDE.
3. Bauen und starten Sie das Projekt mit den bereitgestellten Maven-Skripten:
    - `mvn clean package`
    - `mvn site`
    - `mvn javafx:run`
    - `java -jar target/simulator-22.2-SNAPSHOT.jar`

## Verwendung:

Der Solarsimulator ermöglicht es Ihnen, verschiedene Szenarien und Einstellungen im Zusammenhang mit einer Solaranlage
zu simulieren. Einige der Funktionen des Simulators sind:

- Anpassung der Größe und Leistungsfähigkeit der Solarpanels
- Konfiguration der Leistung verschiedener Geräte
- Experimentieren mit verschiedenen Leistungsplänen und Zeitplänen

Um den Simulator zu verwenden, starten Sie einfach die Anwendung und verwenden Sie die bereitgestellte
Benutzeroberfläche, um die verschiedenen Einstellungen anzupassen und mit verschiedenen Szenarien zu experimentieren.

### Anleitung zum Starten und Aktualisieren der Raspberry Pi 4

Um Ihren Raspberry Pi 4 zu starten, folgen Sie diesen einfachen Schritten:

1. Stellen Sie sicher, dass Sie ein kompatibles Netzteil verwenden. Ein Raspberry Pi 4 erfordert ein USB-C-Netzteil mit
   mindestens 3A Ausgangsstrom.
2. Schließen Sie Ihren Raspberry Pi an ein Display an, entweder über einen HDMI- oder einen DVI-Anschluss.
3. Schließen Sie eine Tastatur und eine Maus an die USB-Ports des Raspberry Pi an.
4. Stecken Sie die Micro-SD-Karte mit dem installierten Betriebssystem in den dafür vorgesehenen Steckplatz ein.
5. Schließen Sie das Netzteil an den Raspberry Pi und an eine Steckdose an. Ihr Raspberry Pi sollte jetzt starten und
   das Betriebssystem booten.

Um Ihr Solar Simulator-Software-Repository zu aktualisieren, können Sie GitLab verwenden. Folgen Sie diesen Schritten,
um Ihr Repository zu aktualisieren:

1. Stellen Sie sicher, dass Sie über eine Internetverbindung verfügen.
2. Öffnen Sie ein Terminalfenster auf dem Raspberry Pi.
3. Navigieren Sie zu dem Verzeichnis, in dem Ihr Solar Simulator-Repository liegt.
4. Geben Sie den folgenden Befehl ein, um Ihr Repository zu aktualisieren: `git pull`
5. GitLab wird die neuesten Updates aus Ihrem Repository herunterladen und auf Ihrem Raspberry Pi installieren.

Stellen Sie sicher, dass Sie diese Schritte regelmäßig wiederholen, um Ihre Solar Simulator-Software auf dem neuesten
Stand zu halten.

## Rahmenbedingungen:

Die Rahmenbedingungen für den Solarsimulator sind
in [diesem Dokument](https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_solaranlage/docu/-/blob/main/rahmenbedingungen.adoc)
festgehalten. Bitte lesen Sie das Dokument für
Informationen zu den Hardware- und Softwareanforderungen, den Umgebungsbedingungen, den Bedingungen für die Verwendung
des Produkts auf Holz und unter Berücksichtigung des künstlichen Grases sowie anderen wichtigen Aspekten, die die
zuverlässige Nutzung des Produkts gewährleisten.

## Mitwirkung:

Wenn Sie zum Solarsimulator-Projekt beitragen möchten, befolgen Sie bitte diese Richtlinien:

1. Forken Sie das Projekt in Ihr eigenes Repository.
2. Erstellen Sie einen neuen Branch für Ihre Änderungen.
3. Machen Sie Ihre Änderungen und committen Sie sie mit klaren und beschreibenden Commit-Nachrichten.
4. Pushen Sie Ihre Änderungen in Ihr geforktes Repository.
5. Erstellen Sie einen Pull Request zum Haupt-Repository.

## Dankeschön:

Dieses Projekt wurde vom Solar Impact Team bei der Primeo Energie AG entwickelt. Ein besonderer Dank geht an die FHNW,
unser Coach-Team und insbesondere an unsere Projektcoachin Andrea Kennel für ihre Unterstützung und Anleitung während
des gesamten Entwicklungsprozesses. Wir möchten uns auch bei allen anderen beteiligten Personen bedanken, die wertvolle
Tipps und Unterstützung geleistet haben.

## Lizenz:

Dieses Projekt ist unter der MIT-Lizenz lizenziert. Weitere Informationen finden Sie in
der [LICENSE-Datei](https://ip12-22vt.pages.fhnw.ch/ip12-22vt_solaranlage/solaranlage_prod/licenses.html).

## Code-Qualität-Anforderungen:

Die Anforderungen an die Source Code Qualität sind in
den [Code-Qualität-Anforderungen](https://gitlab.fhnw.ch/ip12-22vt/ip12-22vt_solaranlage/docu/-/blob/main/coding_conventions.adoc)
festgelegt. Bitte lesen Sie das Dokument für weitere Informationen über die Erwartungen an den Code.

Wir freuen uns über Beiträge und Vorschläge zur Verbesserung der Code-Qualität gemäß den festgelegten Anforderungen.

Wenn Sie Fragen oder Anmerkungen haben, kontaktieren Sie bitte unser Team unter den oben genannten E-Mail-Adressen.

Vielen Dank für Ihr Interesse am Solarsimulator-Projekt!
