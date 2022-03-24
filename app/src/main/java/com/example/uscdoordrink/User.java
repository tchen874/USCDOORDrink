package com.example.uscdoordrink;
import com.google.firebase.auth.FirebaseAuth;

public class User {

    public String name;
    public String email;
    public Boolean isMerchant;

    public User()
    {

    }

    public User(String name, String email, Boolean isMerchant)
    {
        this.name = name;
        this.email = email;
        this.isMerchant = isMerchant;
    }

}
