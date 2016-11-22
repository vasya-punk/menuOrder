package com.company.meal;

import com.company.MenuItemImpl;

public class Addition extends MenuItemImpl {
    private boolean selected;

    public Addition(String name) {
        super(name);
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
