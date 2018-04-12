package com.example.tomas.quiz.presenters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.tomas.quiz.activities.QuestionFragment;
import com.example.tomas.quiz.activities.QuizActivity;
import com.example.tomas.quiz.model.Question;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizDetails;
import com.example.tomas.quiz.model.Rate;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomas on 12.04.2018.
 */

public class QuizActivityPresenter {

    private QuizActivity activity;
    private WebService service;

    // quiz
    private Quiz currentQuiz;

    private String quizID;
    private String quizTitle;
    private int questionsCount;
    private int progress = 0;
    private int score = 0;

    // quiz details
    private QuizDetails details;

    private float avgScore;
    private List<Question> questions = new ArrayList<>();
    private List<Rate> rates = new ArrayList<>();


    public QuizActivityPresenter(QuizActivity activity, WebService service){
        this.activity = activity;
        this.service = service;
    }

    /**
     * Download questions and starts quiz.
     * @param id
     */
    public void startQuiz(String id){

        // get current quiz and set layout
        currentQuiz = getQuiz(id);
        activity.setTitle(currentQuiz.getTitle());
        activity.setProgressValue(currentQuiz.getProgress());

        // get quiz details: questions, answers, average score
        downloadQuizDetails(id);
    }

    /**
     * Gets quiz from database.
     * @param id
     */
    private Quiz getQuiz(String id){
        return RealmService.with(activity).getQuiz(id);
    }

    /**
     * Gets quiz details from web.
     */
    private void downloadQuizDetails(String id){
        Call<QuizDetails> call = service.getQuizDetails(id);
        call.enqueue(new Callback<QuizDetails>() {
            @Override
            public void onResponse(Call<QuizDetails> call, Response<QuizDetails> response) {
                details = response.body();
                askQuestion(details.getQuestions().get(progress));
            }

            @Override
            public void onFailure(Call<QuizDetails> call, Throwable t) {

            }
        });
    }

    /**
     * Starts interface to ask question
     */
    private void askQuestion(Question question){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        QuestionFragment fragment = new QuestionFragment();
        fragment.setPresenter(this);
        transaction.add(activity.getFrame().getId(), fragment);
        transaction.commit();
    }

    public void answered(int check){
        if (details.getQuestions().get(progress).getAnswers().get(check-1).getIsCorrect() == 1){
            Toast.makeText(activity, "SUPER!!!", Toast.LENGTH_LONG).show();
        }
    }
}
