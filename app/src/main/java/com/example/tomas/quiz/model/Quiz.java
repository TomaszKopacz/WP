package com.example.tomas.quiz.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tomas on 11.04.2018.
 *
 * Class representing quiz.
 */

public class Quiz extends RealmObject {

    private String id;

    private String title;

    private Photo mainPhoto;

    private int questions;

    private int progress;

    private int score;

    /**
     * Constructor.
     */
    public Quiz(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuestionsCount() {
        return questions;
    }

    public void setQuestionsCount(int questions) {
        this.questions = questions;
    }

    public Photo getPhoto() {
        return mainPhoto;
    }

    public void setPhoto(Photo photo) {
        this.mainPhoto = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
