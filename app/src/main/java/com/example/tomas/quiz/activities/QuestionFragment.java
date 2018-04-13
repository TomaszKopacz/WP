package com.example.tomas.quiz.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tomas.quiz.R;
import com.example.tomas.quiz.presenters.QuestionFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class QuestionFragment extends Fragment {

    private QuestionFragmentPresenter presenter;

    private String title;
    private String photoUrl;
    private List<RadioButton> buttons = new ArrayList<>();

    @BindView(R.id.question)
    TextView questionTextView;

    @BindView(R.id.question_image)
    ImageView imageView;

    @BindView(R.id.radio_group)
    RadioGroup group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate view
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // bind views
        ButterKnife.bind(this, view);

        // load content
        setUp();

        // set listener
        group.setOnCheckedChangeListener(listener);

        return view;
    }

    private void setUp(){

        // set question
        if (title != null)
            questionTextView.setText(title);

        // set question image
        if (photoUrl != null)
            Glide
                .with(this)
                .load(photoUrl)
                .centerCrop()
                .into(imageView);

        // set answers
        for (RadioButton btn : buttons)
            group.addView(btn);
    }

    /**
     * Sets presenter for this fragment.
     * @param presenter
     */
    public void setPresenter(QuestionFragmentPresenter presenter){
        this.presenter = presenter;
    }

    /**
     * Sets question text.
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets question photo.
     * @param url
     */
    public void loadQuestionPhoto(String url){
        this.photoUrl = url;
    }

    /**
     * Adds new answer.
     * @param radio
     */
    public void addAnswerOption(RadioButton radio){
        buttons.add(radio);
    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            presenter.answered(i);
        }
    };
}
