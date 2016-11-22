package com.company;

import java.util.ArrayList;

public interface MenuItem {
    String getName();

    long getPrice();

    void setId(int id);

    int getId();

    MenuItem add(MenuItem child);

    ArrayList<MenuItem> getChildren();

    boolean isHasChildren();

    void setParent(MenuItem menuItem);

    MenuItem getParent();

    String getCustomQuestion();
}
