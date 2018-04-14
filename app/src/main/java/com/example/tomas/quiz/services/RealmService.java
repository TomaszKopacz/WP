package com.example.tomas.quiz.services;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.example.tomas.quiz.model.Quiz;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

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
     * Returns instance that will be tied to a specific context.
     * @param fragment
     * @return instance
     */
    public static RealmService with(Fragment fragment){
        if (instance == null)
            instance = new RealmService(fragment.getActivity().getApplication());

        return instance;
    }

    /**
     * Returns instance that will be tied to a specific context.
     * @param activity
     * @return instance
     */
    public static RealmService with(Activity activity){
        if (instance == null)
            instance = new RealmService(activity.getApplication());

        return instance;
    }

    /**
     * Returns instance that will be tied to a specific context.
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

    /*===================================================
                        INSERT
     ===================================================*/

    /**
     * Insert quiz.
     * @param quiz
     */
    public void insertQuiz(Quiz quiz){
        realm.beginTransaction();
        realm.copyToRealm(quiz);
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

    /**
     * Returns all quizzes sorted by date.
     * @return list of quizzes
     */
    public List<Quiz> getQuizzes(){
        return realm.where(Quiz.class).sort("createdAt", Sort.DESCENDING).findAll();
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

    public void clearAllQuizzes(){
        realm.beginTransaction();
        realm.delete(Quiz.class);
        realm.commitTransaction();
    }

    /**
     * Checks if quizzes table is empty.
     * @return
     */
    public boolean isQuizzesTableEmpty(){
        return realm.where(Quiz.class).findAll().size() == 0;
    }

    /**
     * Checks if quizzes table containsQuiz quiz with a given id.
     * @return
     */
    public boolean containsQuiz(String id){

        Quiz quiz = realm.where(Quiz.class).contains("id", id).findFirst();

        return (quiz!=null);
    }

    /**
     * Returns size of quizzes table.
     * @return
     */
    public int getQuizCount(){
        return realm.where(Quiz.class).findAll().size();
    }

}
