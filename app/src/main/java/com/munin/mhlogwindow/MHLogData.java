package com.munin.mhlogwindow;

/**
 * Created by munin on 2018/1/29.
 */

public class MHLogData {
    long currentTime = System.currentTimeMillis();
    String msg = "";
    String color = "#ff0000";


    public MHLogData( String msg, String color) {
        this.msg = msg;
        this.color = color;
    }

    public MHLogData(String msg) {
        this.msg = msg;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "MHLogData{" +
                "currentTime=" + currentTime +
                ", msg='" + msg + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
