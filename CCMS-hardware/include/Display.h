class Display{
private:
  LiquidCrystal_I2C * lcd;
  String deviceId;
  const char * DEBUG_TAG = "DISPLAY";

  unsigned short rows;
  unsigned short cols;
  unsigned short buzzerPin;
public:
  Display(Device & device);
  void welcome();
  void phone();
  void wifiStatus(bool);
  void data(DataPacket);
};

Display::Display(Device & device){
  this->rows = device.NUM_ROWS;
  this->cols = device.NUM_COLS;
  this->buzzerPin = device.BUZZER_PIN;
  deviceId = device.getId();
  debug(DEBUG_TAG, "SET DEVICE ID", stFahion(deviceId));

  lcd = new LiquidCrystal_I2C(0x3F, 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);
  lcd->begin(rows, cols);
  debug(DEBUG_TAG, "LCD SIZE =", String(rows) + "x" +cols);

  pinMode(buzzerPin, OUTPUT);
}

void Display::welcome(){
  lcd->setCursor(0, 0);
  lcd->print("Device Starting");
  for (int i = 0; i < 16; i++){
    lcd->setCursor(i, 1);
    lcd->print('*');
    delay(200);
  }
}


void Display::phone() {
  lcd->clear();
  lcd->setCursor(0,0);
  lcd->print("Settings");
}


void Display::data(DataPacket data){

  lcd->setCursor(0,1);
  lcd->print("T = ");
  lcd->print(data.temperature);

  /*
  if (data.temperature > 25){
    lcd->clear();
    lcd->print("Warning");
    digitalWrite(buzzerPin, HIGH);
    while (true);
  }
  */
}

void Display::wifiStatus(bool wifiConnected){
  lcd->clear();
  lcd->setCursor(0,0);

  if (wifiConnected)
    lcd->print("WIFI Connected");
  else {
    lcd->clear();
    lcd->print("WFIF Failed");

    digitalWrite(buzzerPin, HIGH);
    delay(100);
    digitalWrite(buzzerPin, LOW);
  }
}
