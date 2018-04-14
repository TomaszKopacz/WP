package com.example.tomas.quiz.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    ImageView imageView;

    @BindView(R.id.quiz_title)
    TextView titleView;

    @BindView(R.id.quiz_result)
    TextView resultView;

    @BindView(R.id.quiz_score)
    TextView scoreView;

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

    /**
     * Sets image.
     * @param context
     * @param url
     */
    public void setImage(Context context, String url) {
        Glide
                .with(context)
                .load(url)
                .fitCenter()
                .centerCrop()
                .into(imageView);
    }

    /**
     * Sets title.
     * @param title
     */
    public void setTitle(String title) {
         titleView.setText(title);
    }

    /**
     * Sets result.
     * @param result
     */
    public void setResult(String result) {
        resultView.setText(result);
    }

    /**
     * Sets score.
     * @param score
     */
    public void setScore(String score) {
        scoreView.setText(score);
    }

    /**
     * Sets progress.
     * @param progress
     */
    public void setProgressBar(int progress) {
        progressBar.setProgress(progress);
    }

    /**
     * Sets score color.
     * @param color
     */
    public void setScoreColor(int color){
        scoreView.setTextColor(color);
    }
}
