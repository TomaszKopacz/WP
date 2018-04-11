package com.example.tomas.quiz.services;

import com.example.tomas.quiz.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tomas on 11.04.2018.
 *
 * Retrofit interface capturing data from api.
 */

public interface WebService {

    @GET("api/v1/quizzes/0/100")
    Call<List<Quiz>> getQuizzes();
}