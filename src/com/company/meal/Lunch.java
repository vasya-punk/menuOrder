package com.company.meal;

import com.company.MenuItem;
import com.company.MenuItemImpl;

public class Lunch extends MenuItemImpl {

    public Lunch(String name, long price) {
        super(name, price);
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
