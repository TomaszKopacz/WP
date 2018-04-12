package com.example.tomas.quiz.presenters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tomas on 11.04.2018.
 *
 * Interface providing methods for recycler view actions.
 */

public interface RecyclerViewPresenter {

    void onBindView(RecyclerView.ViewHolder holder, int position);
    void onClick(View view);
    int getItemCount();

}
