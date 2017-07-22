package com.suggestme.android;

public class Shop {

    String shopName;
    String desc;

    public Shop() {
        //Empty Constructor For Firebase
    }


    public Shop(String shopName, String desc) {
        this.shopName = shopName; //Parameterized for Program-Inhouse objects.
        this.desc = desc;
    }

    //Getters and Setters
    public String getShopName() {
        return shopName;
    }

    public void setshopNamet(String shopName) {
        this.shopName = shopName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}