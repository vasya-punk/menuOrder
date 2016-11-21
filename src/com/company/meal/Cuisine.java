package com.company.meal;

public enum Cuisine {
    MEXICAN{
        public String getName(){
         return "Mexican";
        }
    },
    POLISH{
        public String getName(){
            return "Polish";
        }
    },
    ITALIAN{
        public String getName(){
            return "Italian";
        }
    };

    public abstract  String getName();
}
