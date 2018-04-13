package com.example.tomas.quiz.services;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.example.tomas.quiz.model.Quiz;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by tomas on 12.04.2018.
 *
 * Class capturing data from realm database.
 */
public class RealmService {

    private static RealmService instance;
    private final Realm realm;

    /**
     * Constructor.
     * @param application
     */
    private RealmService(Application application){
        realm = Realm.getDefaultInstance();
    }

    /**
     * Returns instance that will be tied to a cpecific context.
     * @param fragment
     * @return instance
     */
    public static RealmService with(Fragment fragment){
        if (instance == null)
            instance = new RealmService(fragment.getActivity().getApplication());

        return instance;
    }

    /**
     * Returns instance that will be tied to a cpecific context.
     * @param activity
     * @return instance
     */
    public static RealmService with(Activity activity){
        if (instance == null)
            instance = new RealmService(activity.getApplication());

        return instance;
    }

    /**
     * Returns instance that will be tied to a cpecific context.
     * @param application
     * @return instance
     */
    public static RealmService with(Application application){
        if (instance == null)
            instance = new RealmService(application);

        return instance;
    }

    /**
     * Returns RealmService instance.
     * @return instance
     */
    public static RealmService getInstance(){
        return instance;
    }

    public void clearAll(){
        realm.beginTransaction();
        realm.delete(Quiz.class);
        realm.commitTransaction();
    }

    /*===================================================
                        INSERT
     ===================================================*/

    /**
     * Insert quiz.
     * @param list
     */
    public void insertQuizzes(List<Quiz> list){
        realm.beginTransaction();
        realm.copyToRealm(list);
        realm.commitTransaction();
    }

    /*===================================================
                        GET
     ===================================================*/

    /**
     * Get quiz by its position.
     * @param position
     * @return
     */
    public Quiz getQuiz(int position){
        List<Quiz> list = realm.where(Quiz.class).findAll();
        return list.get(position);
    }

    /**
     * Get quiz by its position.
     * @param id
     * @return
     */
    public Quiz getQuiz(String id){
        Quiz quiz = realm.where(Quiz.class).equalTo("id", id).findFirst();
        return quiz;
    }

    /*===================================================
                        UPDATE
     ===================================================*/

    /**
     * Updates the quiz.
     * @param quiz
     */
    public void updateQuiz(Quiz quiz){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(quiz);
        realm.commitTransaction();
    }


    /*===================================================
                        UTILS
     ===================================================*/

    /**
     * Checks if quizzes table is empty.
     * @return
     */
    public boolean isEmpty(){
        return realm.where(Quiz.class).findAll().size() == 0;
    }

    public int getQuizCount(){
        return realm.where(Quiz.class).findAll().size();
    }

}
