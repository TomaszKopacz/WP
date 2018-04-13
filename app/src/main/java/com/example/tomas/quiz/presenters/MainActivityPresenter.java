package com.example.tomas.quiz.presenters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tomas.quiz.activities.MainActivity;
import com.example.tomas.quiz.activities.QuizActivity;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizzesSet;
import com.example.tomas.quiz.services.QuizAdapter;
import com.example.tomas.quiz.services.QuizViewHolder;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomas on 11.04.2018.
 *
 * Presenter for MainActivity.
 */

public class MainActivityPresenter implements RecyclerViewPresenter {

    private MainActivity activity;
    private WebService service;

    // list of quizzes
    private QuizAdapter adapter;

    /**
     * Constructor.
     * @param activity
     * @param service
     */
    public MainActivityPresenter(MainActivity activity, WebService service){
        this.activity = activity;
        this.service = service;

        this.adapter = new QuizAdapter(this);
    }

    /**
     * Downloads list of quizzes.
     */
    public void downloadQuizzes() {

        if (RealmService.with(activity).isEmpty()){

            // when new elements downloaded
            Call call = service.getQuizzes();
            call.enqueue(new Callback<QuizzesSet>() {

                @Override
                public void onResponse(Call<QuizzesSet> call, Response<QuizzesSet> response) {

                    // assign response objects to realm
                    List<Quiz> list = response.body().getItems();
                    RealmService.with(activity).insertQuizzes(list);

                    // send quizzes to activity
                    activity.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<QuizzesSet> call, Throwable t) {

                }
            });

        } else {
            activity.setAdapter(adapter);
        }
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {

        QuizViewHolder quizHolder = (QuizViewHolder)holder;
        Quiz quiz = RealmService.with(activity).getQuiz(position);

        // set title
        quizHolder.getTitle().setText(quiz.getTitle());

        // set image
        ImageView view = quizHolder.getImage();

        Glide
                .with(activity)
                .load(quiz.getPhoto().getUrl())
                .fitCenter()
                .centerCrop()
                .into(view);

        // set results
        int questionsCount = quiz.getQuestionsCount();
        int numOfAns = quiz.getProgress();
        int score = quiz.getScore();

        if (numOfAns < questionsCount){

            // user haven't completed quiz

            // set text
            quizHolder.getResult().setText("Postęp:");

            // show progress bar and num of answers
            // count progress in %
            int progress = (int)(((float)numOfAns/questionsCount)*100);
            quizHolder.getProgressBar().setProgress(progress);
            quizHolder.getScore().setText(numOfAns + "/" + questionsCount);

            quizHolder.getScore().setTextColor(Color.BLACK);

        } else {

            // quiz is complete

            // set text
            quizHolder.getResult().setText("Wynik:");

            // show progress bar and score
            // count result in %
            int result = (int)(((float)score/questionsCount)*100);
            quizHolder.getProgressBar().setProgress(result);
            quizHolder.getScore().setText(result + "%");

            if (result < 30)
                quizHolder.getScore().setTextColor(Color.RED);
            else if (result > 70)
                quizHolder.getScore().setTextColor(Color.GREEN);
            else
                quizHolder.getScore().setTextColor(Color.YELLOW);




        }
    }

    @Override
    public void onClick(View view) {

        // get clicked quiz
        int position = activity.getRecyclerView().getChildAdapterPosition(view);
        Quiz quiz = RealmService.with(activity).getQuiz(position);
        String id = quiz.getId();

        // start quiz activity
        Intent intent = new Intent(activity, QuizActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public int getItemCount() {
        return RealmService.with(activity).getQuizCount();
    }
}
