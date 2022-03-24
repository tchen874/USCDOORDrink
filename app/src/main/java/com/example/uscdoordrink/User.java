package com.example.uscdoordrink;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String name;
    public String username;
    public String email;
    private List<String> merchant;
    public Boolean isMerchant;

    //should we add a photo to user profiles?
    private String photo=null;

    public User()
    {
        merchant = new ArrayList<String>();
    }

    public User(String name, String email, Boolean isMerchant)
    {
        this.name = name;
        this.email = email;
        this.isMerchant = isMerchant;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserName() {
        return username;
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<String> merchant) {
        this.merchant = merchant;
    }

}
