package com.example.uscdoordrink;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.IgnoreExtraProperties;

public class Store {
    public String storeName;
    private ArrayList<Order> storeOrders = new ArrayList<Order>();
    private String storePhone;
    private String storeAddress;
    private Menu storeMenu;

    //addOrder(), viewMenu()

    public Store(){

    }

    public Store(String name, ArrayList orders, String phone, String address, Menu menu ){
        this.storeName = name;
        this.storeOrders = orders;
        this.storePhone = phone;
        this.storeAddress = address;
        this.storeMenu = menu;
    }

    //getters and setters

    public String getStoreName(){
        return storeName;
    }

    public void setStoreName(String name){
        this.storeName = name;
    }

    public ArrayList getStoreOrders(){
        return storeOrders;
    }

    public void setStoreOrders(ArrayList orders){
        this.storeOrders = orders;
    }

    public String getStorePhone(){
        return storePhone;
    }

    public void setStorePhone(String phone){
        this.storePhone = phone;
    }

    public String getStoreAddress(){
        return storeAddress;
    }

    public void setStoreAddress(String address){
        this.storeAddress = address;
    }

    public Menu getStoreMenu(){
        return storeMenu;
    }

    public void setStoreMenu(Menu menu){
        this.storeMenu = menu;
    }


    //add order to menu
    public void addOrder(Order order){
        this.storeOrders.add(order);
    }
    //remove order to menu
    public void removeOrder(Order order){
        //only remove if it's in the list
        if(this.storeOrders.contains(order)){
            this.storeOrders.remove(order);
        }
    }
}
