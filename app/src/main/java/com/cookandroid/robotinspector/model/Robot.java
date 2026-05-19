package com.cookandroid.robotinspector.model;

public class Robot {
    private int id;
    private String name;
    private String status;
    private int battery;
    private double latitude;
    private double longitude;
    private String lastInspected;

    public Robot() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getBattery() { return battery; }
    public void setBattery(int battery) { this.battery = battery; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getLastInspected() { return lastInspected; }
    public void setLastInspected(String lastInspected) { this.lastInspected = lastInspected; }
}
