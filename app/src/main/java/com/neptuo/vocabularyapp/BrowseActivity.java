package com.neptuo.vocabularyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.VocabularyItem;
import com.neptuo.vocabularyapp.services.VocabularyService;

public class BrowseActivity extends AppCompatActivity {

    private ListView listView;
    private VocabularyService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        service = ServiceProvider.getVocabulary();

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new VocabularyListAdapter(this, service.getItems()));
    }
}
