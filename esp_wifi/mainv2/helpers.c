#include "headers.h"

void printHomePage(WiFiClient client) {
    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("Connection: close");// La conexión se cierra después de finalizar de la respuesta
    client.println();
    client.println("<!DOCTYPE HTML>");
    client.println("<html>");
    client.println("<head><title>Lumina Spargere</title>");
    client.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    client.println(
            "<style>button{background-color:#f44336;border:none;color:white;padding:15px 32px;text-align:center;text-decoration:none;display:inline-block;font-size: 24px; transition-duration: 0.4s;}button:hover{background-color: #4CAF50;  color: white;}</style>");
    client.println("</head>");
    client.println("<body>");
    client.println("<div style='text-align:center;'>");
    client.println("<h1 align='center'>Lumina Spargere</h1>");
    client.println("<br />");
    client.println("<button onClick=location.href='./?FRENTE'>FRENTE</button><br>");
    client.println("<button onClick=location.href='./?RE'>RE</button><br>");
    client.println("<button onClick=location.href='./?DIREITA'>DIREITA</button><br>");
    client.println("<button onClick=location.href='./?ESQUERDA'>ESQUERDA</button><br>");
    client.println("<button onClick=location.href='./?PARAR'>PARAR</button>");
    client.println("<br />");
    client.println("</div>");
    client.println("</body>");
    client.println("</html>");
}

void stopMovement() {
    digitalWrite(R1, LOW);
    digitalWrite(R2, LOW);
    digitalWrite(L1, LOW);
    digitalWrite(L2, LOW);
}

void forward() {
    digitalWrite(R1, HIGH);
    digitalWrite(R2, LOW);
    digitalWrite(L1, LOW);
    digitalWrite(L2, HIGH);
    delay(500);
    stopMovement();
}

void backward() {
    digitalWrite(R1, HIGH);
    digitalWrite(R2, LOW);
    digitalWrite(L1, LOW);
    digitalWrite(L2, HIGH);
    delay(500);
    stopMovement();
}

void right() {
    digitalWrite(R1, LOW);
    digitalWrite(R2, HIGH);
    digitalWrite(L1, LOW);
    digitalWrite(L2, LOW);
    delay(500);
    stopMovement();
}

void left() {
    digitalWrite(R1, LOW);
    digitalWrite(R2, LOW);
    digitalWrite(L1, HIGH);
    digitalWrite(L2, LOW);
    delay(500);
    stopMovement();
}
