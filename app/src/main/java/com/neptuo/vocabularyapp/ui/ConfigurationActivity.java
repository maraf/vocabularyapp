package com.neptuo.vocabularyapp.ui;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.DetailItemRepository;
import com.neptuo.vocabularyapp.data.DownloadRepository;
import com.neptuo.vocabularyapp.data.UserGuessRepository;
import com.neptuo.vocabularyapp.services.ConfigurationStorage;
import com.neptuo.vocabularyapp.services.ServiceProvider;

public class ConfigurationActivity extends ActivityBase {

    private ConfigurationStorage storage;

    private EditText dataUrlText;
    private CheckBox dropUserGuessCheckBox;
    private CheckBox dropDetailsCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        storage = ServiceProvider.getConfigurationStorage();

        dataUrlText = (EditText) findViewById(R.id.dataUrlText);
        dropUserGuessCheckBox = (CheckBox) findViewById(R.id.dropUserGuessCheckBox);
        dropDetailsCheckBox = (CheckBox) findViewById(R.id.dropDetailsCheckBox);
        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.configuration_saved, Toast.LENGTH_SHORT).show();
                saveConfiguration();
                onBackPressed();
            }
        });

        loadConfiguration();
    }

    private void loadConfiguration() {
        dataUrlText.setText(storage.getDataUrl());
    }

    private void saveConfiguration() {
        storage.setDataUrl(dataUrlText.getText().toString());

        if(dropDetailsCheckBox.isChecked()) {
            ServiceProvider.getDetails().clear();

            SQLiteDatabase db = new DbContext(getApplicationContext()).getWritableDatabase();
            DetailItemRepository detailItems = new DetailItemRepository(db);
            DownloadRepository downloads = new DownloadRepository(db);
            downloads.truncate();
            detailItems.truncate();
        }

        if(dropUserGuessCheckBox.isChecked()) {
            ServiceProvider.getUserStorage().truncate();
        }
    }
}
