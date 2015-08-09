package com.neptuo.vocabularyapp.ui.tasks;

import com.neptuo.vocabularyapp.services.models.DefinitionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DownloadDefinitionListAsyncTaskResult {
    private boolean isSuccessfull;
    private List<DefinitionModel> content;
    private String errorMessage;

    public DownloadDefinitionListAsyncTaskResult(boolean isSuccessfull, List<DefinitionModel> content, String errorMessage) {
        this.isSuccessfull = isSuccessfull;
        this.content = content;
        this.errorMessage = errorMessage;
    }

    public List<DefinitionModel> getContent() {
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
