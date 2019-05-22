#include <ESP8266WiFi.h>
const char *ssid = "INFINITUMC0FD";
const char *password = "6652195195";
int port = 10;
WiFiServer server(port);
//MOTOR DERECHA
int OUTPUT4 = 16;
int OUTPUT3 = 5;
//MOTOR IZQUIERDA
int OUTPUT2 = 4;
int OUTPUT1 = 0;
void setup() {
  Serial.begin (9600);
  pinMode (OUTPUT1, OUTPUT);
  pinMode (OUTPUT2, OUTPUT);
  pinMode (OUTPUT3, OUTPUT);
  pinMode (OUTPUT4, OUTPUT);
  delay(10);
  Serial.println();
  Serial.print("CONECTANDO WIFI: ");
  Serial.println(ssid);
  WiFi.begin(ssid, password); //Conexión a la red
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi conectado");
  server.begin(); //Iniciamos el servidor
  Serial.println("Servidor Iniciado");
  Serial.println("IP Servidor:");
  Serial.println(WiFi.localIP()); //Obtenemos la IP
  Serial.print("Puerto:");
  Serial.println(port);
}

void loop() {
  WiFiClient client = server.available();
  if (client) { //Si hay un cliente presente
    Serial.println("Nuevo Cliente");
    while (!client.available() && client.connected()) { //esperamos hasta que hayan datos disponibles
      delay(1);
    }
    String linea1 = client.readStringUntil('r');// Leemos la primera línea de la petición del cliente.
    Serial.print("LINEA: ");
    Serial.println(linea1);
    if (linea1.indexOf("ADELANTE") > 0) { //Buscamos un LED=ON en la 1°Linea
      digitalWrite(OUTPUT1, 0);
      digitalWrite(OUTPUT2, 1);
      digitalWrite(OUTPUT3, 1);
      digitalWrite(OUTPUT4, 0);
    }
    if (linea1.indexOf("ATRAS") > 0)  {
      digitalWrite(OUTPUT1, 1);
      digitalWrite(OUTPUT2, 0);
      digitalWrite(OUTPUT3, 0);
      digitalWrite(OUTPUT4, 1);
    }
    if (linea1.indexOf("DERECHA") > 0)  {
      digitalWrite(OUTPUT1, 0);
      digitalWrite(OUTPUT2, 0);
      digitalWrite(OUTPUT3, 1);
      digitalWrite(OUTPUT4, 0);
    }
    if (linea1.indexOf("IZQUIERDA") > 0)  {
      digitalWrite(OUTPUT1, 0);
      digitalWrite(OUTPUT2, 1);
      digitalWrite(OUTPUT3, 0);
      digitalWrite(OUTPUT4, 0);
    }
     if (linea1.indexOf("PARAR") > 0)  {
      digitalWrite(OUTPUT1, 0);
      digitalWrite(OUTPUT2, 0);
      digitalWrite(OUTPUT3, 0);
      digitalWrite(OUTPUT4, 0);
    }
    client.flush();
    Serial.println("Enviando respuesta...");
    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("Connection: close");// La conexión se cierra después de finalizar de la respuesta
    client.println();
    client.println("<!DOCTYPE HTML>");
    client.println("<html>");
    client.println("<head><title>CARRITO WIFI</title>");
    client.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    client.println("<style>button{background-color:#f44336;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size: 24px; transition-duration: 0.4s;}button:hover{background-color: #4CAF50;  color: white;}</style>");
    client.println("</head>");
    client.println("<body>");
    client.println("<div style='text-align:center;'>");
    client.println("<h1 align='center'>CARRITO WIFI</h1>");
    client.println("<br />");
    client.println("<button onClick=location.href='./?ADELANTE'>ADELANTE</button><br>");
    client.println("<button onClick=location.href='./?ATRAS'>ATRAS</button><br>");
    client.println("<button onClick=location.href='./?DERECHA'>DERECHA</button><br>");
    client.println("<button onClick=location.href='./?IZQUIERDA'>IZQUIERDA</button><br>");
    client.println("<button onClick=location.href='./?PARAR'>PARAR</button>");
    client.println("<br />");
    client.println("</div>");
    client.println("</body>");
    client.println("</html>");
    delay(1);
    Serial.println("RESPUESTA ENVIADA");
    Serial.println();
  }
}

