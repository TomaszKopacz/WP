package com.example.tomas.quiz.model;

/**
 * Created by tomas on 12.04.2018.
 */

public class Answer {

    private Photo image;
    private String text;
    private int isCorrect;

    public Answer(){

    }

    public Photo getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public int getIsCorrect() {
        return isCorrect;
    }
}
