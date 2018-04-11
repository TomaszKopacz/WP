package com.example.tomas.quiz.presenters;

import android.view.View;

/**
 * Created by tomas on 11.04.2018.
 *
 * Interface providing methods for recycler view actions.
 */

public interface RecyclerViewPresenter {

    void onBindView(View view);
    void onClick(View view, int position);
    int getItemCount();

}
