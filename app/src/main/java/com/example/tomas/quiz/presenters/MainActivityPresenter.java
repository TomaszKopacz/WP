package com.example.tomas.quiz.presenters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tomas.quiz.activities.MainActivity;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizzesSet;
import com.example.tomas.quiz.services.QuizAdapter;
import com.example.tomas.quiz.services.QuizViewHolder;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;

import java.util.ArrayList;
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

        Quiz quiz = RealmService.with(activity).getQuiz(position);

        // set text views
        ((QuizViewHolder)holder).getTitle().setText(quiz.getTitle());
        ((QuizViewHolder)holder).getResult().setText("RESULT");

        // set image
        ImageView view = ((QuizViewHolder)holder).getImage();

        Glide
                .with(activity)
                .load(quiz.getPhoto().getUrl())
                .centerCrop()
                .into(view);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getItemCount() {
        return RealmService.with(activity).getQuizCount();
    }
}
