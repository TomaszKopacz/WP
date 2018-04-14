package com.example.tomas.quiz.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.tomas.quiz.R;
import com.example.tomas.quiz.views.MainActivity;
import com.example.tomas.quiz.views.QuestionFragment;
import com.example.tomas.quiz.views.QuizActivity;
import com.example.tomas.quiz.views.ResultFragment;
import com.example.tomas.quiz.model.Answer;
import com.example.tomas.quiz.model.Question;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizDetails;
import com.example.tomas.quiz.model.Rate;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomas on 12.04.2018.
 */

public class QuizActivityPresenter implements QuestionFragmentPresenter, ResultFragmentPresenter {

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
        currentQuiz = RealmService.with(activity).getQuiz(id);
        progress = currentQuiz.getProgress();
        score = currentQuiz.getScore();

        // set title
        activity.setTitle(currentQuiz.getTitle());

        // set background
        Glide
                .with(activity)
                .load(currentQuiz.getPhoto().getUrl())
                .centerCrop()
                .into(activity.getImageVIew());

        // count progress bar value in %
        int status = (int)(((float)progress/currentQuiz.getQuestionsCount())*100);
        activity.setProgressValue(status);

        // get quiz details: questions, answers, average score
        downloadQuizDetails(id);
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
    @Override
    public void answered(int check){

        // get checked answer
        Question q = details.getQuestions().get(progress);
        Answer a = q.getAnswers().get(check);

        // if answer is correct increment score
        if (a.getIsCorrect() == 1){
            score++;
        }

        // increment progress
        progress++;

        // actualize progress bar
        int status = (int)(((float)progress/currentQuiz.getQuestionsCount())*100);
        activity.setProgressValue(status);

        // update realm data
        Quiz quizToUpdate = new Quiz(currentQuiz);
        quizToUpdate.setProgress(progress);
        quizToUpdate.setScore(score);
        RealmService.with(activity).updateQuiz(quizToUpdate);

        // ask next question or show result if last question
        if (progress < currentQuiz.getQuestionsCount())
            askQuestion(details.getQuestions().get(progress));
        else
            showResult();
    }

    /**
     * Starts fragment to ask question.
     */
    private void askQuestion(Question question){

        // get question fragment
        QuestionFragment fragment = getQuestionFragment(question);

        // set fragment to activity
        activity.setFragment(fragment);
    }

    /**
     * Starts fragment to show result.
     */
    private void showResult(){

        // get result fragment
        ResultFragment fragment = getResultFragment();

        // set fragment to activity
        activity.setFragment(fragment);
    }

    /**
     * Returns new question fragment.
     * @param question
     * @return
     */
    private QuestionFragment getQuestionFragment(Question question){

        QuestionFragment fragment = new QuestionFragment();
        fragment.setPresenter(this);

        // set question text
        int number = details.getQuestions().indexOf(question) + 1;
        fragment.setTitle(number + ". " + question.getText());

        // set optional photo
        if (question.getImage() != null)
            fragment.loadQuestionPhoto(question.getImage().getUrl());

        // insert answers
        int count = question.getAnswers().size();
        for(int i = 0; i < count; i++){
            RadioButton btn = createAnswerButton(activity,
                    question.getAnswers().get(i).getText(), i);

            fragment.addAnswerOption(btn);
        }

        return fragment;
    }

    /**
     * Creates new radio button holding answer.
     * @param context
     * @param text
     * @param buttonId
     * @return
     */
    private RadioButton createAnswerButton(Context context, String text, int buttonId){

        // create btn
        RadioButton btn = new RadioButton(activity);
        btn.setId(buttonId);

        // set params
        btn.setText(text);
        btn.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        btn.setTextSize(16);

        // set margins
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int)context.getResources().getDimension(R.dimen.margin);
        params.setMargins(margin, margin, margin, margin);
        btn.setLayoutParams(params);

        return btn;
    }

    /**
     * Returns new result fragment.
     * @param
     * @return
     */
    private ResultFragment getResultFragment() {

        ResultFragment fragment = new ResultFragment();
        fragment.setPresenter(this);

        int result = (int)(((float)score/currentQuiz.getQuestionsCount())*100);
        fragment.setScore(result);

        for (Rate rate : details.getRates()){
            if (result >= rate.getFrom() && result <= rate.getTo()){
                fragment.setComment(rate.getContent());
            }
        }

        return fragment;
    }

    @Override
    public void exitQuiz() {

        // go to main activity
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void doQuizAgain() {

        // reset results
        progress = 0;
        score = 0;

        // update realm data
        Quiz quizToUpdate = new Quiz(currentQuiz);
        quizToUpdate.setProgress(progress);
        quizToUpdate.setScore(score);
        RealmService.with(activity).updateQuiz(quizToUpdate);

        // start this quiz again
        Intent intent = new Intent(activity, QuizActivity.class);
        intent.putExtra("id", currentQuiz.getId());
        activity.startActivity(intent);
        activity.finish();
    }
}
