package com.example.uscdoordrink;
import com.google.firebase.auth.FirebaseAuth;

public class User {

    public String name;
    public String email;

    public User()
    {

    }

    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

}
