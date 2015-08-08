package com.neptuo.vocabularyapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.neptuo.vocabularyapp.services.VocabularyItem;
import com.neptuo.vocabularyapp.services.VocabularyService;

public class MainActivity extends AppCompatActivity {

    private Button translateButton;
    private Button browseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MainActivity self = this;

        translateButton =  (Button) findViewById(R.id.translateButton);
        browseButton = (Button) findViewById(R.id.browseButton);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent translateIntent = new Intent(self, TranslateActivity.class);
                startActivity(translateIntent);
            }
        });
    }
}
