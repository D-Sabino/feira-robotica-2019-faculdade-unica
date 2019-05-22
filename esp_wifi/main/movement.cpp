#include "constants.h"

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
