package com.neptuo.vocabularyapp.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.Sql;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.ui.tasks.LoadFromDbAsyncTask;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button translateButton;
    private Button browseButton;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoadFromDbAsyncTask(this, new DbContext(getApplicationContext())).execute();

        try {
            DbContext dbContext = new DbContext(getApplicationContext());
            SQLiteDatabase db = dbContext.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Sql.Language._NAME, "Česky");
            values.put(Sql.Language._CODE, "cs-CZ");
            long rowId = db.insert(Sql.Language.TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final MainActivity self = this;

        translateButton =  (Button) findViewById(R.id.translateButton);
        browseButton = (Button) findViewById(R.id.browseButton);
        downloadButton = (Button) findViewById(R.id.downloadButton);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, TranslateActivity.class);
                startActivity(intent);
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, BrowseActivity.class);
                startActivity(intent);
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, DownloadActivity.class);
                startActivity(intent);
            }
        });

        checkTranslateActivityAvailability();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTranslateActivityAvailability();
    }

    private void checkTranslateActivityAvailability() {
        boolean hasVocabularyItems = ServiceProvider.getDetails().size() > 0;
        translateButton.setEnabled(hasVocabularyItems);
        browseButton.setEnabled(hasVocabularyItems);
    }

    public void dbLoadCompleted(List<DetailModel> models) {
        ServiceProvider.getDetails().clear();
        ServiceProvider.getDetails().addAll(models);
        checkTranslateActivityAvailability();
    }
}
