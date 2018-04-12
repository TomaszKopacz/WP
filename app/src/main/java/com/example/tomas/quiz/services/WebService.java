package com.example.tomas.quiz.services;

import com.example.tomas.quiz.model.QuizDetails;
import com.example.tomas.quiz.model.QuizzesSet;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tomas on 11.04.2018.
 *
 * Retrofit interface capturing data from api.
 */

public interface WebService {

    @GET("api/v1/quizzes/0/100")
    Call<QuizzesSet> getQuizzes();

    @GET("api/v1/quiz/{id}/0")
    Call<QuizDetails> getQuizDetails();
}
