package com.example.uscdoordrink;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.IgnoreExtraProperties;

public class Store {
    private String storeID;
    public String storeName;
    private ArrayList<Order> storeOrders = new ArrayList<Order>();
    private Location storeLoc;
    private String storePhone;
    private String storeAddress;
    private Menu storeMenu;

    //addOrder(), viewMenu()

    public Store(){

    }

    public Store(String id, String name, ArrayList orders, Location loc, String phone, String address, Menu menu ){
        this.storeID = id;
        this.storeName = name;
        this.storeOrders = orders;
        this.storeLoc = loc;
        this.storePhone = phone;
        this.storeAddress = address;
        this.storeMenu = menu;

    }

    //getters and setters
    public String getStoreID(){
        return storeID;
    }

    public void setStoreID(String id){
        this.storeID = id;
    }

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

    public Location getStoreLoc(){
        return storeLoc;
    }

    public void setStoreLoc(Location loc){
        this.storeLoc = loc;
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
        this.storeOrders.remove(order);
    }




}
