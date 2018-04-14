package com.example.tomas.quiz.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tomas on 11.04.2018.
 *
 * Class representing quiz.
 */

public class Quiz extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;

    private Photo mainPhoto;

    private int questions;

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
        progress = quiz.progress;
        score = quiz.score;
    }

    public void incrementProgress(){
        progress++;
    }

    public void incrementScore(){
        score++;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
