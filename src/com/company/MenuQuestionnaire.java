package com.company;

import com.company.meal.Cuisine;

import java.util.ArrayList;
import java.util.Scanner;


public class MenuQuestionnaire {

    public static final String DEFAULT_QUESTION = "What would you like to order?";
    public static final String ADITIONAL_QUESTION = "Do you want to add something additional?";
    public static final String LAST_QUESTION = "Do you want to order something more? (y/n)";

    public MenuItem menu;
    public MenuItem currentMenuItem;
    public ArrayList<MenuItem> order;

    public void start() {
        menu = getMenu();

        currentMenuItem = menu;

        Scanner scanner = new Scanner(System.in);
        order = new ArrayList<MenuItem>();

        while (true) {
            if(currentMenuItem == null){
                break;
            }

            String question = getQuestion(currentMenuItem);
            System.out.println(question);

            String input = scanner.nextLine();

            if(question.equals(LAST_QUESTION)){
                if ("n".equals(input)) {
                    break;
                }

                if ("y".equals(input)) {
                    currentMenuItem = menu;
                    continue;
                }
            }else {
                if ("0".equals(input)) {
                    if(!currentMenuItem.isAdditionalChooser()){
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

            if(item.getPrice() > 0)
                order.add(item);

            currentMenuItem = item;
        }

        scanner.close();

        if(order.size() > 0){
            String submittedOrder = getOrder();
            System.out.println("Your order submitted: \n" + submittedOrder);
        }else {
            System.out.println("See you next time!");
        }
    }

    private String getQuestion(MenuItem currentMenuItem){
        String result;
        if (currentMenuItem.getChildren() != null && currentMenuItem.getChildren().size() > 0) {
            if(currentMenuItem.getQuestion() != null){
                result = currentMenuItem.getQuestion();
            }else {
                if(currentMenuItem.isAdditionalChooser()){
                    result = ADITIONAL_QUESTION;
                }else {
                    result = DEFAULT_QUESTION;
                }
            }

            result += "\n";

            for (MenuItem m : currentMenuItem.getChildren()) {
                result += m.getId() + ". " + m.getFullName() + "\n";
            }

            if(currentMenuItem.isAdditionalChooser()){
                result += "0. No";
            }else {
                result +="0. Back";
            }
        }else {
            result = LAST_QUESTION;
        }

        return result;
    }

    private String getOrder() {
        StringBuffer result = new StringBuffer();
        int cnt = 0;
        int price = 0;
        for (MenuItem m : order){
            if(m.getPrice() > 0){
                cnt++;
                result.append(cnt + "." + m.getName() + " : " + m.getPrice() + "\n");
                price += m.getPrice();
            }
        }

        result.append("-----------------------\n");
        result.append(price);

        return result.toString();
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
                            .add(new MenuItem("Long Ireland", 15, true)
                                            .add(new MenuItem("Rom"))
                                            .add(new MenuItem("Tequilla"))
                            )
                            .add(new MenuItem("Tea", 8, true)
                                            .add(new MenuItem("Sugar"))
                                            .add(new MenuItem("Lemon"))
                            )
                            .add(new MenuItem("Cappuchino", 10, true)
                                            .add(new MenuItem("Cinnamon"))
                                            .add(new MenuItem("Chocolate"))
                            )
            )
            .add(new MenuItem("Lunch")
                            .setCustomQuestion("What cuisine do you prefer")
                            .add(new MenuItem(Cuisine.MEXICAN.toString())
                                            .add(new MenuItem("Hot and spicy", 20)
                                                            .addPart(new MenuItem("Burito"))
                                                            .addPart(new MenuItem("Guacamole"))
                                            )
                            )
                            .add(new MenuItem(Cuisine.POLISH.toString())
                                            .add(new MenuItem("Traditional Polish", 18)
                                                            .addPart(new MenuItem("Zurek"))
                                                            .addPart(new MenuItem("Sernik"))
                                            )
                            )
                            .add(new MenuItem(Cuisine.POLISH.toString())
                                            .add(new MenuItem("Mammamia", 25)
                                                            .addPart(new MenuItem("Pizza Quattro Stragioni"))
                                                            .addPart(new MenuItem("Tiramisu"))
                                            )
                            )
            );
        return result;
    }
}

