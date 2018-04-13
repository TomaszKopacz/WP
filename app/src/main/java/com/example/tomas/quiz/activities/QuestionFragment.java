package com.example.tomas.quiz.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.model.Question;
import com.example.tomas.quiz.presenters.QuizActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 *
 */
public class QuestionFragment extends Fragment {

    private QuizActivityPresenter presenter;
    private Question question;

    @BindView(R.id.question)
    TextView questionTextView;

    @BindView(R.id.radio1)
    RadioButton button1;

    @BindView(R.id.radio2)
    RadioButton button2;

    @BindView(R.id.radio3)
    RadioButton button3;

    @BindView(R.id.radio4)
    RadioButton button4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate view
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        // bind views
        ButterKnife.bind(this, view);

        // set data
        setData();

        return view;
    }

    public void setPresenter(QuizActivityPresenter presenter){
        this.presenter = presenter;
    }

    public void setQuestion(Question question){
        this.question = question;
    }

    private void setData(){
        questionTextView.setText(question.getText());
        button1.setText(question.getAnswers().get(0).getText());
        button2.setText(question.getAnswers().get(1).getText());
        button3.setText(question.getAnswers().get(2).getText());
        button4.setText(question.getAnswers().get(3).getText());
    }

    @OnCheckedChanged({
            R.id.radio1,
            R.id.radio2,
            R.id.radio3,
            R.id.radio4
    })
    public void onRadioSelected(CompoundButton button, boolean checked){
        if (checked){
            switch (button.getId()){
                case R.id.radio1:
                    presenter.answered(1);
                    break;

                case R.id.radio2:
                    presenter.answered(2);
                    break;

                case R.id.radio3:
                    presenter.answered(3);
                    break;

                case R.id.radio4:
                    presenter.answered(4);
                    break;
            }
        }
    }
}
