package com.example.tomas.quiz.model;

import io.realm.RealmObject;

/**
 * Created by tomas on 14.04.2018.
 *
 * Category of question.
 */

public class Category extends RealmObject{

    private String name;

    /**
     * Constructor.
     */
    public Category(){

    }

    public String getName() {
        return name;
    }
}
