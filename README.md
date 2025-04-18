# Projekt GivMig
En forsimplet version af ønskeskyen hvor du kan
1. Oprette og slette brugere
2. Oprette, redigere og slette ønskelister
3. Oprette, redigere, slette og reservere ønsker

## Installation af kode
  Tilføj linket til vores GitHub projekt, i jeres IDE, 
  Link : https://github.com/safirestorm/Projekt2_Gruppe5.git

## For at køre programmet lokalt
Hvis du ønsker at køre applikationen lokalt skal du:

1. Opret en lokal MySQL-database med navnet givmig
2. Kør SQL-scriptet i projektet (src/main/resources/sql/cleanDataBaseScript.sql)
3. I application.properties, tilføj dine egne databaseoplysninger:

![carbon (1)](https://github.com/user-attachments/assets/94a94d9e-cebc-48b8-b117-ab5170f9f905)

## For at bruge programmet online
Bare brug dette link : givmig-dpc5a4dwauckbsga.swedencentral-01.azurewebsites.net
## Hvad består vores kode af?
1. Vi bruger Java 21
2. Vi bruger frameworket Spring Boot, se pom.xml for dependencies
3. Vi bruger HTML og CSS

Vores kode er bygget op med en MVC-model, hvor Model repræsenterer domænelogikken den findes i java mappen, View er grænsefladen og findes under mappen resources, og Controller fungerer som bindeledslogik mellem brugerinput og systemets logik og fines i java mappen

### Hvordan bruger jeg programmet online, som aktiv bruger
1. Åben vores webapp på linket : givmig-dpc5a4dwauckbsga.swedencentral-01.azurewebsites.net
2. Tryk på "OPRET EN NY BRUGER"
3. Udfyld formularen og tryk submit
4. Log ind med din nye bruger
5. Tryk på "OPRET ØNSKELISTE"
6. Udfyld formularen og tryk "OPRET ØSNKELISTE"
7. Tryk "ÅBEN" på din nye ønskeliste
8. Tryk "TILFØJ ET ØNSKE"
9. Udfyld formularen og tryk "OPRET ØNSKE"
10. Se dine ønsker i din ønskeliste, og eventuelt sende linket videre til dine gæster så de kan se dine ønsker

### Hvordan bruger jeg programmet online, hvis jeg har fået tilsendt et ønskeliste link
1. Åben tilsendt link
2. Tryk på "reserver", hvis du ønsker at reservere en gave

#### Dette er en opgave lavet i forbindelse med Datamatiker studiet
#### Projektgruppe
- Mikkel Mikkelsen – Frontend, Backend og Database
- Peter Tange – Frontend, Backend og Database
- Sarah Fagerstrøm – Frontend, Backend og Database
- Frederik Fuglø - Frontend, Backend og Database
