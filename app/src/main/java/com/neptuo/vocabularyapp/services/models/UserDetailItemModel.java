package com.neptuo.vocabularyapp.services.models;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class UserDetailItemModel {
    private DetailItemModel model;
    private UserGuessModel guess;

    public UserDetailItemModel(DetailItemModel model, UserGuessModel guess) {
        this.model = model;
        this.guess = guess;
    }

    public DetailItemModel getModel() {
        return model;
    }

    public int getCorrectCount() {
        return guess.getCorrectCount();
    }

    public void incrementCorrectCount() {
        guess.incrementCorrent();
    }

    public int getWrongCount() {
        return guess.getWrongCount();
    }

    public  void incrementWrongCount() {
        guess.incrementWrong();
    }

    public int getTotalCount() {
        return getCorrectCount() + getWrongCount();
    }
}
