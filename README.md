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








