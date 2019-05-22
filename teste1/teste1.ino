void forward();

void backward();

void turnRight();

void turnLeft();

void stop();

void controlRightWheel(int action);

void controlLeftWheel(int action);

void controlWheel(int forwardPin, int backwardPin, int action);

const int GO_FORWARD = 1;
const int GO_BACKWARD = 2;
const int TURN_OFF = 3;
const int MOVE_TIME = 750;
const int ACTION_DELAY = 150;

void loop() {
    
    //forward();
    //forward();
    //forward();
    //turnRight();
    //turnRight();
    //delay(300);
}

void forward() {
    delay(ACTION_DELAY);
    controlRightWheel(GO_FORWARD);
    controlLeftWheel(GO_FORWARD);
    delay(MOVE_TIME);
    stop();
}

void backward() {
    delay(ACTION_DELAY);
    controlRightWheel(GO_BACKWARD);
    controlLeftWheel(GO_BACKWARD);
    delay(MOVE_TIME);
    stop();
}

void turnRight() {
    delay(ACTION_DELAY);
    controlRightWheel(GO_BACKWARD);
    controlLeftWheel(GO_FORWARD);
    delay(MOVE_TIME);
    stop();
}

void turnLeft() {
    delay(ACTION_DELAY);
    controlRightWheel(GO_FORWARD);
    controlLeftWheel(GO_BACKWARD);
    delay(MOVE_TIME);
    stop();
}

void stop() {
    controlLeftWheel(TURN_OFF);
    controlRightWheel(TURN_OFF);
}

void controlRightWheel(int action) {
    controlWheel(10, 9, action);
}

void controlLeftWheel(int action) {
    controlWheel(6, 7, action);
}

void controlWheel(int forwardPin, int backwardPin, int action) {
    switch (action) {
        case GO_FORWARD:
            controlWheel(forwardPin, backwardPin, TURN_OFF);
            digitalWrite(forwardPin, HIGH);
            break;
        case GO_BACKWARD:
            controlWheel(forwardPin, backwardPin, TURN_OFF);
            digitalWrite(backwardPin, HIGH);
            break;
        case TURN_OFF:
            digitalWrite(forwardPin, LOW);
            digitalWrite(backwardPin, LOW);
            break;
    }
}

void setup() {
    Serial.begin(9600);
    pinMode(10, OUTPUT);
    pinMode(9, OUTPUT);
    pinMode(7, OUTPUT);
    pinMode(6, OUTPUT);
    pinMode(2, INPUT);
    pinMode(3, INPUT);
}
