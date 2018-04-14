package com.example.tomas.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 12.04.2018.
 *
 * Question.
 */

public class Question {

    private String type;
    private String answer;
    private Photo image;
    private String text;

    private List<Answer> answers = new ArrayList<>();

    /**
     * Constructor.
     */
    public Question(){

    }

    public String getType() {
        return type;
    }

    public String getAnswer() {
        return answer;
    }

    public Photo getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
