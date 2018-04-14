package com.example.tomas.quiz.model;

import io.realm.RealmObject;

/**
 * Created by tomas on 14.04.2018.
 */

public class Category extends RealmObject{

    private String name;

    public Category(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
