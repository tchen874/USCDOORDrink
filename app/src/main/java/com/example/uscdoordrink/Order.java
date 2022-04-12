package com.example.uscdoordrink;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private ArrayList<Drink> orderDrinks = new ArrayList<Drink>();
    //? TODO
    private Customer cus;
    private Merchant sel;
    private Location custLoc;
    private double caffTotal; /// caffeine in mg/d


    public Order(){
    }
    public Order(ArrayList<Drink> drinks, Customer customer, Merchant seller, Location loc, double caff){
        this.orderDrinks = drinks;
        this.cus = customer;
        this.sel = seller;
        this.custLoc = loc;
        this.caffTotal = caff;
    }

    //getters and setters

    public ArrayList<Drink> getOrderDrinks() {
        return orderDrinks;
    }

    public void setOrderDrinks(ArrayList<Drink> orderDrinks) {
        this.orderDrinks = orderDrinks;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public Merchant getSel() {
        return sel;
    }

    public void setSel(Merchant sel) {
        this.sel = sel;
    }

    public Location getCustLoc() {
        return custLoc;
    }

    public void setCustLoc(Location custLoc) {
        this.custLoc = custLoc;
    }

    public double getCaffTotal() {
        return caffTotal;
    }

    public void setCaffTotal(double caffTotal) {
        this.caffTotal = caffTotal;
    }

    //warn if caffeine total is too high, TODO! need to make into activity and call toast and maybe make user click continue yes/no
    public boolean warnCaff(double caffeine){
        if(caffeine > 400){
            return true;
        }
        return false;
    }

    //add drink to cart, increase caffeine total. if caffeine > 400, warn! only add if user continues past? TODO
    public void addDrink(Drink drink){
        this.orderDrinks.add(drink);
    }

    //remove drink from cart
    public void removeDrink(Drink drink){
        this.orderDrinks.remove(drink);
    }

    //start order? TODO start timer until delivery using route time
    public void placeOrder(){
    //push to the database

    }

    //structuring user order data as hashmap
    public Map<String, Object> ordersToHashMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("CUSTOMER_FIELD", cus);
        map.put("DRINK_FIELD", orderDrinks);
        map.put("CAFFEINATED_FIELD", caffTotal);
        map.put("MERCHANT_FIELD", sel);
        return map;
    }

}
