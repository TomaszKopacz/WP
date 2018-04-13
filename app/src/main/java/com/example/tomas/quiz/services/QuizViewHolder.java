package com.example.tomas.quiz.services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tomas.quiz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tomas on 11.04.2018.
 *
 * View Holder for quiz item.
 */

public class QuizViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.quiz_image)
    ImageView image;

    @BindView(R.id.quiz_title)
    TextView title;

    @BindView(R.id.quiz_result)
    TextView result;

    @BindView(R.id.quiz_score)
    TextView score;

    @BindView(R.id.progress_bar_result)
    ProgressBar progressBar;

    /**
     * Constructor.
     * @param itemView
     */
    public QuizViewHolder(View itemView) {
        super(itemView);

        // bind views
        ButterKnife.bind(this, itemView);
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getResult() {
        return result;
    }

    public TextView getScore() {
        return score;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
