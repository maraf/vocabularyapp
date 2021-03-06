package com.neptuo.vocabularyapp.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neptuo.vocabularyapp.MyApplication;
import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.ui.fragments.SelectDetailDialogFragment;
import com.neptuo.vocabularyapp.ui.tasks.LoadFromDbAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActivityBase {

    private Button translateButton;
    private Button browseButton;
    private Button downloadButton;
    private Button configurationButton;
    private TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ServiceProvider.tryInitialize(getApplicationContext())) {
            new LoadFromDbAsyncTask(this, new DbContext(getApplicationContext())).execute();
        }

        final MainActivity self = this;

        MyApplication app = (MyApplication)getApplication();

        translateButton =  (Button) findViewById(R.id.translateButton);
        browseButton = (Button) findViewById(R.id.browseButton);
        downloadButton = (Button) findViewById(R.id.downloadButton);
        configurationButton = (Button) findViewById(R.id.configurationButton);
        versionText = (TextView) findViewById(R.id.versionText);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = "v" + packageInfo.versionName;
            versionText.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionText.setVisibility(View.GONE);
        }

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SelectDetailDialogFragment fragment = new SelectDetailDialogFragment();
                fragment.setOkListener(new SelectDetailDialogFragment.OnClickOkListener() {
                    @Override
                    public void onClick(int detailIndex, boolean isReverse) {
                        Intent intent = new Intent(self, TranslateActivity.class);
                        intent.putExtra(TranslateActivity.PARAMETER_DETAIL_INDEX, detailIndex);
                        intent.putExtra(TranslateActivity.PARAMETER_IS_REVERSE, isReverse);
                        startActivity(intent);
                    }
                });
                fragment.show(transaction, "dialog");
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                SelectDetailDialogFragment fragment = new SelectDetailDialogFragment();
                fragment.setOkListener(new SelectDetailDialogFragment.OnClickOkListener() {
                    @Override
                    public void onClick(int detailIndex, boolean isReverse) {
                        Intent intent = new Intent(self, BrowseActivity.class);
                        intent.putExtra(BrowseActivity.PARAMETER_DETAIL_INDEX, detailIndex);
                        intent.putExtra(BrowseActivity.PARAMETER_IS_REVERSE, isReverse);
                        startActivity(intent);
                    }
                });
                fragment.show(transaction, "dialog");
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, DownloadActivity.class);
                startActivity(intent);
            }
        });

        configurationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, ConfigurationActivity.class);
                startActivity(intent);
            }
        });

        checkTranslateActivityAvailability();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTranslateActivityAvailability();
    }

    private void checkTranslateActivityAvailability() {
        boolean hasVocabularyItems = ServiceProvider.getDetails().size() > 0;
        translateButton.setEnabled(hasVocabularyItems);
        browseButton.setEnabled(hasVocabularyItems);
    }

    public void dbLoadCompleted(List<DetailModel> models) {
        Toast.makeText(this, R.string.db_loaded, Toast.LENGTH_SHORT).show();
        ServiceProvider.getDetails().clear();
        ServiceProvider.getDetails().addAll(models);

        ServiceProvider.getTags().clear();
        for (DetailModel model : models) {
            for (DetailItemModel itemModel : model.getItems()) {
                ServiceProvider.getTags().addAll(itemModel.getTags());
            }
        }

        checkTranslateActivityAvailability();
    }
}
