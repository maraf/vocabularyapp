package com.neptuo.vocabularyapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.neptuo.vocabularyapp.ui.fragments.BrowseDialogFragment;
import com.neptuo.vocabularyapp.ui.fragments.SelectTagDialogFragment;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailItemViewModel;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> lastSelectedTags;
    private boolean lastIsNoTagIncluded;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_translate, menu);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SelectTagDialogFragment fragment = new SelectTagDialogFragment();
                fragment.setLastSelectedTags(lastSelectedTags, lastIsNoTagIncluded);
                fragment.setOkListener(new SelectTagDialogFragment.OnClickOkListener() {
                    @Override
                    public void onClick(List<String> selectedTags, boolean isNoTagIncluded) {
                        lastSelectedTags.clear();
                        lastSelectedTags.addAll(selectedTags);
                        lastIsNoTagIncluded = isNoTagIncluded;

                        session.filterTags(selectedTags, lastIsNoTagIncluded);
                        prepareNextItem();
                    }
                });

                fragment.show(transaction, "dialog");

                return true;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lastSelectedTags = new ArrayList<String>();
        lastSelectedTags.addAll(ServiceProvider.getTags());
        lastIsNoTagIncluded = true;

        DetailModel detail = prepareDetailModel();
        session = new Session(detail, ServiceProvider.getUserStorage());

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
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
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
                    incrementGuess(false);
                    updateSuccessBar();

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    BrowseDialogFragment fragment = new BrowseDialogFragment();
                    fragment.setItemModel(itemViewModel.getModel());
                    fragment.setPositiveButton(getString(R.string.nextButton_next), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prepareNextItem();
                            dialog.dismiss();
                        }
                    });
                    fragment.setNegativeButton("Zkusit zadat", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    fragment.show(transaction, "dialog");

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

    private void incrementGuess(boolean isSuccess) {
        if(itemViewModel != null) {
            if(isSuccess) {
                itemViewModel.getUserModel().incrementCorrectCount();
            } else {
                itemViewModel.getUserModel().incrementWrongCount();
            }
        }
    }

    private void prepareNextItem(boolean isSuccess) {
        if(itemViewModel != null) {
            incrementGuess(isSuccess);
            session.updateGroup(itemViewModel.getUserModel());
        }

        prepareNextItem();
    }

    private void prepareNextItem() {
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
