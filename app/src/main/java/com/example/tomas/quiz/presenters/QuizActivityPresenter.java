package com.example.tomas.quiz.presenters;

import com.example.tomas.quiz.activities.QuestionFragment;
import com.example.tomas.quiz.activities.QuizActivity;
import com.example.tomas.quiz.activities.ResultFragment;
import com.example.tomas.quiz.model.Answer;
import com.example.tomas.quiz.model.Question;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizDetails;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;

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

    // quiz details
    private QuizDetails details;

    private int progress = 0;
    private int score = 0;

    public QuizActivityPresenter(QuizActivity activity, WebService service){
        this.activity = activity;
        this.service = service;
    }

    /**
     * Download questions and starts quiz.
     * @param id
     */
    public void startQuiz(String id){

        // get current quiz values
        currentQuiz = getQuiz(id);
        progress = currentQuiz.getProgress();
        score = currentQuiz.getScore();

        // set title
        activity.setTitle(currentQuiz.getTitle());

        // count progress bar value in %
        int status = (int)(((float)progress/currentQuiz.getQuestionsCount())*100);
        activity.setProgressValue(status);

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

                if (progress < currentQuiz.getQuestionsCount())
                    // ask next question
                    askQuestion(details.getQuestions().get(progress));

                else
                    // it was last question: show result
                    showResult();
            }

            @Override
            public void onFailure(Call<QuizDetails> call, Throwable t) {

            }
        });
    }

    /**
     * Reacts the answer.
     * @param check
     */
    public void answered(int check){

        // get answer checked
        Question q = details.getQuestions().get(progress);
        Answer a = q.getAnswers().get(check-1);

        // if answer is correct increment score
        if (a.getIsCorrect() == 1){
            score++;
        }

        // update progress
        progress++;

        // update data to realm
        Quiz quizToUpdate = new Quiz(currentQuiz);
        quizToUpdate.setProgress(progress);
        quizToUpdate.setScore(score);
        RealmService.with(activity).updateQuiz(quizToUpdate);

        // actualize progress bar
        int status = (int)(((float)progress/currentQuiz.getQuestionsCount())*100);
        activity.setProgressValue(status);


        if (progress < currentQuiz.getQuestionsCount())
            // ask next question
            askQuestion(details.getQuestions().get(progress));

        else
            // it was last question: show result
            showResult();
    }

    /**
     * Starts interface to ask question.
     */
    private void askQuestion(Question question){

        // get question fragment
        QuestionFragment fragment = getQuestionFragment(question);

        // set fragment to activity
        activity.setFragment(fragment);
    }

    private void showResult(){

        // get result fragment
        ResultFragment fragment = getResultFragment();

        // set fragment to activity
        activity.setFragment(fragment);
    }

    private QuestionFragment getQuestionFragment(Question question){
        QuestionFragment fragment = new QuestionFragment();
        fragment.setPresenter(this);
        fragment.setQuestion(question);

        return fragment;
    }

    private ResultFragment getResultFragment() {
        ResultFragment fragment = new ResultFragment();
        fragment.setPresenter(this);
        fragment.setScore((int)(((float)score/currentQuiz.getQuestionsCount())*100));
        fragment.setRates(details.getRates());

        return fragment;
    }
}
