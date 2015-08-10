package com.neptuo.vocabularyapp.services.models;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserGuessModel {
    private int correctCount;
    private int wrongCount;

    public UserGuessModel(int correctCount, int wrongCount) {
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void incrementCorrent() {
        correctCount++;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void incrementWrong() {
        wrongCount++;
    }
}
