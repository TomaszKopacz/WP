package com.example.tomas.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 12.04.2018.
 */

public class Question {

    private String type;
    private String answer;

    private Photo image;
    private String text;

    private List<Answer> answers = new ArrayList<>();

}
