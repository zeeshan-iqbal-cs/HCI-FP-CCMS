package com.example.haf.myfreeze;

public class BluetoothDeviceEntry {
    String Name;
    String MAc;

    public BluetoothDeviceEntry() {
    }

    public BluetoothDeviceEntry(String name, String MAc) {
        Name = name;
        this.MAc = MAc;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMAc() {
        return MAc;
    }

    public void setMAc(String MAc) {
        this.MAc = MAc;
    }
}
