package com.neptuo.vocabularyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.ui.adapters.DetailItemModelListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.VocabularyService;

public class BrowseActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new DetailItemModelListAdapter(this, ServiceProvider.getDetails().get(0).getItems()));
    }
}
