package com.neptuo.vocabularyapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.UserStorage;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.BrowseItemListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.AlphabetUserDetailItemModelComparator;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.PercentageUserDetailItemModelComparator;

import java.util.Collections;
import java.util.List;

public class BrowseActivity extends DetailActivityBase {

    private ListView listView;
    private Button alphabetOriginalSortButton;
    private Button alphabetTranslatedSortButton;
    private Button percentageSortButton;
    private Button defaultSortButton;

    private DetailModel detail;
    private UserStorage userStorage;
    private List<UserDetailItemModel> userItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        userStorage = ServiceProvider.getUserStorage();
        detail = prepareDetailModel();
        userItems = UserDetailConverter.map(userStorage, detail.getItems());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BrowseItemListAdapter(this, userItems));

        alphabetOriginalSortButton = (Button) findViewById(R.id.alphabetOriginalSortButton);
        alphabetTranslatedSortButton = (Button) findViewById(R.id.alphabetTranslatedSortButton);
        percentageSortButton = (Button) findViewById(R.id.percentageSortButton);
        defaultSortButton = (Button) findViewById(R.id.defaultSortButton);

        alphabetOriginalSortButton.setText(getEllapsedText(detail.getDownload().getSourceLanguage().getName()));
        alphabetOriginalSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new AlphabetUserDetailItemModelComparator(true));
                updateListViewAdapter();
            }
        });

        alphabetTranslatedSortButton.setText(getEllapsedText(detail.getDownload().getTargetLanguage().getName()));
        alphabetTranslatedSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new AlphabetUserDetailItemModelComparator(false));
                updateListViewAdapter();
            }
        });

        percentageSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new PercentageUserDetailItemModelComparator(true));
                updateListViewAdapter();
            }
        });

        defaultSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userItems = UserDetailConverter.map(userStorage, detail.getItems());
                updateListViewAdapter();
            }
        });

        updateSuccessBar();
    }

    private String getEllapsedText(String text) {
        if(text.length() > 5)
            return text.substring(0, 5) + ".";

        return text;
    }

    private void updateListViewAdapter() {
        BrowseItemListAdapter adapter = new BrowseItemListAdapter(this, userItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateSuccessBar() {
        TextView percentageText = (TextView) findViewById(R.id.percentageText);
        FrameLayout placeholder = (FrameLayout) findViewById(R.id.backgroundLayout);

        int totalCount = 0;
        int correctCount = 0;
        int wrongCount = 0;
        for (UserDetailItemModel userItem : userItems) {
            totalCount += userItem.getTotalCount();
            correctCount += userItem.getCorrectCount();
            wrongCount += userItem.getWrongCount();
        }

        if(totalCount == 0) {
            percentageText.setText(R.string.not_yet_tested);
            placeholder.setBackgroundColor(Color.alpha(0));
        } else {
            double ratio = ((double)correctCount) / totalCount;
            int percentage = (int) (ratio * 100);

            percentageText.setText(percentage + "%");
            placeholder.setBackgroundColor(PercentageConverter.mapToColor(percentage));
        }
    }
}
