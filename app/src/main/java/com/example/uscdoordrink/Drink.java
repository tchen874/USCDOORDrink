package com.example.uscdoordrink;

import android.renderscript.Sampler;

import java.io.*;
import java.time.temporal.ValueRange;
import java.util.*;
import java.lang.Object.*;


public class Drink implements java.io.Serializable {
    public String name;
    private double price;
    private double caffeine;

    public Drink(){}
    public Drink(String name, double price, double caffeine){
        this.name = name;
        this.price = price;
        this.caffeine = caffeine;
    }

    //getters and setters

    public String getName() {
        return name;
    }
    public void setName(String drinkName) {
        this.name = drinkName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getCaffeine() {
        return caffeine;
    }
    public void setCaffeine(double caffeine) {
        this.caffeine = caffeine;
    }

    // Helper function
    @Override
    public java.lang.String toString() {
        return "drinkName='" + name + '\'' +
                ", price=" + price +
                ", caffeine=" + caffeine +
                '}';
    }

    public List<ArrayList<String>> DrinkToList(List<Drink> drinkList)
    {
        List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
//        ArrayList<String> temp = new ArrayList<String>();
        for(int i = 0; i < drinkList.size(); i++)
        {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(drinkList.get(i).getName());
            temp.add(String.valueOf(drinkList.get(i).getPrice()));
            temp.add(String.valueOf(drinkList.get(i).getCaffeine()));
            result.add(temp);
        }

//        for(int i = 0; i < result.size(); i++)
//        {
//            System.out.println("RESUKT= " + result.get(i));
//        }
        return result;
    }

}
