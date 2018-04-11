package com.example.tomas.quiz.presenters;

import android.view.View;

import com.example.tomas.quiz.activities.MainActivity;
import com.example.tomas.quiz.services.WebService;

/**
 * Created by tomas on 11.04.2018.
 *
 * Presenter for MainActivity.
 */

public class MainActivityPresenter implements RecyclerViewPresenter {

    private MainActivity activity;
    private WebService service;

    /**
     * Constructor.
     * @param activity
     * @param service
     */
    public MainActivityPresenter(MainActivity activity, WebService service){
        this.activity = activity;
        this.service = service;
    }

    /**
     * Downloads list of quizzes.
     */
    public void downloadQuizzes(){

    }

    @Override
    public void onBindView(View view) {

    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
