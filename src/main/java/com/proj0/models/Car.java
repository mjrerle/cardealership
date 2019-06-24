package com.proj0.models;

public class Car {
    private int cid;
    private String model;
    private int year;
    private double price;
    private int uid;
    private String location;

    public Car(int cid) {
        this.cid = cid;
        this.uid = 0;
        this.location = "lot";
    }

    public Car(int cid, int uid) {
        this.cid = cid;
        this.uid = uid;
        this.location = "lot";
    }

    public Car(String model, int year, double price) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.uid = 0;
        this.location = "lot";
    }

    public Car(String model, int year, double price, int uid) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.uid = uid;
        this.location = "lot";
    }

    public Car(String model, int year, double price, int uid, String location) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.uid = uid;
        this.location = location;
    }

    public Car(int cid, String model, int year, double price, int uid, String location) {
        this.cid = cid;
        this.model = model;
        this.year = year;
        this.price = price;
        this.uid = uid;
        this.location = location;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        // return "{" +
        // " cid='" + getCid() + "'" +
        // ", model='" + getModel() + "'" +
        // ", year='" + getYear() + "'" +
        // ", price='" + getPrice() + "'" +
        // ", uid='" + getUid() + "'" +
        // ", location='" + getLocation() + "'" +
        // "}";
        String format = "|%1$-5s|%2$-20s|%3$-5s|%4$-10s|%5$-5s|%6$-10s|";
        return String.format(format, Integer.toString(getCid()), getModel(), Integer.toString(getYear()),
                Double.toString(getPrice()), Integer.toString(getUid()), getLocation());
    }

}
