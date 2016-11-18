package com.company.meal;

import java.util.ArrayList;

public interface Meal {
    public long getPrice();
    public String getName();

    public ArrayList<Meal> getParts();
    public String getPartsDescription(ArrayList<Meal> parts);
    public boolean isCanChoose();
    public boolean isCanAdd();
}
