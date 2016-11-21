package com.company;

import com.company.meal.Cuisine;
import com.company.meal.Drink;
import com.company.meal.Lunch;

import java.util.ArrayList;
import java.util.Scanner;


public class MenuQuestionnaire {

    public static final String DEFAULT_QUESTION = "What would you like to order?";
    public static final String ADITIONAL_QUESTION = "Do you want to add something additional?";
    public static final String LAST_QUESTION = "Do you want to order something more?";
    public static final String BACK = "0. Back";
    public static final String EXIT = "0. Exit";
    public static final String YES= "1. Yes";
    public static final String NO= "0. No";

    public MenuItem menu;
    public MenuItem currentMenuItem;
    public ArrayList<MenuItem> order;

    public void start() {
        menu = getMenu();

        currentMenuItem = menu;

        Scanner scanner = new Scanner(System.in);
        order = new ArrayList<MenuItem>();

        while (true) {
            // if no parent or menu is null
            if(currentMenuItem == null){
                break;
            }

            boolean isLastQuestion = false;
            if(!(currentMenuItem instanceof Lunch && currentMenuItem.isHasChildren())) {
                printQuestion(currentMenuItem);
                printAnswerVariants(currentMenuItem);
            }else {
                isLastQuestion = true;
                System.out.println(LAST_QUESTION);
                System.out.println(YES);
                System.out.println(NO);
            }

            // validate input
            String input = scanner.nextLine();
            if(isLastQuestion){
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
                System.out.println("Try one more time");
                continue;
            }

            // putting order
            if(item.getPrice() > 0)
                order.add(item);

            currentMenuItem = item;
        }

        scanner.close();

        if(order.size() > 0){
            System.out.println("Your order submitted:");
            printOrder();
        }else {
            System.out.println("See you next time!");
        }
    }

    private void printAnswerVariants(MenuItem currentMenuItem) {
        for (MenuItem m : currentMenuItem.getChildren()) {
            System.out.println(m.getId() + ". " + m.getName());
        }

        if(currentMenuItem instanceof Drink && currentMenuItem.isHasChildren()){
            System.out.println(NO);
        }else if(currentMenuItem.getParent() == null){
            System.out.println(EXIT);
        }else {
            System.out.println(BACK);
        }
    }

    private void printQuestion(MenuItem currentMenuItem){
        if (currentMenuItem.getCustomQuestion() != null){
            System.out.println(currentMenuItem.getCustomQuestion());
        }else if(currentMenuItem instanceof Drink && currentMenuItem.isHasChildren()){
            System.out.println(ADITIONAL_QUESTION);
        }else {
            System.out.println(DEFAULT_QUESTION);
        }
    }

    private void printOrder() {
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

    public MenuItem getMenu(){
        MenuItem result = new MenuItem("Menu")
            .add(new MenuItem("Drinks")
                            .setCustomQuestion("What drink would you prefer?")
                            .add(new Drink("Long Ireland", 15)
                                            .add(new MenuItem("Rom"))
                                            .add(new MenuItem("Tequilla"))
                            )
                            .add(new Drink("Tea", 8)
                                            .add(new MenuItem("Sugar"))
                                            .add(new MenuItem("Lemon"))
                            )
                            .add(new Drink("Cappuchino", 10))
            )
            .add(new MenuItem("Lunch")
                            .setCustomQuestion("What cuisine do you prefer")
                            .add(new MenuItem(Cuisine.MEXICAN.getName())
                                            .add(new Lunch("Hot and spicy", Cuisine.MEXICAN, 20)
                                                            .add(new MenuItem("Burito"))
                                                            .add(new MenuItem("Guacamole"))
                                            )
                            )
                            .add(new MenuItem(Cuisine.POLISH.getName())
                                            .add(new Lunch("Traditional Polish", Cuisine.POLISH, 18)
                                                            .add(new MenuItem("Zurek"))
                                                            .add(new MenuItem("Sernik"))
                                            )
                            )
                            .add(new MenuItem(Cuisine.ITALIAN.getName())
                                            .add(new Lunch("Mammamia", Cuisine.ITALIAN, 25)
                                                            .add(new MenuItem("Pizza Quattro Stragioni"))
                                                            .add(new MenuItem("Tiramisu"))
                                            )
                            )
            );
        return result;
    }
}

