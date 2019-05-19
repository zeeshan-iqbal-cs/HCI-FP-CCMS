struct DataPacket{
  float temperature;
};

class SensorManager{
private:
  const char * DEBUG_TAG = "SENSOR";
  DallasTemperature * temperatureSensor;
  OneWire * oneWire;
public:
  void begin(Device & device);
  DataPacket read();
};

void SensorManager::begin(Device & device) {
  //--Inetialize temperature sensor
  oneWire = new OneWire(device.TEMP_SENSOR_PIN);
  temperatureSensor = new DallasTemperature(oneWire);

  debug(DEBUG_TAG, " --Sensors ready-- ");
}


DataPacket SensorManager::read(){
  DataPacket readData;
  temperatureSensor->requestTemperatures();
  readData.temperature = temperatureSensor->getTempCByIndex(0);
  debug(DEBUG_TAG, "Data read", String("T: ") + readData.temperature);
  return readData;
}
