package com.proj0.models;

public class Payment {
    private int pid;
    private double amountPaid;
    private int monthsLeft;
    private int cid;
    private int uid;

    public Payment(int cid, int uid) {
        this.cid = cid;
        this.uid = uid;
    }

    public Payment(double amountPaid, int monthsLeft) {
        this.amountPaid = amountPaid;
        this.monthsLeft = monthsLeft;
    }

    public Payment(double amountPaid, int monthsLeft, int cid, int uid) {
        this.amountPaid = amountPaid;
        this.monthsLeft = monthsLeft;
        this.cid = cid;
        this.uid = uid;
    }

    public Payment(int pid, double amountPaid, int monthsLeft, int cid, int uid) {
        this.pid = pid;
        this.amountPaid = amountPaid;
        this.monthsLeft = monthsLeft;
        this.cid = cid;
        this.uid = uid;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public double getAmountPaid() {
        return this.amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getMonthsLeft() {
        return this.monthsLeft;
    }

    public void setMonthsLeft(int monthsLeft) {
        this.monthsLeft = monthsLeft;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object obj) {
        Payment payment = (Payment) obj;
        return getPid() == payment.getPid() && getAmountPaid() == payment.getAmountPaid()
                && getMonthsLeft() == payment.getMonthsLeft() && getCid() == payment.getCid()
                && getUid() == payment.getUid();
    }

    @Override
    public String toString() {
        // return "{" + " pid='" + getPid() + "'" + ", amountPaid='" + getAmountPaid() +
        // "'" + ", monthsLeft='"
        // + getMonthsLeft() + "'" + ", cid='" + getCid() + "'" + ", uid='" + getUid() +
        // "'" + "}";
        String format = "|%1$-5s|%2$-15s|%3$-20s|%4$-5s|%5$-5s|";
        return String.format(format, Integer.toString(getPid()), Double.toString(getAmountPaid()),
                Integer.toString(getMonthsLeft()), Integer.toString(getCid()), Integer.toString(getUid()));
    }

}
