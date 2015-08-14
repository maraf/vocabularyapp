package com.neptuo.vocabularyapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ConfigurationStorage;
import com.neptuo.vocabularyapp.services.ServiceProvider;

public class ConfigurationActivity extends AppCompatActivity {

    private ConfigurationStorage storage;

    private EditText dataUrlText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        storage = ServiceProvider.getConfigurationStorage();

        dataUrlText = (EditText) findViewById(R.id.dataUrlText);
        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfiguration();
            }
        });

        loadConfiguration();
    }

    private void loadConfiguration() {
        dataUrlText.setText(storage.getDataUrl());
    }

    private void saveConfiguration() {
        storage.setDataUrl(dataUrlText.getText().toString());
    }
}
