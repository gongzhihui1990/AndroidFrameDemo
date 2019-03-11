package com.starkrak.framedemo.playgame;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class GameSrc {

//    public GameSrc(@NonNull Drawable questionDrawable, @NonNull Answer[] answer) {
//        this.questionDrawable = questionDrawable;
//        this.answer = answer;
//    }

    private String gameHint;
    public Drawable questionDrawable;

    public Answer[] answer;

    public void setGameHint(String gameHint) {
        this.gameHint = gameHint;
    }

    public String getHint() {
        return gameHint;
    }


    public static class Answer {
        public Drawable answerDrawable;
        public GameColor answerColor;

        public Answer(@NonNull Drawable answerDrawable, @NonNull GameColor answerColor) {
            this.answerDrawable = answerDrawable;
            this.answerColor = answerColor;
        }
    }
}