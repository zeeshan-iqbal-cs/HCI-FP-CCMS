class Device{

public:
  //---------------- Display
  //---------------- Size of LCD
  const static int NUM_ROWS = 2;
  const static int NUM_COLS = 16;

  //------------------ LCD pins (I2C hardware)
  //==> SDA -> D2
  //==> SCL -> D1

  //------------------ BLUE-TOOTH veriables
  const static int BLUETOOTH_BAUD = 9600;
  // communication
  const static int BLUETOOTH_RX = D0;
  const static int BLUETOOTH_TX = D4;
  // switching
  const static int BLUETOOTH_BUTTON_PIN = D7;
  const static int BLUETOOTH_POWER_PIN = D8;
  
  //------------------ Sensors veriables
  const static int TEMP_SENSOR_PIN = D3;

  //------------------ Buzzer Pins
  const static int BUZZER_PIN = D5;

  /**********************************************
  *************** Functions will start **********
  ***********************************************/
  Device();
  String getId(){ return id;}
  String getSsid(){ return ssid;}
  String getPassword(){ return password;}
  void setSsid(String ssid);
  void setPassword(String password);
  void save();
private:
  String id;
  String ssid;
  String password;
  const char * DEBUG_TAG = "DEVICE";
};

void Device::setSsid(String ssid){
  this->ssid = ssid;
}
void Device::setPassword(String password){
  this->password = password;
}

Device::Device(){
  Serial.begin(9600);
  delay(500);

  Serial.println();
  Serial.println("************************");
  Serial.println("______     ______  _____");
  Serial.println("|    \\       |    |   |");
  Serial.println("|     \\      |    |___|");
  Serial.println("|______\\     |    |\\");
  Serial.println("|       \\    |    | \\");
  Serial.println("|        \\___|___ |  \\");
  Serial.println("CAST: An dream of clean air");

  debug(DEBUG_TAG, "Loading data from EEPROM");

  StorageIO rom;
  rom.reposition();

  id = rom.readNextString();
  ssid = rom.readNextString();
  password = rom.readNextString();
  id = "hardcode";
  debug(DEBUG_TAG, "ID", id);
  debug(DEBUG_TAG, "SSID", ssid);
  debug(DEBUG_TAG, "PASSWORD", password);
  debug(DEBUG_TAG, "DONE LOADING");
}

void Device::save(){
  debug(DEBUG_TAG, "Loading data from EEPROM");

  StorageIO rom;
  rom.reposition();

  rom.writeNextString(id);
  rom.writeNextString(ssid);
  rom.writeNextString(password);

  debug(DEBUG_TAG, "ID", id);
  debug(DEBUG_TAG, "SSID", ssid);
  debug(DEBUG_TAG, "PASSWORD", password);
}
