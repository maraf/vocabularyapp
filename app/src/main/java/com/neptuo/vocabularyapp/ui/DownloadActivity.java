package com.neptuo.vocabularyapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.services.ConfigurationStorage;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.adapters.DownloadItemListAdapter;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.StoreToDbAsyncTask;

import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private List<DownloadModel> definitions;

    private Button downloadButton;
    private Button downloadItemButton;
    private DownloadItemListAdapter tableLayoutAdapter;
    private TableLayout tableLayout;
    private ProgressDialog progress;
    private ConfigurationStorage configurationStorage;
    private int downloadCount = 0;
    private int totalItemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final DownloadActivity self = this;

        definitions = ServiceProvider.getDefinitions();
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
                totalItemCount = 0;

                definitions.clear();
                definitions.addAll(tableLayoutAdapter.getModelsToDownload());
                for (DownloadModel model : definitions) {
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
                definitions.clear();
                definitions.addAll(result.getContent());

                tableLayoutAdapter = new DownloadItemListAdapter(this, result.getContent());
                tableLayoutAdapter.setItemSelectedListener(new DownloadItemListAdapter.OnItemSelectedListener() {
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
                totalItemCount += result.getContent().getItems().size();

                boolean isAdded = false;
                for (DetailModel detail : ServiceProvider.getDetails()) {
                    if(detail.getDownload().hashCode() == result.getContent().getDownload().hashCode()) {
                        detail.getItems().addAll(result.getContent().getItems());
                        isAdded = true;
                        break;
                    }
                }

                if(!isAdded) {
                    ServiceProvider.getDetails().add(result.getContent());
                }

            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if(downloadCount == 0) {
            progress.hide();
            Toast.makeText(this, getString(R.string.download_itemcount) + totalItemCount, Toast.LENGTH_SHORT).show();
            new StoreToDbAsyncTask(this, new DbContext(getApplicationContext())).execute(ServiceProvider.getDetails());
        }
    }

    public void storeCompleted() {
        Toast.makeText(this, R.string.db_saved, Toast.LENGTH_SHORT).show();
    }
}
