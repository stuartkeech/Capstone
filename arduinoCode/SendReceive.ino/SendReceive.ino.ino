#include <nRF24L01.h>
#include <printf.h>
#include <RF24.h>
#include <RF24_config.h>

//SendReceive.ino
 
#include<SPI.h>
#include<RF24.h>

const int greenPin = 10;
const int yellowPin = 4;
const int redPin = 6;
//const int button_startpause = 2;
//const int button_stop = 3;
 
// CE, CSN pins
RF24 radio(7, 8);
 
void setup(void){
  while(!Serial);

  pinMode(greenPin, OUTPUT);
  pinMode(yellowPin, OUTPUT);
  pinMode(redPin, OUTPUT);
  //pinMode(button_startpause, INPUT);
  //pinMode(button_stop, INPUT);
  Serial.begin(115200);
 
  radio.begin();
  radio.setPALevel(RF24_PA_MAX);
  radio.setChannel(0x77);
  radio.openWritingPipe(0xF0F0F0F0E1LL);
  const uint64_t pipe = (0xE8E8F0F0E1LL);
  radio.openReadingPipe(1, pipe);
  
  radio.enableDynamicPayloads();
  radio.powerUp();
  
}
 
void loop(void){
  radio.startListening();
  Serial.println("Starting loop. Radio on.");
  char receivedMessage[32] = {0};
  if(radio.available()){
    radio.read(receivedMessage, sizeof(receivedMessage));
    Serial.println(receivedMessage);
    Serial.println("Turning off the radio."); 
    radio.stopListening(); //so we can interact
 
    String stringMessage(receivedMessage);
 
    if(stringMessage == "GETSTRING"){
      Serial.println("Looks like they want a string!!");
      const char text[] = "Yo wassup, haha";
      radio.write(text, sizeof(text));
      Serial.println("We sent our message.");
    }
    else if(stringMessage == "error"){
      Serial.println("Turning on the RED light...");
      digitalWrite(redPin, HIGH); 
      digitalWrite(greenPin, LOW); 
      digitalWrite(yellowPin, LOW);
      const char text[] = "Message received, RED light on";
      radio.write(text, sizeof(text));
      Serial.println("We sent our message.");
    }
    else if(stringMessage == "start"){
      Serial.println("Turning on the GREEN light...");
      digitalWrite(redPin, LOW); 
      digitalWrite(greenPin, HIGH); 
      digitalWrite(yellowPin, LOW);
      const char text[] = "Message received, GREEN light on";
      radio.write(text, sizeof(text));
      Serial.println("We sent our message.");
    }
    else if(stringMessage == "stop"){
      Serial.println("Turning on the YELLOW light...");
      digitalWrite(redPin, LOW); 
      digitalWrite(greenPin, LOW); 
      digitalWrite(yellowPin, HIGH);
      const char text[] = "Message received, YELLOW light on";
      radio.write(text, sizeof(text));
      Serial.println("We sent our message.");
    }

//  if (button_startpause == HIGH) {    
//    digitalWrite(redPin, LOW); 
//    digitalWrite(greenPin, HIGH); 
//    digitalWrite(yellowPin, HIGH);
//  } else {
//    digitalWrite(redPin, LOW); 
//    digitalWrite(greenPin, LOW); 
//    digitalWrite(yellowPin, LOW);
//  }
//
//  if (button_stop == HIGH) {    
//    digitalWrite(redPin, HIGH); 
//    digitalWrite(greenPin, LOW); 
//    digitalWrite(yellowPin, HIGH);
//  } else {
//    digitalWrite(redPin, LOW); 
//    digitalWrite(greenPin, LOW); 
//    digitalWrite(yellowPin, LOW);
//  }
  }
  delay(100);
 
}
