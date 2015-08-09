package com.neptuo.vocabularyapp;

import com.neptuo.vocabularyapp.services.Vocabulary;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadAsyncTaskResult {
    private boolean isSuccessfull;
    private Vocabulary content;
    private String errorMessage;

    public DownloadAsyncTaskResult(boolean isSuccessfull, Vocabulary content, String errorMessage) {
        this.isSuccessfull = isSuccessfull;
        this.content = content;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public Vocabulary getContent() {
        return content;
    }

    public void setContent(Vocabulary content) {
        this.content = content;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
