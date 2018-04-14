package com.example.tomas.quiz.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tomas.quiz.R;
import com.example.tomas.quiz.app.MainApp;
import com.example.tomas.quiz.presenters.MainActivityPresenter;
import com.example.tomas.quiz.services.QuizAdapter;
import com.example.tomas.quiz.services.WebService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    @Inject
    WebService service;

    @BindView(R.id.recview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind views
        ButterKnife.bind(this);

        // get dependencies
        ((MainApp)getApplication()).getWebServiceComponent().inject(this);

        // set presenter
        presenter = new MainActivityPresenter(this, service);

        // set up recycler view
        setUpRecyclerView();

        // get quizzes
        presenter.downloadQuizzes();
    }

    /**
     * Customize recycler view.
     * Recycler has linear layout, default item animator and fixed size.
     */
    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Sets adapter for recycler view.
     * @param adapter
     */
    public void setAdapter(QuizAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

    /**
     * Returns recycler view.
     * @return recycler view
     */
    public RecyclerView getRecyclerView(){
        return recyclerView;
    }
}
