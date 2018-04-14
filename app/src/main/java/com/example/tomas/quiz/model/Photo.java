package com.example.tomas.quiz.model;

import io.realm.RealmObject;

/**
 * Created by tomas on 11.04.2018.
 *
 * Photo.
 */

public class Photo extends RealmObject {

    private String url;

    /**
     * Constructor.
     */
    public Photo(){

    }

    public String getUrl() {
        return url;
    }
}
