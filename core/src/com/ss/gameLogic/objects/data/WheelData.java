package com.ss.gameLogic.objects.data;


public class WheelData {
    public String region;

    public int id ;

    public int  quantity;

    public int percent;
    public WheelData(String region,int id,int quantity, int percent){
        this.region = region;
        this.id = id;
        this.quantity = quantity;
        this.percent = percent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }


}
