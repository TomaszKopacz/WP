package com.example.tomas.quiz.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.model.Answer;
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

    @BindView(R.id.radio_group)
    RadioGroup group;


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
        int count = question.getAnswers().size();

        for(int i = 0; i < count; i++){
            RadioButton btn = new RadioButton(getContext());
            btn.setId(i);
            btn.setText(question.getAnswers().get(i).getText());
            btn.setTextColor(getResources().getColor(R.color.colorTextPrimary));
            btn.setTextSize(20);

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            int margin = (int)getResources().getDimension(R.dimen.margin);
            params.setMargins(margin, margin, margin, margin);
            btn.setLayoutParams(params);

            group.addView(btn);
        }

        group.setOnCheckedChangeListener(listener);
    }

    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            presenter.answered(i);
        }
    };
}
