package com.example.uscdoordrink;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String name;
    public String username;
    public String email;
    private List<String> merchant;


    public User() {
        merchant = new ArrayList<String>();
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;

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

    static void viewAccount(){
        //TODO
        //visiting your own profile

    }
    static void viewHistory(){
        //TODO
    }
}
