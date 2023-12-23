package org.example.models;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private int id;
    private String name;

    private int year;

    public Person(){}

    public Person(int id){
        this.id = id;
    }

    public Person(String name, int year){
        this.name = name;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
