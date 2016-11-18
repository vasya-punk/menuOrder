package com.company.meal;

import java.util.ArrayList;

public class MealImpl implements Meal {

    private long price;
    private String name;
    private boolean canChoose;
    private ArrayList<Meal> parts;
    private boolean canAdd;

    public MealImpl(String name, long price){
        this.name = name;
        this.price = price;
    }

    public MealImpl(String name, long price, ArrayList<Meal> parts){
        this(name, price);

        this.parts = parts;
    }

    public MealImpl(String name, long price, ArrayList<Meal> parts, boolean canAdd){
        this(name, price, parts);
        this.canAdd = canAdd;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Meal> getParts() {
        return parts;
    }

    public String getPartsDescription(ArrayList<Meal> parts){
        StringBuffer result = new StringBuffer(getName());
        result.append('\n');
        for (Meal m : parts){
            result.append(m.getName() + ", ");
        }
        result.delete(result.lastIndexOf(","), result.length());
        return result.toString();
    }

    public boolean isCanChoose() {
        return canChoose;
    }

    public boolean isCanAdd() {
        return canAdd;
    }
}
