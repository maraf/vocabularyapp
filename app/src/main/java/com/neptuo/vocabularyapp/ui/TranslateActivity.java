package com.neptuo.vocabularyapp.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.Session;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailItemViewModel;

public class TranslateActivity extends DetailActivityBase {

    private TextView originalLabel;
    private TextView originalText;
    private TextView translatedLabel;
    private EditText translatedText;
    private TextView descriptionLabel;
    private TextView descriptionText;

    private Button tryButton;
    private Button descriptionButton;
    private Button nextButton;

    private Session session;
    private UserDetailItemViewModel itemViewModel;
    private boolean isGivenUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DetailModel detail = prepareDetailModel();
        session = new Session(detail, ServiceProvider.getUserStorage());

        setTitle(getTitle() + ": " + detail.getDownload().getSourceLanguage().getName() + " -> " + detail.getDownload().getTargetLanguage().getName());

        originalLabel = (TextView) findViewById(R.id.originalLabel);
        originalText = (TextView) findViewById(R.id.originalText);
        translatedLabel = (TextView) findViewById(R.id.translatedLabel);
        translatedText = (EditText) findViewById(R.id.translatedText);
        descriptionLabel = (TextView) findViewById(R.id.descriptionLabel);
        descriptionText = (TextView) findViewById(R.id.descriptionText);

        tryButton = (Button) findViewById(R.id.tryButton);
        descriptionButton = (Button) findViewById(R.id.descriptionButton);
        nextButton = (Button) findViewById(R.id.nextButton);

        originalLabel.setText(detail.getDownload().getSourceLanguage().getName());
        translatedLabel.setText(detail.getDownload().getTargetLanguage().getName());

        translatedText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT) {
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
                    prepareNextItem(false);
                } else {
                    isGivenUp = true;

                    translatedText.setText(itemViewModel.getModel().getTranslatedText());
                    translatedText.setEnabled(false);
                    setDescriptionVisibility(true);

                    tryButton.setEnabled(false);
                    descriptionButton.setEnabled(false);
                    nextButton.setText(getString(R.string.nextButton_next));
                }
            }
        });

        prepareNextItem(true);
    }

    private void tryTranslation() {
        String typedTranslation = translatedText.getText().toString().toLowerCase();
        if(itemViewModel.tryTranslation(typedTranslation)) {
            prepareNextItem(true);
            Toast.makeText(this, R.string.translate_success, Toast.LENGTH_SHORT).show();
        } else {
            itemViewModel.getUserModel().incrementWrongCount();
            tryButton.setText(getString(R.string.tryButton_nextTry));
            updateSuccessBar();

            Toast.makeText(this, R.string.translate_fail, Toast.LENGTH_SHORT).show();
        }
    }

    private void setDescriptionVisibility(boolean isVisible) {
        if (isVisible) {
            descriptionLabel.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.VISIBLE);
            descriptionButton.setText(getString(R.string.descriptionButton_hide));
        } else {
            descriptionLabel.setVisibility(View.GONE);
            descriptionText.setVisibility(View.GONE);
            descriptionButton.setText(getString(R.string.descriptionButton_show));
        }
    }

    private void setTryButtonText(String text) {
        tryButton.setText(text);
        translatedText.setImeActionLabel(text, KeyEvent.KEYCODE_ENTER);
    }

    private void prepareNextItem(boolean isSuccess) {
        if(itemViewModel != null) {
            if(isSuccess) {
                itemViewModel.getUserModel().incrementCorrectCount();
            } else {
                itemViewModel.getUserModel().incrementWrongCount();
            }
            session.update(itemViewModel.getUserModel());
        }

        isGivenUp = false;
        itemViewModel = new UserDetailItemViewModel(session.nextRandom());
        updateSuccessBar();

        originalText.setText(itemViewModel.getModel().getOriginalText());
        translatedText.setText("");
        translatedText.setEnabled(true);

        descriptionText.setText(itemViewModel.getModel().getTranslatedDescription());
        setDescriptionVisibility(false);

        setTryButtonText(getString(R.string.tryButton_try));
        tryButton.setEnabled(true);
        descriptionButton.setEnabled(true);
        nextButton.setText(getString(R.string.nextButton_giveUp));
    }

    private void updateSuccessBar() {
        TextView percentageText = (TextView) findViewById(R.id.percentageText);
        FrameLayout placeholder = (FrameLayout) findViewById(R.id.backgroundLayout);

        if(itemViewModel.getUserModel().getTotalCount() == 0) {
            percentageText.setText(R.string.not_yet_tested);
            placeholder.setBackgroundColor(Color.alpha(0));
        } else {
            double ratio = itemViewModel.getCorrentGuessRatio();
            int percentage = (int) (ratio * 100);

            percentageText.setText(percentage + "%");
            placeholder.setBackgroundColor(PercentageConverter.mapToColor(percentage));
        }
    }
}