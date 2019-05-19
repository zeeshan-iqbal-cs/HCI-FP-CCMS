
class ServerConnection{
private:
  String deviceId;
  const char * DEBUG_TAG = "SERVER_CONNECTION";
  const char * FIREBASE_HOST = "internet-controled-car.firebaseio.com";
  const char * FIREBASE_AUTH = "SsZIb57wNWlqki6tsEWJcugiKJm7Q3waEvBeTc1h";

public:
  void begin(Device & device){
    this->deviceId = device.getId();
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  }
  int sendData(DataPacket & data){
    debug(DEBUG_TAG, "SENDING DATA TO SERVER");

    //-----------------------------------------
    //---- Creating payload to send to server
    //-- Creating JSON Buffer
    StaticJsonBuffer<100> jsonBuffer;  //------ Allocated buffer to pack JSON
    
    //-- Add sensor's data in JSON
    JsonObject& temperatureObject = jsonBuffer.createObject();
    temperatureObject["temprature"] = data.temperature; //-- Add temperature
    
    //-- Add timestamp
    //- It will go in nested packed
    //- Will be applied by the server
    JsonObject& tempTime = temperatureObject.createNestedObject("timestamp");
    tempTime[".sv"] = "timestamp";
    
    String json;
    temperatureObject.prettyPrintTo(json);
    debug(DEBUG_TAG, "DATA Packet", "\n" + json);

    //-----------------------------------
    //-- Pushing data on Firebase
    String path = Firebase.push("ccms", temperatureObject);
    delay(1000);
    if (Firebase.failed()) {
      debug(DEBUG_TAG, "SENDING DATA TO SERVER", "Error!");
      debug(DEBUG_TAG, "SEND ERROR", path);
      debug(DEBUG_TAG, "SEND ERROR",Firebase.error());
      return -1;
    }else{
      debug(DEBUG_TAG, "SENDING DATA TO SERVER", "DATA PUSHED");
      return 0;
    }
  }

};
