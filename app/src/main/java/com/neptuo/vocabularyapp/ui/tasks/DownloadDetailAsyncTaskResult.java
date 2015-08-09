package com.neptuo.vocabularyapp.ui.tasks;

import com.neptuo.vocabularyapp.services.models.DetailModel;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadDetailAsyncTaskResult {
    private boolean isSuccessfull;
    private DetailModel content;
    private String errorMessage;

    public DownloadDetailAsyncTaskResult(boolean isSuccessfull, DetailModel content, String errorMessage) {
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

    public DetailModel getContent() {
        return content;
    }

    public void setContent(DetailModel content) {
        this.content = content;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
