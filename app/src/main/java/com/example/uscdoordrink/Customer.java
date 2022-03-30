package com.example.uscdoordrink;

import java.util.ArrayList;
import java.util.Map;

public class Customer {

    //adding basic customer variables because will be using them and they are needed for basic functionality of app
    public static final String drink_name_ = "drink_name";
    public String id;
    public String customer_name;
    public String drink;
    public String startTime;
    public String endTime;
    public String seller_name;
    public String restaurant_name;
    public Boolean caffeine;
    public String order_location;

    //creating Customer constructor using map of string, objects
    //the variables that I am getting will be from firebase firestore
    // query data from map https://stackoverflow.com/questions/46276806/retrieving-data-using-map-from-firebase-database
    public Customer(String id, Map<String, Object> map){
        this.id = id;
        this.customer_name = (String)map.get("customer_name");
        this.drink = (String)map.get("drink_name");
        this.startTime = (String)map.get("start_time");
        this.seller_name = (String)map.get("seller_name");
        this.restaurant_name = (String)map.get("restaurant_name");
        this.endTime = (String)map.get("end_time");
        this.caffeine = (Boolean) map.get("caffeine");
        this.order_location = (String) map.get("order_location");
    }




    private String custName;
    //private Location custLoc;
    private ArrayList<Order> currOrders;

    static void createCustAccount() {

    }
    public void addOrder(Order order){
        this.currOrders.add(order);
    }

    public void sendNotification(){

    }
    public void viewAccount() {

    }
    public void viewHistory(){

    }

    //getters and setters
    public ArrayList<Order> getcurrOrders() {return currOrders;}
    public String getCustName(){ return custName;}

    public void setCurrOrders(ArrayList<Order> currOrders) {this.currOrders = currOrders;}
    public void setCustName(String custName){this.custName = custName;}

}
