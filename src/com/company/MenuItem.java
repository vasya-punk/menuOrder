package com.company;

import com.company.meal.Meal;

import java.util.ArrayList;

public class MenuItem implements Meal {

    private String name;
    private ArrayList<MenuItem> children;
    private MenuItem parent;
    private long price;
    private int id;
    private String customQuestion;
    private boolean hasChildren;

    public MenuItem(String name){
        this.name = name;
    }

    public MenuItem(String name, long price){
        this(name);

        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public MenuItem add(MenuItem child){
        hasChildren = true;

        child.setParent(this);

        if(children == null){
            children = new ArrayList<MenuItem>();
        }

        child.setId(children.size() + 1);

        children.add(child);

        return this;
    }

    public ArrayList<MenuItem> getChildren() {
        return children;
    }

    public MenuItem getParent() {
        return parent;
    }
    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomQuestion() {
        return customQuestion;
    }

    public MenuItem setCustomQuestion(String question) {
        this.customQuestion = question;
        return this;
    }

    public boolean isHasChildren(){
        return hasChildren;
    }
}
