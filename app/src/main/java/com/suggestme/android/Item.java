package com.suggestme.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Item {

    String itemName;
    String category;
    String shopName;
    int price;
    String desc;
    String url;

    public Item() {
        //Empty Constructor For Firebase
    }


    public Item(String itemName, String category, String shopName, int price, String desc, String url) {
        this.itemName = itemName; //Parameterized for Program-Inhouse objects.
        this.category = category;
        this.shopName=shopName;
        this.price = price;
        this.desc=desc;
        this.url=url;

    }

    //Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }


}