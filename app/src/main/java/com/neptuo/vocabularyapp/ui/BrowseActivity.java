package com.neptuo.vocabularyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.UserStorage;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.BrowseItemListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.AlphabetUserDetailItemModelComparator;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.PercentageUserDetailItemModelComparator;

import java.util.Collections;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

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
        detail = ServiceProvider.getDetails().get(0);
        userItems = UserDetailConverter.map(userStorage, detail.getItems());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BrowseItemListAdapter(this, userItems));

        alphabetOriginalSortButton = (Button) findViewById(R.id.alphabetOriginalSortButton);
        alphabetTranslatedSortButton = (Button) findViewById(R.id.alphabetTranslatedSortButton);
        percentageSortButton = (Button) findViewById(R.id.percentageSortButton);
        defaultSortButton = (Button) findViewById(R.id.defaultSortButton);

        alphabetOriginalSortButton.setText(detail.getDownload().getSourceLanguage());
        alphabetOriginalSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(userItems, new AlphabetUserDetailItemModelComparator(true));
                updateListViewAdapter();
            }
        });

        alphabetTranslatedSortButton.setText(detail.getDownload().getTargetLanguage());
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
    }

    private void updateListViewAdapter() {
        BrowseItemListAdapter adapter = new BrowseItemListAdapter(this, userItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
