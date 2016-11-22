package com.company;

import com.company.meal.Drink;
import com.company.meal.Lunch;
import com.company.meal.Cuisine;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class MenuQuestionnaire {

    public MenuItem menu;
    private Properties properties;

    public MenuQuestionnaire(Properties properties) {
        this.properties = properties;
    }

    public void start() {
        menu = getMenu();

        MenuItem currentMenuItem = menu;

        Scanner scanner = new Scanner(System.in);
        ArrayList<MenuItem> order = new ArrayList<MenuItem>();

        while (true) {
            // if no parent or menu is null
            if(currentMenuItem == null){
                break;
            }

            boolean isNotLast = isNotLastQuestion(currentMenuItem);
            printQuestion(currentMenuItem, isNotLast);
            printAnswerVariants(currentMenuItem, isNotLast);

            // validate input
            String input = scanner.nextLine();
            if(!isNotLast){
                if ("0".equals(input)) {
                    break;
                }

                if ("1".equals(input)) {
                    currentMenuItem = menu;
                    continue;
                }
            }else {
                if ("0".equals(input)) {
                    if(currentMenuItem.getParent() == null){
                        break;
                    }else if(!(currentMenuItem instanceof Drink)){
                        currentMenuItem = currentMenuItem.getParent();
                        continue;
                    }else {
                        input = Integer.toString(currentMenuItem.getId());
                    }
                }
            }

            MenuItem item = getSelectedChild(input, currentMenuItem.getChildren());
            if (item == null) {
                System.out.println(properties.getProperty("tryOneMoreTime"));
                continue;
            }

            if(item.getPrice() > 0) {
                order.add(item);
            }

            currentMenuItem = item;
        }

        scanner.close();

        // putting order
        if(order.size() > 0){
            System.out.println(properties.getProperty("orderSubmitted"));
            printOrder(order);
        }else {
            System.out.println(properties.getProperty("seeYou"));
        }
    }

    private boolean isNotLastQuestion(MenuItem currentMenuItem){
        return !(currentMenuItem instanceof Lunch) && currentMenuItem.getChildren() != null;
    }

    private void printAnswerVariants(MenuItem currentMenuItem, boolean isNotLast) {
        if(isNotLast && currentMenuItem.isHasChildren()){
            for (MenuItem m : currentMenuItem.getChildren()) {
                System.out.println(m.getId() + ". " + m.getName());
            }

            // add last variant no, back, exit
            if(currentMenuItem instanceof Drink && currentMenuItem.isHasChildren()){
                System.out.println(properties.getProperty("no"));
            }else if(currentMenuItem.getParent() == null){
                System.out.println(properties.getProperty("exit"));
            }else {
                System.out.println(properties.getProperty("back"));
            }
        }else {
            System.out.println(properties.getProperty("yes"));
            System.out.println(properties.getProperty("no"));
        }
    }

    private void printQuestion(MenuItem currentMenuItem, boolean isNotLast){
        if(isNotLast){
            if (currentMenuItem.getCustomQuestion() != null){
                System.out.println(currentMenuItem.getCustomQuestion());
            }else if(currentMenuItem instanceof Drink && currentMenuItem.isHasChildren()){
                System.out.println(properties.getProperty("additionalQuestion"));
            }else {
                System.out.println(properties.getProperty("defaultQuestion"));
            }
        }else {
            System.out.println(properties.getProperty("lastQuestion"));
        }
    }

    private MenuItem getSelectedChild(String input, ArrayList<MenuItem> children) {
        int answerId;

        try {
            answerId = Integer.parseInt(input);
        }catch (NumberFormatException e){
            return null;
        }

        if(answerId != 0 && children != null && children.size() > 0) {
            for (MenuItem m : children){
                if(m.getId() == answerId){
                    return m;
                }
            }
        }
        return null;
    }

    private void printOrder(ArrayList<MenuItem> order) {
        int cnt = 0;
        int price = 0;
        for (MenuItem m : order){
            if(m.getPrice() > 0){
                cnt++;
                System.out.println(cnt + "." + m.getName() + " : " + m.getPrice());
                price += m.getPrice();
            }
        }

        System.out.println("-----------------------");
        System.out.println(price);
    }

    public MenuItem getMenu(){
        MenuItem result = new MenuItemImpl("Menu")
            .add(new MenuItemImpl("Drinks")
                    .setCustomQuestion("What drink would you prefer?")
                    .add(new Drink("Long Ireland", 15)
                            .setCustomQuestion("Do you want more alcohol to the cocktail?")
                            .add(new MenuItemImpl("Tequilla"))
                            .add(new MenuItemImpl("Rom"))
                    )
                    .add(new Drink("Tea", 8)
                            .add(new MenuItemImpl("Sugar"))
                            .add(new MenuItemImpl("Lemon"))
                    )
                    .add(new Drink("Cappuchino", 10))
            )
            .add(new MenuItemImpl("Lunch")
                    .setCustomQuestion("What cuisine do you prefer")
                    .add(new MenuItemImpl(Cuisine.MEXICAN.getName())
                            .add(new Lunch("Hot and spicy", 20)
                                    .add(new MenuItemImpl("Burito"))
                                    .add(new MenuItemImpl("Guacamole"))
                            )
                    )
                    .add(new MenuItemImpl(Cuisine.POLISH.getName())
                            .add(new Lunch("Traditional Polish", 18)
                                    .add(new MenuItemImpl("Zurek"))
                                    .add(new MenuItemImpl("Sernik"))
                            )
                    )
                    .add(new MenuItemImpl(Cuisine.ITALIAN.getName())
                            .add(new Lunch("Mammamia", 25)
                                    .add(new MenuItemImpl("Pizza Quattro Stragioni"))
                                    .add(new MenuItemImpl("Tiramisu"))
                            )
                    )
            );
        return result;
    }
}

