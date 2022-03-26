package com.example.uscdoordrink;

//import android.location.Location;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.IgnoreExtraProperties;

public class Store {
    private String storeID;
    public String storeName;
    private ArrayList storeOrders = new ArrayList<Order>();
    double latitude;
    double longitude;
    private String storePhone;
    private String storeAddress;
    private Menu storeMenu;

    //addOrder(), viewMenu()

    public Store(){}

    public Store(String id, String name, ArrayList orders, double latitude, double longitude, String phone, String address, Menu menu ){
        this.storeID = id;
        this.storeName = name;
        this.storeOrders = orders;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
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
