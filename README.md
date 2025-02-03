# 🌦️ Installationsanleitung - Wetter-App mit Docker  

Diese Anleitung beschreibt die Installation und den Start der Wetter-App mit **Docker & Docker Compose**.  
Es sind **keine manuellen Installationen von Java oder PostgreSQL** erforderlich.  

## 1. Voraussetzungen  
- [Docker](https://docs.docker.com/get-docker/) installieren  
- Terminal/Eingabeaufforderung öffnen und prüfen:  
```bash
docker --version
docker-compose --version
```
## 2. Projektdateien vorbereiten
Repo Clonen und sicherstellen, dass folgende Dateien vorhanden sind:
```bash
weather-app/
  ├── backend/
  │   ├── Dockerfile
  │   ├── target/weather-widget-0.0.1-SNAPSHOT.jar
  ├── weatherdb_dump.sql
  ├── docker-compose.yml
  ├── README.md
```
## 3. Anwendung starten
Docker-Container bauen und starten:
```bash
docker-compose up --build -d
```
Wetter-App im Browser öffnen:
```bash
http://localhost:8080
```
## 4. Anwendung stoppen
```bash
docker-compose down
```
Falls auch die PostgreSQL-Daten gelöscht werden sollen:
```bash
docker-compose down -v
```


# 🌦️ Wetter-App - Dokumentation  

## 📌 Projektübersicht  

Ich habe soweit nur ein Widget entworfen. Die Einbindung in Teams habe ich kurz probiert, letztendlich aber nicht hinbekommen. Deshalb auch das manifest.json im Frontend und die Key-Dateien, um HTTPS zu bekommen. Bin dann daran gescheitert, dass ich einen Teams-Unternehmensaccount bräuchte, weil ich in "normalen Teams" keine Apps hochladen durfte. Habe dann noch herausgefunden, dass man einen Developer-Account beantragen kann, aber dann war die Seite da irgendwie down, und dann habe ich das aufgegeben.

Die Wetter-App ist eine Full-Stack-Anwendung, die Wetterdaten für verschiedene Städte anzeigt.
Das Projekt besteht aus:  
- **Frontend:** React (TypeScript)  
- **Backend:** Spring Boot (Java)  
- **Datenbank:** PostgreSQL  
- **Deployment:** Docker & Docker Compose  
- **API:** Open-Meteo (für Wetterdaten)
---

## 🚀 Funktionen  
✅ Anzeige der Wetterdaten für verschiedene Städte  
✅ Datenaktualisierung über eine API (Spring Boot)  
✅ PostgreSQL-Datenbank zur Speicherung von Wetterdaten  
✅ Automatisierte Bereitstellung mit Docker  

---






