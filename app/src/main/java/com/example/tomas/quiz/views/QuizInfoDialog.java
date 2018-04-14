package com.example.tomas.quiz.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.presenters.QuizInfoDialogPresenter;
import com.github.rubensousa.raiflatbutton.RaiflatButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tomas on 14.04.2018.
 */

public class QuizInfoDialog extends Dialog {

    private QuizInfoDialogPresenter presenter;
    private Quiz quiz;

    @BindView(R.id.category)
    TextView categoryView;

    @BindView(R.id.description)
    TextView descriptionView;

    @BindView(R.id.start_quiz)
    RaiflatButton startBtn;

    /**
     * Constructor.
     * @param context
     */
    public QuizInfoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_info_dialog);

        // bind views
        ButterKnife.bind(this);

        // set up
        setUp();
    }

    /**
     * Sets layout content.
     */
    private void setUp() {

        setTitle("Rozpocznij quiz");

        if (quiz.getContent().equals(""))
            descriptionView.setVisibility(View.GONE);
        else
            descriptionView.setText(quiz.getContent());

        if (quiz.getCategory().getName().equals(""))
            categoryView.setVisibility(View.GONE);
        else
            categoryView.setText("Kategoria: " + quiz.getCategory().getName());
    }

    @OnClick(R.id.start_quiz)
    public void startBtnClicked(){
        if (presenter != null)
            presenter.startQuiz(quiz);
    }

    /**
     * Sets presenter for this dialog.
     * @param presenter
     */
    public void setPresenter(QuizInfoDialogPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Sets quiz for this dialog.
     * @param quiz
     */
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
