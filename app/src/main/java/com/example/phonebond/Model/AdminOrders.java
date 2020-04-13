package com.example.phonebond.Model;

public class AdminOrders {

    public String date,time,state,number,totalAmount;

    public AdminOrders() {
    }

    public AdminOrders(String date, String time, String state, String number, String totalAmount) {
        this.date = date;
        this.time = time;
        this.state = state;
        this.number = number;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
