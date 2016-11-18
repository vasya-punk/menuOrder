package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class MenuItem {

    private String name;
    private ArrayList<MenuItem> children;
    private ArrayList<MenuItem> parts;
    private MenuItem parent;
    private long price;
    private boolean additionalChooser;
    private int id;
    private String question;

    public MenuItem(String name){
        this.name = name;
    }

    public MenuItem(String name, long price){
        this(name);

        this.price = price;
    }

    public MenuItem(String name, long price, boolean additionalChooser){
        this(name, price);

        this.additionalChooser = additionalChooser;
    }

    public MenuItem add(MenuItem child){
        child.setParent(this);

        if(children == null){
            children = new ArrayList<MenuItem>();
        }

        child.setId(children.size() + 1);

        children.add(child);

        return this;
    }

    public MenuItem addPart(MenuItem child){
        child.setParent(this);

        if(parts == null){
            parts = new ArrayList<MenuItem>();
        }

        child.setId(parts.size() + 1);

        parts.add(child);

        return this;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        String result = name;
        if(parts != null && parts.size() > 0){
            result += "\n"+ getPartsDescription();
        }
        return result;
    }

    public ArrayList<MenuItem> getChildren() {
        return children;
    }

    public long getPrice() {
        return price;
    }

    public MenuItem getParent() {
        return parent;
    }
    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    private String getPartsDescription(){
        StringBuffer result = new StringBuffer("(");
        for (MenuItem m : parts){
            result.append(m.getName()+", ");
        }
        result.delete(result.lastIndexOf(", "), result.length());
        result.append(")");
        return result.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAdditionalChooser() {
        return additionalChooser;
    }

    public MenuItem setCustomQuestion(String question) {
        this.question = question;
        return this;
    }
}
