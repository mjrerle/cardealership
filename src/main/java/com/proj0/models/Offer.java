package com.proj0.models;

public class Offer {
    private int oid;
    private double amount;
    private int monthsOffered;
    private String status;
    private double downPayment;
    private int uid;
    private int cid;

    public Offer(int oid) {
        this.oid = oid;
    }

    public Offer(int oid, int cid, int uid) {
        this.oid = oid;
        this.uid = uid;
        this.cid = cid;
    }

    public Offer(double amount, int monthsOffered, double downPayment, int cid, int uid) {
        this.amount = amount;
        this.monthsOffered = monthsOffered;
        this.status = "unevaluated";
        this.downPayment = downPayment;
        this.uid = uid;
        this.cid = cid;
    }

    public Offer(double amount, int monthsOffered, String status, double downPayment, int cid, int uid) {
        this.amount = amount;
        this.monthsOffered = monthsOffered;
        this.status = status;
        this.downPayment = downPayment;
        this.uid = uid;
        this.cid = cid;
    }

    public Offer(int oid, double amount, int monthsOffered, String status, double downPayment, int cid, int uid) {
        this.oid = oid;
        this.amount = amount;
        this.monthsOffered = monthsOffered;
        this.status = status;
        this.downPayment = downPayment;
        this.uid = uid;
        this.cid = cid;
    }

    public int getOid() {
        return this.oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMonthsOffered() {
        return this.monthsOffered;
    }

    public void setMonthsOffered(int monthsOffered) {
        this.monthsOffered = monthsOffered;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDownPayment() {
        return this.downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object obj) {
        Offer offer = (Offer) obj;
        return getOid() == offer.getOid() && getAmount() == offer.getAmount() && getCid() == offer.getCid()
                && getMonthsOffered() == offer.getMonthsOffered() && getUid() == offer.getUid()
                && getDownPayment() == offer.getDownPayment() && getStatus().equals(offer.getStatus());
    }

    @Override
    public String toString() {
        // return "{" + " oid='" + getOid() + "'" + ", amount='" + getAmount() + "'" +
        // ", monthsOffered='"
        // + getMonthsOffered() + "'" + ", status='" + getStatus() + "'" + ", uid='" +
        // getUid() + "'" + ", cid='"
        // + getCid() + "'" + "}";
        String format = "|%1$-5s|%2$-10s|%3$-20s|%4$-20s|%5$-20s|%6$-5s|%7$-5s|";
        return String.format(format, Integer.toString(getOid()), Double.toString(getAmount()),
                Integer.toString(getMonthsOffered()), getStatus(), Double.toString(getDownPayment()),
                Integer.toString(getUid()), Integer.toString(getCid()));
    }

}
