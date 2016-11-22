package com.company;

import com.company.meal.*;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class MenuQuestionnaire {

    public MenuItem menu;
    private Properties properties;
    private ArrayList<Order> order;

    public MenuQuestionnaire(Properties properties) {
        this.properties = properties;
    }

    public void start() {
        menu = getMenu();

        MenuItem currentMenuItem = menu;

        Scanner scanner = new Scanner(System.in);
        order = new ArrayList<Order>();

        boolean needToReset = false;
        while (true) {
            // if no parent or menu is null
            if(currentMenuItem == null){
                break;
            }

            boolean isLast = needToReset || isLastQuestion(currentMenuItem);

            if(isLast){
                buy(currentMenuItem);

                printLasQuestion();
            }else {
                printQuestion(currentMenuItem);
                printAnswerVariants(currentMenuItem);
            }

            String inputStr = scanner.nextLine();
            int input;

            // validate input
            try {
                input = Integer.parseInt(inputStr);
            }catch (NumberFormatException e){
                System.out.println(properties.getProperty("tryOneMoreTime"));
                continue;
            }

            if(isLast){
                needToReset = false;
                if (input == 1) {
                    currentMenuItem = menu;
                    continue;
                }

                if (input == 0) {
                    break;
                }
            }else {
                if (input == 0) {
                    if(currentMenuItem.getParent() == null){
                        break;
                    }

                    // clicked No needed additions
                    if(currentMenuItem instanceof Drink && ((Drink) currentMenuItem).isHasAdditions()){
                        buy(currentMenuItem);
                        needToReset = true;
                    }

                    currentMenuItem = currentMenuItem.getParent();
                    continue;
                }
            }

            MenuItem item = getSelectedChild(input, currentMenuItem.getChildren());

            // can't find item
            if (item == null) {
                System.out.println(properties.getProperty("tryOneMoreTime"));
                continue;
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

    private void buy(MenuItem currentMenuItem){
        MenuItem orderedMenuItem = currentMenuItem;
        if(currentMenuItem instanceof Addition){
            ((Addition) currentMenuItem).setSelected(true);
            orderedMenuItem = currentMenuItem.getParent();
        }

        String orderName = currentMenuItem.getName();
        if(orderedMenuItem instanceof MealWithAdditions){
            orderName =  ((MealWithAdditions) orderedMenuItem).getFullName();
        }

        if(currentMenuItem instanceof Addition){
            ((Addition) currentMenuItem).setSelected(false);
        }

        Order orderItem = new Order(orderName, orderedMenuItem.getPrice());
        if(orderItem.getPrice() > 0) {
            order.add(orderItem);
        }
    }

    private boolean isLastQuestion(MenuItem currentMenuItem){
        return currentMenuItem instanceof Lunch || !currentMenuItem.isHasChildren();
    }

    private void printAnswerVariants(MenuItem currentMenuItem) {
        for (MenuItem m : currentMenuItem.getChildren()) {
            System.out.println(m.getId() + ". " + m.getName());
        }

        // add last variant no, back, exit
        if(currentMenuItem instanceof Drink && ((Drink) currentMenuItem).isHasAdditions()){
            System.out.println(properties.getProperty("no"));
        }else if(!currentMenuItem.isHasChildren()){
            System.out.println(properties.getProperty("exit"));
        }else {
            System.out.println(properties.getProperty("back"));
        }
    }

    private void printQuestion(MenuItem currentMenuItem){
        if (currentMenuItem.getCustomQuestion() != null){
            System.out.println(currentMenuItem.getCustomQuestion());
        }else if(currentMenuItem instanceof Drink && ((Drink) currentMenuItem).isHasAdditions()){
            System.out.println(properties.getProperty("additionalQuestion"));
        }else {
            System.out.println(properties.getProperty("defaultQuestion"));
        }
    }

    private void printLasQuestion(){
        System.out.println(properties.getProperty("lastQuestion"));
        System.out.println(properties.getProperty("yes"));
        System.out.println(properties.getProperty("no"));
    }

    private MenuItem getSelectedChild(int input, ArrayList<MenuItem> children) {
        if(input != 0 && children != null && children.size() > 0) {
            for (MenuItem m : children){
                if(m.getId() == input){
                    return m;
                }
            }
        }
        return null;
    }

    private void printOrder(ArrayList<Order> order) {
        int cnt = 0;
        int price = 0;
        for (Order m : order){
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
                            .add(new Addition("Tequilla"))
                            .add(new Addition("Rom"))
                    )
                    .add(new Drink("Tea", 8)
                            .add(new Addition("Sugar"))
                            .add(new Addition("Lemon"))
                    )
                    .add(new Drink("Cappuchino", 10))
            )
            .add(new MenuItemImpl("Lunch")
                    .setCustomQuestion("What cuisine do you prefer")
                    .add(new MenuItemImpl(Cuisine.MEXICAN.getName())
                            .add(new Lunch("Hot and spicy", 20)
                                    .add(new MealPart("Burito"))
                                    .add(new MealPart("Guacamole"))
                            )
                    )
                    .add(new MenuItemImpl(Cuisine.POLISH.getName())
                            .add(new Lunch("Traditional Polish", 18)
                                    .add(new MealPart("Zurek"))
                                    .add(new MealPart("Sernik"))
                            )
                    )
                    .add(new MenuItemImpl(Cuisine.ITALIAN.getName())
                            .add(new Lunch("Mammamia", 25)
                                    .add(new MealPart("Pizza Quattro Stragioni"))
                                    .add(new MealPart("Tiramisu"))
                            )
                    )
            );
        return result;
    }
}

