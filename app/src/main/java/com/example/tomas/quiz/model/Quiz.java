package com.example.tomas.quiz.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tomas on 11.04.2018.
 *
 * Quiz.
 */

public class Quiz extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private Photo mainPhoto;

    private int questions;

    private Date createdAt;

    private String content;

    private Category category;

    private int progress = 0;

    private int score = 0;

    /**
     * Constructor.
     */
    public Quiz(){

    }

    /**
     * Copying constructor.
     * @param quiz
     */
    public Quiz(Quiz quiz){
        id = quiz.id;
        title = quiz.title;
        mainPhoto = quiz.mainPhoto;
        content = quiz.content;
        category = quiz.category;
        questions = quiz.questions;
        createdAt = quiz.createdAt;
        progress = quiz.progress;
        score = quiz.score;
    }

    public String getTitle() {
        return title;
    }

    public int getQuestionsCount() {
        return questions;
    }

    public String getContent() {
        return content;
    }

    public Category getCategory() {
        return category;
    }

    public Photo getPhoto() {
        return mainPhoto;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
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
