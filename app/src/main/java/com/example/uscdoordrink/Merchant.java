package com.example.uscdoordrink;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends User {
    //merchant
    Store store;
    private ArrayList<Order> currOrders;

    static void createMerchAccount() {
    }

    static void createStore() {
    }

    static void viewAccount() {
    }

    static void viewHistory() {
    }

    //getters and setters
    public Store getStore(){
        return store;
    }
    public ArrayList<Order> getcurrOrders(){
        return currOrders;
    }

    public void setStore(Store store){
        this.store = store;
    }
    public void setcurrOrders(ArrayList<Order> currOrders){
        this.currOrders = currOrders;
    }





}
