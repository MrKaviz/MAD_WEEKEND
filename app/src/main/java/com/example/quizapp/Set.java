package com.example.quizapp;

public class Set {
    private String name;
    private int sets;

    public Set(){

    }

    public Set(String name, int sets) {
        this.name = name;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSet() {
        return sets;
    }

    public void setSet(int sets) {
        this.sets = sets;
    }
}
