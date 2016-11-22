package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/resources/resources.properties");
            properties.load(fis);

            new MenuQuestionnaire(properties).start();

        } catch (IOException e) {
            System.err.println("ERROR: Resources file is not exist!");
        }
    }
}
