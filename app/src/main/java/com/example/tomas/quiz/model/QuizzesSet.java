package com.example.tomas.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 11.04.2018.
 *
 * Quizzes set.
 */
public class QuizzesSet {

    private int count;
    private List<Quiz> items = new ArrayList<>();

    /**
     * Constructor.
     */
    public QuizzesSet(){

    }

    public int getCount() {
        return count;
    }

    public List<Quiz> getItems() {
        return items;
    }

}
