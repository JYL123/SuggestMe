package com.suggestme.android;

public class Rating {

    int rating;
    String shopName;
    String username;
    String itemName;

    public Rating() {
        //Empty Constructor For Firebase
    }


    public Rating(int rating, String shopName, String itemName, String username) {
        this.rating = rating;
        this.shopName=shopName;
        this.itemName=itemName;
        this.username=username;
    }

    //Getters and Setters

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName=itemName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}