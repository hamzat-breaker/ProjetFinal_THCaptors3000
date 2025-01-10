#include <WiFi.h>
#include <HTTPClient.h>
#include "DHT.h"

// -------------------------
// Configuration du WiFi
// -------------------------
const char* ssid     = "Mon_SSID";
const char* password = "Mon_Mot_de_Passe";

// -------------------------
// Configuration DHT
// -------------------------
#define DHTPIN 4      // GPIO où est branché le data du DHT22
#define DHTTYPE DHT22
DHT dht(DHTPIN, DHTTYPE);

// -------------------------
// Configuration du serveur
// -------------------------
String serverName = "http://10.0.2.2:3000/data"; 
// Pour tests en local (émulateur Android) : http://10.0.2.2:3000
// Sinon, remplacer par l'IP ou le nom de domaine de votre back-end

// -------------------------
void setup() {
  Serial.begin(115200);

  // Initialisation du DHT
  dht.begin();

  // Connexion WiFi
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  Serial.println("Connexion au WiFi...");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nConnecté au WiFi !");
}

void loop() {
  // Lecture du capteur
  float temperature = dht.readTemperature();
  float humidity    = dht.readHumidity();

  // Vérification si la lecture est valide
  if (isnan(temperature) || isnan(humidity)) {
    Serial.println("Impossible de lire les données du DHT22");
  } else {
    Serial.print("[DHT22] Temp: ");
    Serial.print(temperature);
    Serial.print("°C  |  Humidité: ");
    Serial.print(humidity);
    Serial.println("%");

    // Envoi au serveur
    if (WiFi.status() == WL_CONNECTED) {
      HTTPClient http;
      http.begin(serverName);

      // Création du JSON à envoyer
      String jsonData = String("{\"temperature\":") + temperature 
                      + ",\"humidity\":" + humidity + "}";

      int httpResponseCode = http.POST(jsonData);

      if (httpResponseCode > 0) {
        Serial.print("Réponse HTTP: ");
        Serial.println(httpResponseCode);
        String response = http.getString();
        Serial.println("Réponse serveur: " + response);
      } else {
        Serial.print("Erreur requête POST: ");
        Serial.println(httpResponseCode);
      }

      http.end();
    }
  }

  // On attebnd 10s
  delay(10000);
}
