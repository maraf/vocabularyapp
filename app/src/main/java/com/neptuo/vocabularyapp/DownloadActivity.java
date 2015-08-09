package com.neptuo.vocabularyapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.VocabularyService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadActivity extends AppCompatActivity {

    private VocabularyService service;

    private EditText urlText;
    private Button downloadButton;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final DownloadActivity self = this;

        service = ServiceProvider.getVocabulary();

        urlText = (EditText) findViewById(R.id.urlText);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        progress = new ProgressDialog(self);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                DownloadAsyncTask task = new DownloadAsyncTask(self);
                task.execute(urlText.getText().toString());
            }
        });
    }

    public void downloadingCompleted(DownloadAsyncTaskResult result) {
        progress.hide();

        if(result ==null) {
            Toast.makeText(this, "Chyba při pokusu o stažení dat a jejich zpracování.", Toast.LENGTH_SHORT).show();
        } else {
            if(result.isSuccessfull()) {
                Toast.makeText(this, "Staženo celkem položek: " + result.getContent().getItems().size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
