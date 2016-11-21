package com.company.meal;

import com.company.MenuItem;

public class Lunch extends MenuItem {

    private Cuisine cuisine;

    public Lunch(String name, Cuisine cuisine, long price) {
        super(name, price);

        this.cuisine = cuisine;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public String getName() {
        String result = super.getName();
        if(getChildren() != null && getChildren().size() > 0){
            result += "\n"+ getPartsDescription();
        }
        return result;
    }

    private String getPartsDescription(){
        StringBuffer result = new StringBuffer("(");
        for (MenuItem m : getChildren()){
            result.append(m.getName()+", ");
        }
        result.delete(result.lastIndexOf(", "), result.length());
        result.append(")");
        return result.toString();
    }
}
