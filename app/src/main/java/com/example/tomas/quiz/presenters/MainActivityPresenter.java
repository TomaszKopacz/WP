package com.example.tomas.quiz.presenters;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.tomas.quiz.model.Quiz;
import com.example.tomas.quiz.model.QuizzesSet;
import com.example.tomas.quiz.services.NetworkConnection;
import com.example.tomas.quiz.services.QuizAdapter;
import com.example.tomas.quiz.views.QuizViewHolder;
import com.example.tomas.quiz.services.RealmService;
import com.example.tomas.quiz.services.WebService;
import com.example.tomas.quiz.views.MainActivity;
import com.example.tomas.quiz.views.QuizActivity;
import com.example.tomas.quiz.views.QuizInfoDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomas on 11.04.2018.
 *
 * Presenter for MainActivity.
 */

public class MainActivityPresenter implements RecyclerViewPresenter, QuizInfoDialogPresenter {

    private MainActivity activity;
    private WebService service;

    // quizzes adapter
    private List<Quiz> quizzes = new ArrayList<>();
    private QuizAdapter adapter;

    /**
     * Constructor.
     * @param activity
     * @param service
     */
    public MainActivityPresenter(MainActivity activity, WebService service){
        this.activity = activity;
        this.service = service;

        this.adapter = new QuizAdapter(this);
    }

    /**
     * Downloads list of quizzes.
     */
    public void setQuizzes() {

        quizzes = RealmService.with(activity).getQuizzes();
        downloadNewQuizzes();
        activity.setAdapter(adapter);
    }

    /**
     * Downloads new quizzes.
     */
    private void downloadNewQuizzes(){

        // internet is on -> download quizzes
        if (NetworkConnection.isConnected(activity)){

            Call call = service.getQuizzes();
            call.enqueue(new Callback<QuizzesSet>() {

                @Override
                public void onResponse(Call<QuizzesSet> call, Response<QuizzesSet> response) {

                    // assign response objects to realm
                    List<Quiz> list = response.body().getItems();

                    // put new quizzes to database
                    for (Quiz quiz : list)
                        if (!RealmService.with(activity).containsQuiz(quiz.getId()))
                            RealmService.with(activity).insertQuiz(quiz);

                    // set list
                    quizzes = RealmService.with(activity).getQuizzes();
                    activity.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<QuizzesSet> call, Throwable t) {
                    Toast.makeText(activity, "Nie można pobrać nowych quizów",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        else
            Toast.makeText(activity, "BRAK DOSTĘPU DO INTERNETU", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {

        // empty quizzes list tells that there are no quizzes available
        if (quizzes.isEmpty()){
            Toast.makeText(activity, "Brak dostępnych quizów", Toast.LENGTH_LONG).show();
            return;
        }

        // get holder and quiz
        QuizViewHolder quizHolder = (QuizViewHolder)holder;
        Quiz quiz = quizzes.get(position);

        // set title and image
        quizHolder.setTitle(quiz.getTitle());
        quizHolder.setImage(activity, quiz.getPhoto().getUrl());

        // get results
        int questionsCount = quiz.getQuestionsCount();
        int numOfAns = quiz.getProgress();
        int score = quiz.getScore();

        // if user haven't completed quiz
        if (numOfAns < questionsCount){

            // count progress in %
            int progress = (int)(((float)numOfAns/questionsCount)*100);

            // set views
            quizHolder.setResult("Postęp: ");
            quizHolder.setProgressBar(progress);
            quizHolder.setScore(numOfAns + "/" + questionsCount);
            quizHolder.setScoreColor(Color.BLACK);
        }

        // if quiz is complete
        else {

            // count result in %
            int result = (int)(((float)score/questionsCount)*100);

            // set views
            quizHolder.setResult("Wynik: ");
            quizHolder.setProgressBar(result);
            quizHolder.setScore(result + "%");

            if (result < 30)
                quizHolder.setScoreColor(Color.RED);
            else if (result > 70)
                quizHolder.setScoreColor(Color.GREEN);
            else
                quizHolder.setScoreColor(Color.YELLOW);
        }
    }

    @Override
    public void onClick(View view) {

        // get clicked quiz
        int position = activity.getRecyclerView().getChildAdapterPosition(view);
        Quiz quiz = quizzes.get(position);
        String id = quiz.getId();

        // start quiz activity
        startQuizActivity(id);
    }

    @Override
    public void onLongClick(View view) {

        // get quiz
        int position = activity.getRecyclerView().getChildAdapterPosition(view);
        Quiz quiz = RealmService.with(activity).getQuiz(position);

        // show dialog
        QuizInfoDialog dialog = getInfoDialog(quiz);
        dialog.show();
    }

    /**
     * Returns dialog.
     * @param quiz
     * @return
     */
    private QuizInfoDialog getInfoDialog(Quiz quiz){
        QuizInfoDialog dialog = new QuizInfoDialog(activity);
        dialog.setPresenter(this);
        dialog.setQuiz(quiz);

        return dialog;
    }


    @Override
    public int getItemCount() {
        return RealmService.with(activity).getQuizCount();
    }

    @Override
    public void startQuiz(Quiz quiz) {
        startQuizActivity(quiz.getId());
    }

    /**
     * Starts quiz activity.
     */
    private void startQuizActivity(String quizId){

        // check internet connection
        if (!NetworkConnection.isConnected(activity)){
            Toast.makeText(activity, "BRAK DOSTĘPU DO INTERNETU", Toast.LENGTH_LONG).show();
            return;
        }

        // start new intent
        Intent intent = new Intent(activity, QuizActivity.class);
        intent.putExtra("id", quizId);
        activity.startActivity(intent);
        activity.finish();
    }
}
