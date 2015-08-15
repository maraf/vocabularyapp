package com.neptuo.vocabularyapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.UserStorage;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.BrowseItemListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.AlphabetUserDetailItemModelComparator;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.PercentageUserDetailItemModelComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrowseActivity extends DetailActivityBase {

    private EditText searchText;
    private ImageButton searchButton;
    private ListView listView;
    private Button alphabetOriginalSortButton;
    private Button alphabetTranslatedSortButton;
    private Button percentageSortButton;
    private Button defaultSortButton;

    private DetailModel detail;
    private UserStorage userStorage;
    private List<UserDetailItemModel> userItems;
    private List<UserDetailItemModel> searchedItems;

    private String lastSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        userStorage = ServiceProvider.getUserStorage();
        detail = prepareDetailModel();
        userItems = UserDetailConverter.map(userStorage, detail.getItems());
        searchedItems = new ArrayList<UserDetailItemModel>(userItems);

        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (ImageButton) findViewById(R.id.searchButton);

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
                Collections.sort(userItems, new AlphabetUserDetailItemModelComparator(detail, true));
                updateSearchedItems();
                updateListViewAdapter();
            }
        });

        alphabetTranslatedSortButton.setText(getEllapsedText(detail.getDownload().getTargetLanguage().getName()));
        alphabetTranslatedSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new AlphabetUserDetailItemModelComparator(detail, false));
                updateSearchedItems();
                updateListViewAdapter();
            }
        });

        percentageSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new PercentageUserDetailItemModelComparator(true));
                updateSearchedItems();
                updateListViewAdapter();
            }
        });

        defaultSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userItems = UserDetailConverter.map(userStorage, detail.getItems());
                updateSearchedItems();
                updateListViewAdapter();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchText.getText().toString();
                if (search != lastSearch) {
                    lastSearch = search;
                    updateSearchedItems();
                    updateListViewAdapter();
                    updateSuccessBar();
                }
            }
        });

        updateSuccessBar();
    }

    private void updateSearchedItems() {
        searchedItems.clear();

        lastSearch = lastSearch == null ? null : lastSearch.toLowerCase();
        for (UserDetailItemModel itemModel : userItems) {
            if(lastSearch == null || itemModel.getModel().getOriginalText().toLowerCase().contains(lastSearch))
                searchedItems.add(itemModel);
        }
    }

    private String getEllapsedText(String text) {
        if(text.length() > 5)
            return text.substring(0, 5) + ".";

        return text;
    }

    private void updateListViewAdapter() {
        BrowseItemListAdapter adapter = new BrowseItemListAdapter(this, searchedItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateSuccessBar() {
        TextView percentageText = (TextView) findViewById(R.id.percentageText);
        FrameLayout placeholder = (FrameLayout) findViewById(R.id.backgroundLayout);

        int totalCount = 0;
        int correctCount = 0;
        int wrongCount = 0;
        for (UserDetailItemModel userItem : searchedItems) {
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
