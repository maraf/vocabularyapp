package com.neptuo.vocabularyapp.ui;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.services.ConfigurationStorage;
import com.neptuo.vocabularyapp.services.DuplicityChecker;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.adapters.DownloadListAdapter;
import com.neptuo.vocabularyapp.ui.fragments.DuplicityDialogFragment;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.StoreToDbAsyncTask;

public class DownloadActivity extends ActivityBase {

    private Button downloadButton;
    private Button downloadItemButton;
    private DownloadListAdapter tableLayoutAdapter;
    private TableLayout tableLayout;
    private ProgressDialog progress;
    private ConfigurationStorage configurationStorage;
    private int downloadCount = 0;
    private int totalItemCount = 0;
    private DuplicityChecker duplicityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final DownloadActivity self = this;

        configurationStorage = ServiceProvider.getConfigurationStorage();

        downloadButton = (Button) findViewById(R.id.downloadButton);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        downloadItemButton = (Button) findViewById(R.id.downloadItemButton);
        progress = new ProgressDialog(self);

        downloadItemButton.setEnabled(false);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setMessage(getString(R.string.download_list));
                progress.show();

                DownloadListAsyncTask task = new DownloadListAsyncTask(self);
                task.execute(configurationStorage.getDataUrl());
            }
        });

        downloadItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setMessage(getString(R.string.download_details));
                progress.show();
                ServiceProvider.getDetails().clear();
                ServiceProvider.getTags().clear();
                totalItemCount = 0;
                duplicityChecker = new DuplicityChecker();

                for (DownloadModel model : tableLayoutAdapter.getModelsToDownload()) {
                    new DownloadDetailAsyncTask(self).execute(model);
                    downloadCount++;
                }

                if (downloadCount == 0)
                    progress.hide();
            }
        });
    }

    public void downloadingCompleted(DownloadListAsyncTaskResult result) {

        if (result == null) {
            Toast.makeText(this, R.string.neterror_general, Toast.LENGTH_SHORT).show();
        } else if (result.isSuccessfull()) {
            if (result.getContent().size() > 0) {

                tableLayoutAdapter = new DownloadListAdapter(this, result.getContent());
                tableLayoutAdapter.setItemSelectedListener(new DownloadListAdapter.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected() {
                        downloadItemButton.setEnabled(true);
                    }

                    @Override
                    public void onNothingSelected() {
                        downloadItemButton.setEnabled(false);
                    }
                });

                tableLayout.removeAllViews();
                for (int i = 0; i < tableLayoutAdapter.getCount(); i++) {
                    View view = tableLayoutAdapter.getView(i, null, tableLayout);
                    tableLayout.addView(view);
                }

                downloadItemButton.setEnabled(false);
            } else {
                Toast.makeText(this, R.string.download_nodata, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }

        progress.hide();
    }

    public void downloadingCompleted(DownloadDetailAsyncTaskResult result) {
        downloadCount--;

        if(result == null) {
            Toast.makeText(this, R.string.neterror_general, Toast.LENGTH_SHORT).show();
        } else {
            if(result.isSuccessfull()) {
                boolean isAdded = false;
                for (DetailModel detail : ServiceProvider.getDetails()) {
                    if(detail.getDownload().hashCode() == result.getContent().getDownload().hashCode()) {
                        for (DetailItemModel itemModel : result.getContent().getItems()) {
                            if(duplicityChecker.tryAdd(itemModel)) {
                                detail.getItems().add(itemModel);
                                totalItemCount++;
                            }
                        }
                        isAdded = true;
                        break;
                    }
                }

                if(!isAdded) {
                    DetailModel detail = new DetailModel(result.getContent().getDownload());
                    for (DetailItemModel itemModel : result.getContent().getItems()) {
                        if(duplicityChecker.tryAdd(itemModel)) {
                            detail.getItems().add(itemModel);
                            totalItemCount++;
                        }
                    }

                    ServiceProvider.getDetails().add(detail);
                }

                for (DetailItemModel itemModel : result.getContent().getItems()) {
                    ServiceProvider.getTags().addAll(itemModel.getTags());
                }
            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if(downloadCount == 0) {
            progress.hide();

            String message = getString(R.string.download_itemcount1)
                    + totalItemCount
                    + getString(R.string.download_itemcount2)
                    + duplicityChecker.getCount()
                    + getString(R.string.download_itemcount3);

            if(duplicityChecker.getCount() > 0) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DuplicityDialogFragment fragment = new DuplicityDialogFragment();
                fragment.setDuplicityChecker(duplicityChecker);
                fragment.show(transaction, "dialog");
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            new StoreToDbAsyncTask(this, new DbContext(getApplicationContext())).execute(ServiceProvider.getDetails());
        }
    }

    public void storeCompleted() {
        Toast.makeText(this, R.string.db_saved, Toast.LENGTH_SHORT).show();
    }
}
