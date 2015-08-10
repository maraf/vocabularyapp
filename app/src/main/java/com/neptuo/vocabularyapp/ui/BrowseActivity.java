package com.neptuo.vocabularyapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.BrowseItemListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;

import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private ListView listView;
    private List<UserDetailItemModel> userItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        DetailModel detail = ServiceProvider.getDetails().get(0);
        userItems = UserDetailConverter.map(ServiceProvider.getUserStorage(), detail.getItems());

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BrowseItemListAdapter(this, userItems));
    }
}
