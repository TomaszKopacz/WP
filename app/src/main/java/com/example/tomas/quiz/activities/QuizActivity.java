package com.example.tomas.quiz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.app.MainApp;
import com.example.tomas.quiz.presenters.QuizActivityPresenter;
import com.example.tomas.quiz.services.WebService;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {

    private QuizActivityPresenter presenter;

    @Inject
    WebService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // butter knife
        ButterKnife.bind(this);

        // dependency injection
        ((MainApp)getApplication()).getWebServiceComponent().inject(this);

        // presenter
        presenter = new QuizActivityPresenter(this, service);

        // init
        init();
    }

    private void init(){
        String quizId = getIntent().getStringExtra("id");
        presenter.startQuiz(quizId);
    }
}
