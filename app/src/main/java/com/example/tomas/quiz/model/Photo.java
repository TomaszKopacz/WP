package com.example.tomas.quiz.model;

import io.realm.RealmObject;

/**
 * Created by tomas on 11.04.2018.
 *
 * Class representing photo.
 */

public class Photo extends RealmObject {

    private String url;

    /**
     * Empty constructor.
     */
    public Photo(){

    }

    /**
     * Constructor.
     * @param url
     */
    public Photo(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
