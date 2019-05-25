#include "cco_helpers.cpp"

WiFiServer server(port);

void loop() {
    WiFiClient client = server.available();
    if (client) { //Si hay un cliente presente
        Serial.println("Novo Cliente");
        while (!client.available() && client.connected()) { //esperamos hasta que hayan datos disponibles
            delay(1);
        }
        String linea1 = client.readStringUntil('r');// Leemos la primera línea de la petición del cliente.
        Serial.print("Endpoint Acessado: ");
        Serial.println(linea1);

        if (linea1.indexOf("FRENTE") > 0) forward();
        if (linea1.indexOf("RE") > 0) backward();
        if (linea1.indexOf("DIREITA") > 0) right();
        if (linea1.indexOf("ESQUERDA") > 0) left();
        if (linea1.indexOf("PARAR") > 0) stopMovement();

        client.flush();
        printHomePage(client);
        Serial.println("RESPOSTA ENVIADA");
        Serial.println();
    }
}

void setup() {
    Serial.begin(9600);

    pinMode(R1, OUTPUT);
    pinMode(R2, OUTPUT);
    pinMode(L1, OUTPUT);
    pinMode(L2, OUTPUT);

    Serial.println();
    Serial.print("CONECTANDO WIFI: ");
    Serial.println(ssid);
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
    Serial.println(port);
}
