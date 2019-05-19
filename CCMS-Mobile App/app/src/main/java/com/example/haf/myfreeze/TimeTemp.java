package com.example.haf.myfreeze;

class TimeTemp {
    String Temp;
    String Time;

    public TimeTemp() {
    }

    public TimeTemp(String temp, String time) {
        Temp = temp;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

}
