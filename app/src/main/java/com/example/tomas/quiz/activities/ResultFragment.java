package com.example.tomas.quiz.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.Rate;
import com.example.tomas.quiz.presenters.QuizActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ResultFragment extends Fragment {

    private QuizActivityPresenter presenter;

    private int score = 0;
    private List<Rate> rates = new ArrayList<>();

    @BindView(R.id.score)
    TextView scoreView;

    @BindView(R.id.rate)
    TextView rateView;

    @BindView(R.id.again)
    Button againBtn;

    @BindView(R.id.back)
    Button backBtn;

    public ResultFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        // bind views
        ButterKnife.bind(this, view);

        // set data
        setData();

        return view;
    }

    private void setData(){
        // show percent result
        scoreView.append(score + "%");

        // show message
        if (!rates.isEmpty()){
            for (Rate rate : rates){
                if (score >= rate.getFrom() && score < rate.getTo()){
                    rateView.setText(rate.getContent());
                }
            }
        }
    }

    public void setPresenter(QuizActivityPresenter presenter){
        this.presenter = presenter;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setRates(List<Rate> rates){
        this.rates = rates;
    }

}
