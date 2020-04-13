package com.example.phonebond.Model;

public class chart {

    private String pid,bondName,descount,date,time,proColour,proDesc,proPrice,proName,quantity;

    public chart() {
    }

    public chart(String pid, String bondName, String descount, String date, String time, String proColour, String proDesc, String proPrice, String proName, String quantity) {
        this.pid = pid;
        this.bondName = bondName;
        this.descount = descount;
        this.date = date;
        this.time = time;
        this.proColour = proColour;
        this.proDesc = proDesc;
        this.proPrice = proPrice;
        this.proName = proName;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBondName() {
        return bondName;
    }

    public void setBondName(String bondName) {
        this.bondName = bondName;
    }

    public String getDescount() {
        return descount;
    }

    public void setDescount(String descount) {
        this.descount = descount;
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

    public String getProColour() {
        return proColour;
    }

    public void setProColour(String proColour) {
        this.proColour = proColour;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
