package com.neptuo.vocabularyapp.ui;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.UserStorage;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.UserDetailItemModel;
import com.neptuo.vocabularyapp.ui.adapters.BrowseListAdapter;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.ui.fragments.SelectTagDialogFragment;
import com.neptuo.vocabularyapp.ui.viewmodels.BrowseViewModel;
import com.neptuo.vocabularyapp.ui.viewmodels.PercentageConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.UserDetailConverter;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.AlphabetUserDetailItemModelComparator;
import com.neptuo.vocabularyapp.ui.viewmodels.comparators.PercentageUserDetailItemModelComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrowseActivity extends DetailActivityBase {

    private EditText searchText;
    private ListView listView;

    private DetailModel detail;
    private UserStorage userStorage;
    private List<BrowseViewModel> allItems;

    private String lastSearch;
    private List<String> lastSelectedTags;
    private boolean lastIsNoTagIncluded;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_browse, menu);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (searchText.getVisibility() == View.GONE) {
                    searchText.setVisibility(View.VISIBLE);
                    searchText.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
                } else {
                    searchText.setVisibility(View.GONE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    searchText.clearFocus();
                }
                return true;
            }
        });


        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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

                        // Update visible items.
                        updateSearchedItems();
                        updateSuccessBar();
                        updateListViewAdapter();
                    }
                });

                fragment.show(transaction, "dialog");

                return true;
            }
        });


        SubMenu subMenu = menu.getItem(2).getSubMenu();
        if(subMenu != null) {
            subMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Default sorting
                    allItems.clear();
                    for (UserDetailItemModel itemModel : UserDetailConverter.map(userStorage, detail.getItems())) {
                        allItems.add(new BrowseViewModel(itemModel));
                    }

                    updateSearchedItems();
                    updateListViewAdapter();
                    return true;
                }
            });

            subMenu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Ratio sorting
                    Collections.sort(allItems, new PercentageUserDetailItemModelComparator(true));
                    updateSearchedItems();
                    updateListViewAdapter();
                    return true;
                }
            });

            subMenu.getItem(2).setTitle(detail.getDownload().getSourceLanguage().getName());
            subMenu.getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Source text sorting
                    Collections.sort(allItems, new AlphabetUserDetailItemModelComparator(detail, true));
                    updateSearchedItems();
                    updateListViewAdapter();
                    return true;
                }
            });

            subMenu.getItem(3).setTitle(detail.getDownload().getTargetLanguage().getName());
            subMenu.getItem(3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Target text sorting
                    Collections.sort(allItems, new AlphabetUserDetailItemModelComparator(detail, false));
                    updateSearchedItems();
                    updateListViewAdapter();
                    return true;
                }
            });
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        lastSelectedTags = new ArrayList<String>(ServiceProvider.getTags());
        lastIsNoTagIncluded = true;

        userStorage = ServiceProvider.getUserStorage();
        detail = prepareDetailModel();
        allItems = new ArrayList<BrowseViewModel>();
        List<UserDetailItemModel> items = UserDetailConverter.map(userStorage, detail.getItems());
        for (UserDetailItemModel item : items) {
            allItems.add(new BrowseViewModel(item));
        }

        searchText = (EditText) findViewById(R.id.searchText);

        listView = (ListView) findViewById(R.id.listView);
        updateListViewAdapter();

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = searchText.getText().toString();
                    if (search != lastSearch) {
                        lastSearch = search;
                        updateSearchedItems();
                        updateListViewAdapter();
                        updateSuccessBar();
                    }

                    return true;
                }

                return false;
            }
        });

        updateSuccessBar();
    }

    private void updateSearchedItems() {
        lastSearch = lastSearch == null ? null : lastSearch.toLowerCase();
        for (BrowseViewModel itemModel : allItems) {
            boolean isVisible = false;
            if(lastSearch == null || itemModel.getModel().getModel().getOriginalText().toLowerCase().contains(lastSearch)) {
                if(lastIsNoTagIncluded && itemModel.getModel().getModel().getTags().isEmpty()) {
                    isVisible = true;
                } else {
                    for (String tag : lastSelectedTags) {
                        if (itemModel.getModel().getModel().getTags().contains(tag)) {
                            isVisible = true;
                        }
                    }
                }
            }

            itemModel.setIsVisible(isVisible);
        }
    }

    private void updateListViewAdapter() {
        List<UserDetailItemModel> visibleItems = new ArrayList<UserDetailItemModel>();
        for (BrowseViewModel item : allItems) {
            if(item.isVisible()) {
                visibleItems.add(item.getModel());
            }
        }

        BrowseListAdapter adapter = new BrowseListAdapter(this, visibleItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateSuccessBar() {
        TextView percentageText = (TextView) findViewById(R.id.percentageText);
        FrameLayout placeholder = (FrameLayout) findViewById(R.id.backgroundLayout);

        int totalCount = 0;
        int correctCount = 0;
        int wrongCount = 0;
        for (BrowseViewModel viewItem : allItems) {
            if(viewItem.isVisible()) {
                totalCount += viewItem.getModel().getTotalCount();
                correctCount += viewItem.getModel().getCorrectCount();
                wrongCount += viewItem.getModel().getWrongCount();
            }
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
