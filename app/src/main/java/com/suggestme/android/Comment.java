package com.suggestme.android;

public class Comment {

    String comment;
    int commentRating;
    String shopName;
    String username;
    String itemName;
    String itemShop;

    public Comment() {
        //Empty Constructor For Firebase
    }


    public Comment(String comment, int commentRating, String shopName, String itemName, String username, String itemShop) {
        this.comment = comment; //Parameterized for Program-Inhouse objects.
        this.commentRating = commentRating;
        this.shopName=shopName;
        this.itemName=itemName;
        this.username=username;
        this.itemShop=itemShop;
    }

    //Getters and Setters
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentRating() {
        return commentRating;
    }

    public void setCommentRating(int commentRating) {
        this.commentRating = commentRating;
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

    public String getItemShop() {
        return itemShop;
    }

    public void setItemShop(String itemShop) {
        this.itemShop = itemShop;
    }

}