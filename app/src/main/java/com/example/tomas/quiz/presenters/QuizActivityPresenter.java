package com.example.tomas.quiz.presenters;

import com.example.tomas.quiz.activities.QuizActivity;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizDetails;
import com.example.tomas.quiz.services.WebService;

/**
 * Created by tomas on 12.04.2018.
 */

public class QuizActivityPresenter {

    private QuizActivity activity;
    private WebService service;

    private Quiz quiz;
    private QuizDetails details;

    public QuizActivityPresenter(QuizActivity activity, WebService service){
        this.activity = activity;
        this.service = service;
    }

    public void startQuiz(String id){

    }
}
