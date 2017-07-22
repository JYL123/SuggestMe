package com.suggestme.android;

public class Rating {

    int rating;
    String shopName;
    String username;
    String itemName;
    int noOfRaters;
    String itemShop;

    public Rating() {
        //Empty Constructor For Firebase
    }


    public Rating(int rating, String shopName, String itemName, String username, int noOfRaters, String itemShop) {
        this.rating = rating;
        this.shopName=shopName;
        this.itemName=itemName;
        this.username=username;
        this.noOfRaters=noOfRaters;
        this.itemShop=itemShop;
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

    public int getNoOfRaters() {
        return noOfRaters;
    }

    public void setNoOfRaters(int noOfRaters) {
        this.noOfRaters = noOfRaters;
    }

    public String getItemShop() {
        return itemShop;
    }

    public void setItemShop(String itemShop) {
        this.itemShop = itemShop;
    }


}