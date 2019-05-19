package com.example.haf.myfreeze;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class DeviceData {
    String name;
    Integer Temp;


    public DeviceData() {
    }

    public DeviceData( Integer Temp, String name) {
        this.name = name;
        this.Temp = Temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return Temp;
    }

    public void setTemp(int temp) {
        Temp = temp;
    }
}
