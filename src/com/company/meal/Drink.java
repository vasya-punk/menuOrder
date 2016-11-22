package com.company.meal;

import com.company.MenuItem;
import com.company.MenuItemImpl;

public class Drink extends MenuItemImpl implements MealWithAdditions {
    private boolean hasAdditions;

    public Drink(String name, long price) {
        super(name, price);
    }

    public MenuItem add(MenuItem child){
        if(child instanceof Addition){
            hasAdditions = true;
        }
        return super.add(child);
    }

    public String getFullName() {
        String result = super.getName();
        if(hasAdditions && getChildren() != null && getChildren().size() > 0){
            result += getPartsDescription();
        }
        return result;
    }

    public String getPartsDescription(){
        StringBuffer sb = new StringBuffer();
        for (MenuItem m : getChildren()){
            if(m instanceof Addition && ((Addition) m).isSelected()){
                sb.append(m.getName()+", ");
            }
        }
        if(sb.lastIndexOf(", ") != -1){
            sb.delete(sb.lastIndexOf(", "), sb.length());
        }

        String result = sb.toString();
        if(result.length() > 0){
            result = " + (" + result + ")";
        }

        return result;
    }

    public boolean isHasAdditions() {
        return hasAdditions;
    }
}
