package com.example.uscdoordrink;

public class Drink {
    public String drinkName;
    private double price;
    private double caffeine;

    public Drink(String name, double price, double caffeine){
        this.drinkName = name;
        this.price = price;
        this.caffeine = caffeine;
    }

    //getters and setters

    public String getDrinkName() {
        return drinkName;
    }
    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
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
}
