package com.example.tomas.quiz.services;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.presenters.RecyclerViewPresenter;
import com.example.tomas.quiz.views.QuizViewHolder;

/**
 * Created by tomas on 11.04.2018.
 *
 * Adapter for quizzes in recycler view.
 */
public class QuizAdapter extends RecyclerView.Adapter {

    private RecyclerViewPresenter presenter;

    /**
     * Constructor.
     * @param presenter
     */
    public QuizAdapter(RecyclerViewPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        // inflate item layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_item, parent, false);

        // set on click listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClick(view);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                presenter.onLongClick(view);
                return true;
            }
        });

        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        presenter.onBindView(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }
}
