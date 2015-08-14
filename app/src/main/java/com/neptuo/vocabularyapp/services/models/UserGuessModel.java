package com.neptuo.vocabularyapp.services.models;

import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.UserGuessRepository;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserGuessModel {
    private UserGuessRepository repository;
    private String key;
    private int correctCount;
    private int wrongCount;

    public UserGuessModel(UserGuessRepository repository, String key, int correctCount, int wrongCount) {
        this.repository = repository;
        this.key = key;
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void incrementCorrent() {
        correctCount++;
        repository.update(key, this);
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void incrementWrong() {
        wrongCount++;
        repository.update(key, this);
    }
}
