package com.example.uscdoordrink;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Drink> drinkList = new ArrayList<Drink>();
    private ArrayList<Double> discountList = new ArrayList<Double>(); //? ask

    public Menu(){

    }
    public Menu(ArrayList<Drink> drinks){
        this.drinkList = drinks;
    }

    public Menu(ArrayList<Drink> drinks, ArrayList<Double> discounts){
        this.drinkList = drinks;
        this.discountList = discounts;
    }

    //getters and setters
    public ArrayList getDrinkList(){
        return drinkList;
    }

    public void setDrinkList(ArrayList<Drink> drinks){
        this.drinkList = drinks;
    }

    public ArrayList getDiscountList(){
        return discountList;
    }

    public void setDiscountList(ArrayList<Double> discounts){
        this.discountList = discounts;
    }

    //add drink to menu
    public void addDrink(Drink drink){
        this.drinkList.add(drink);
    }

    //remove drink from menu
    public void removeDrink(Drink drink){
        this.drinkList.remove(drink);
    }

    //add discount
    public void addDiscount(double discount){
        this.discountList.add(discount);
    }

    //remove discount
    public void removeDiscount(double discount){
        this.discountList.remove(discount);
    }




}
