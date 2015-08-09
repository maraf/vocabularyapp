package com.neptuo.vocabularyapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.VocabularyItem;
import com.neptuo.vocabularyapp.services.VocabularyService;

public class TranslateActivity extends AppCompatActivity {

    private TextView originalLabel;
    private TextView originalText;
    private TextView translatedLabel;
    private EditText translatedText;
    private TextView descriptionLabel;
    private TextView descriptionText;

    private Button tryButton;
    private Button descriptionButton;
    private Button nextButton;

    private VocabularyService service;
    private VocabularyItem item;
    private boolean isGivenUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        service = ServiceProvider.getVocabulary();

        originalLabel = (TextView) findViewById(R.id.originalLabel);
        originalText = (TextView) findViewById(R.id.originalText);
        translatedLabel = (TextView) findViewById(R.id.translatedLabel);
        translatedText = (EditText) findViewById(R.id.translatedText);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        descriptionText = (TextView) findViewById(R.id.descriptionText);

        tryButton = (Button) findViewById(R.id.tryButton);
        descriptionButton = (Button) findViewById(R.id.descriptionButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        translatedText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.integer.action_try) {
                    tryTranslation();
                    return true;
                }
                return false;
            }
        });

        descriptionLabel.setVisibility(View.INVISIBLE);
        descriptionText.setVisibility(View.INVISIBLE);
        descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDescriptionVisibility(descriptionLabel.getVisibility() != View.VISIBLE);
            }
        });

        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryTranslation();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGivenUp) {
                    prepareNextItem();
                } else {
                    isGivenUp = true;

                    translatedText.setText(item.getTranslatedText());
                    translatedText.setEnabled(false);
                    setDescriptionVisibility(true);

                    tryButton.setEnabled(false);
                    descriptionButton.setEnabled(false);
                    nextButton.setText(getString(R.string.nextButton_next));
                }
            }
        });

        prepareNextItem();
    }

    private void tryTranslation() {
        String typedTranslation = translatedText.getText().toString().toLowerCase();
        if(item.getTranslatedText().toLowerCase().equals(typedTranslation)) {
            prepareNextItem();
            Toast.makeText(this, R.string.translate_success, Toast.LENGTH_SHORT).show();
        } else {
            tryButton.setText(getString(R.string.tryButton_nextTry));
            Toast.makeText(this, R.string.translate_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private void setDescriptionVisibility(boolean isVisible) {
        if (isVisible) {
            descriptionLabel.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.VISIBLE);
            descriptionButton.setText(getString(R.string.descriptionButton_hide));
        } else {
            descriptionLabel.setVisibility(View.INVISIBLE);
            descriptionText.setVisibility(View.INVISIBLE);
            descriptionButton.setText(getString(R.string.descriptionButton_show));
        }
    }

    private void setTryButtonText(String text) {
        tryButton.setText(text);
        translatedText.setImeActionLabel(text, KeyEvent.KEYCODE_ENTER);
    }

    private void prepareNextItem() {
        isGivenUp = false;

        item = service.nextRandom();

        originalText.setText(item.getOriginalText());
        translatedText.setText("");
        translatedText.setEnabled(true);

        descriptionText.setText(item.getTranslatedDescription());
        setDescriptionVisibility(false);

        setTryButtonText(getString(R.string.tryButton_try));
        tryButton.setEnabled(true);
        descriptionButton.setEnabled(true);
        nextButton.setText(getString(R.string.nextButton_giveUp));
    }
}
