package com.neptuo.vocabularyapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;

public class BrowseItemActivity extends AppCompatActivity {

    public static final String PARAMETER_SOURCE_TEXT = "sourceText";
    public static final String PARAMETER_SOURCE_DESCRIPTION = "sourceDescription";
    public static final String PARAMETER_TARGET_TEXT = "targetText";
    public static final String PARAMETER_TARGET_DESCRIPTION = "targetDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_item);

        TextView sourceText = (TextView) findViewById(R.id.originalText);
        TextView sourceDescription = (TextView) findViewById(R.id.originalDescription);
        TextView targetText = (TextView) findViewById(R.id.translatedText);
        TextView targetDescription = (TextView) findViewById(R.id.translatedDescription);

        Bundle extras = getIntent().getExtras();
        sourceText.setText(extras.getString(PARAMETER_SOURCE_TEXT));
        sourceDescription.setText(extras.getString(PARAMETER_SOURCE_DESCRIPTION));
        targetText.setText(extras.getString(PARAMETER_TARGET_TEXT));
        targetDescription.setText(extras.getString(PARAMETER_TARGET_DESCRIPTION));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
