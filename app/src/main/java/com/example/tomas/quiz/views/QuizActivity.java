package com.example.tomas.quiz.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tomas.quiz.R;
import com.example.tomas.quiz.app.MainApp;
import com.example.tomas.quiz.presenters.QuizActivityPresenter;
import com.example.tomas.quiz.services.WebService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Quiz activity.
 * Contained of question fragment or result fragment if all questions are answered.
 * Questions loaded from web are shown in question fragment.
 * To show progress of quiz, progress bar is implemented.
 */
public class QuizActivity extends FragmentActivity {

    private QuizActivityPresenter presenter;

    @Inject
    WebService service;

    @BindView(R.id.image_layout)
    ImageView image;

    @BindView(R.id.quiz_title)
    TextView titleView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.frame)
    FrameLayout frame;

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

    // get quiz data and init quiz
    private void init(){
        String quizId = getIntent().getStringExtra("id");
        presenter.startQuiz(quizId);
    }

    /**
     * Sets title of quiz.
     * @param title
     */
    public void setTitle(String title){
        titleView.setText(title);
    }

    /**
     * Sets image.
     * @param url
     */
    public void setImage(String url){
        Glide
                .with(this)
                .load(url)
                .centerCrop()
                .into(image);
    }

    /**
     * Sets fragment.
     * @param fragment
     */
    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStack();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        frame.removeAllViews();
        transaction.add(frame.getId(), fragment);
        transaction.commit();
    }

    /**
     * Sets progress value.
     * @param progress
     */
    public void setProgressValue(int progress){
        progressBar.setProgress(progress);
    }

    public ImageView getImageVIew(){
        return image;
    }
}
