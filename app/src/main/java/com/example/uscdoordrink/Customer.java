package com.example.uscdoordrink;

import java.util.ArrayList;

public class Customer {
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