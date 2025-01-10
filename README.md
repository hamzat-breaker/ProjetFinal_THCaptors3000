SCIACCO Achille 
TB3
# T&H CAPTORS 3000

- **ESP32** + Capteur DHT22 qui envoie les relevés (température, humidité) au back-end.
- **Back-end Node.js/Express** avec MongoDB pour stocker les données et proposer une API sécurisée.
- **Site web** (3 pages : accueil, connexion, dashboard).
- **Application Android Kotlin** pour consulter les relevés en mobilité.

# Installation et exécution

1. **ESP32**  
   - Ouvrir `ESP32/code_esp32.ino` dans Arduino IDE ou PlatformIO.  
   - Adapter les informations WiFi (SSID, password) et l’URL du serveur.  
   - Flasher l’ESP32.

2. **Back-end**  
   - Se placer dans `backend/`  
   - Créer un fichier `.env` avec `MONGO_URI`, `JWT_SECRET` et `PORT`.  
   - `npm install`  
   - `npm start`.  
   - Par défaut, le serveur écoute sur http://localhost:3000

3. **Site web**  
   - Ouvrir les fichiers HTML dans `web/` (ou lancer un petit serveur web).  

4. **Application Android**  
   - Ouvrir le dossier `android/THCaptorsApp/` dans Android Studio.  
   - Vérifier que l’URL du back-end est correcte.  
