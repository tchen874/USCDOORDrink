package com.example.uscdoordrink;

import java.util.ArrayList;

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
