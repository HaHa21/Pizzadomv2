package com.pizzadom.pizzadom;

/**
 * Created by User on 24/6/2017.
 */

public class Person {
    private String name;
    private String phone;


    public Person(String name, String phone){
          this.name = name;
          this.phone = phone;
    }

    public String getPhone(){return phone;}

    public String getName(){
        return name;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String Phone){
        this.phone = Phone;
    }


}
