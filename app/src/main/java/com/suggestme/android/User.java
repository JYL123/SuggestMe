package com.suggestme.android;

/**
 * Created by Jasmine on 6/12/2017.
 */
import com.google.firebase.database.IgnoreExtraProperties;

public class User {

    String username;
    String password;
    int userRating;
    String email;


    public User() {
        //Empty Constructor For Firebase
    }

    public User(String username, String password, int userRating, String email)
    {
        this.username = username; //Parameterized for Program-Inhouse objects.
        this.password = password;
        this.userRating=userRating;
        this.email=email;
    }

    //Getters and Setters
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUserRating()
    {
        return userRating;
    }
    public void setUserRating(int userRating)
    {
        this.userRating = userRating;
    }

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}