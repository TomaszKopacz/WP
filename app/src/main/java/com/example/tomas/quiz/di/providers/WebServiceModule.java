package com.example.tomas.quiz.di.providers;

import com.example.tomas.quiz.services.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tomas on 11.04.2018.
 *
 * Provides singleton WebService instance based on retrofit.
 */

@Module
public class WebServiceModule {

    private static final String BASE_URL = "http://quiz.o2.pl/";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    WebService provideWebService(Retrofit retrofit){
        return retrofit.create(WebService.class);
    }
}
