#include <ESP8266WiFi.h>

WiFiServer server(1532);

// Right
const int RF = 16;
const int RB = 5;
// Left
const int LF = 0;
const int LB = 4;

// Wireless network settings
const char *ssid = "Lumina";
const char *password = "15321532";

bool isLedOn = false;

void loop() {
    WiFiClient client = server.available(); 

    if (client) { //Si hay un cliente presente

        if(isLedOn){
            digitalWrite(14, LOW);
            isLedOn = false;
        } else {
            digitalWrite(14, HIGH);
            isLedOn = true;
        }
        
        Serial.println("Novo Cliente");
        while (!client.available() && client.connected()) { //esperamos hasta que hayan datos disponibles
            delay(1);
        }
        String linea1 = client.readStringUntil('r');// Leemos la primera línea de la petición del cliente.
        Serial.print("Endpoint Acessado: ");
        Serial.println(linea1);

        if (linea1.indexOf("FRENTE") > 0) {
            digitalWrite(RF, HIGH);
            digitalWrite(RB, LOW);
            digitalWrite(LF, HIGH);
            digitalWrite(LB, LOW);
            delay(150);
            digitalWrite(RF, LOW);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, LOW);
        }
        if (linea1.indexOf("TRAS") > 0) {
            digitalWrite(RF, LOW);
            digitalWrite(RB, HIGH);
            digitalWrite(LF, LOW);
            digitalWrite(LB, HIGH);
            delay(150);
            digitalWrite(RF, LOW);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, LOW);
        }
        if (linea1.indexOf("DIREITA") > 0) {
            digitalWrite(RF, LOW);
            digitalWrite(RB, HIGH);
            digitalWrite(LF, HIGH);
            digitalWrite(LB, LOW);
            delay(150);
            digitalWrite(RF, LOW);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, LOW);
        }
        if (linea1.indexOf("ESQUERDA") > 0) {
            digitalWrite(RF, HIGH);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, HIGH);
            delay(150);
            digitalWrite(RF, LOW);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, LOW);
        }
        if (linea1.indexOf("PARAR") > 0) {
            digitalWrite(RF, LOW);
            digitalWrite(RB, LOW);
            digitalWrite(LF, LOW);
            digitalWrite(LB, LOW);
        }

        client.flush();
        const char *html = "HTTP/1.1 200 OK"
                           "\nContent-Type: text/html"
                           "\nConnection: close"
                           "\n"
                           "\n<!DOCTYPE HTML>"
                           "\n<html>"
                           "\n<head><title>Lumina Spargere</title>"
                           "\n<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                           "\n"
                           "\n<style>button{background-color:#f44336border:nonecolor:whitepadding:15px 32pxtext-align:centertext-decoration:nonedisplay:inline-blockfont-size: 24px transition-duration: 0.4s}button:hover{background-color: #4CAF50  color: white}</style>"
                           "\n</head>"
                           "\n<body>"
                           "\n<div style='text-align:center'>"
                           "\n<h1 align='center'>Lumina Spargere</h1>"
                           "\n<br />"
                           "\n<button onClick=location.href='./?FRENTE'>FRENTE</button><br>"
                           "\n<button onClick=location.href='./?TRAS'>RE</button><br>"
                           "\n<button onClick=location.href='./?DIREITA'>DIREITA</button><br>"
                           "\n<button onClick=location.href='./?ESQUERDA'>ESQUERDA</button><br>"
                           "\n<button onClick=location.href='./?PARAR'>PARAR</button>"
                           "\n<br />"
                           "\n</div>"
                           "\n</body>"
                           "\n</html>";
        client.println(html);
        Serial.println("RESPOSTA ENVIADA");
        Serial.println();
    }
}

void setup() {
    Serial.begin(9600);

    pinMode(RF, OUTPUT);
    pinMode(RB, OUTPUT);
    pinMode(LF, OUTPUT);
    pinMode(LB, OUTPUT);
    pinMode(14, OUTPUT);

    Serial.println();
    Serial.print("CONECTANDO WIFI: ");
    Serial.println(ssid);

    IPAddress ip(192, 168, 0, 42);
    IPAddress gateway(192, 168, 0, 1);
    IPAddress subnet(255, 255, 255, 0);
    WiFi.config(ip, gateway, subnet);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println("");
    Serial.println("WiFi conectado");
    server.begin();
    Serial.println("Servidor Iniciado");
    Serial.println("IP Servidor:");
    Serial.println(WiFi.localIP());
    Serial.print("Porta:");
    Serial.println(1532);
}
