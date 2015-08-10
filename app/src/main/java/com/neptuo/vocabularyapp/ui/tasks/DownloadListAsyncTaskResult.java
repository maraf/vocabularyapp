package com.neptuo.vocabularyapp.ui.tasks;

import com.neptuo.vocabularyapp.services.models.DownloadModel;

import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DownloadListAsyncTaskResult {
    private boolean isSuccessfull;
    private List<DownloadModel> content;
    private String errorMessage;

    public DownloadListAsyncTaskResult(boolean isSuccessfull, List<DownloadModel> content, String errorMessage) {
        this.isSuccessfull = isSuccessfull;
        this.content = content;
        this.errorMessage = errorMessage;
    }

    public List<DownloadModel> getContent() {
        return content;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
