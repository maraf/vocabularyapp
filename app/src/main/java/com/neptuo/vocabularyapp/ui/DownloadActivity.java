package com.neptuo.vocabularyapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.services.VocabularyService;
import com.neptuo.vocabularyapp.ui.adapters.DownloadItemListAdapter;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadListAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTaskResult;

import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private VocabularyService service;
    private List<DownloadModel> definitions;

    private Button downloadButton;
    private Button downloadItemButton;
    private ListView listView;
    private ProgressDialog progress;
    private int downloadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final DownloadActivity self = this;

        service = ServiceProvider.getVocabulary();
        definitions = ServiceProvider.getDefinitions();

        downloadButton = (Button) findViewById(R.id.downloadButton);
        listView = (ListView) findViewById(R.id.listView);
        downloadItemButton = (Button) findViewById(R.id.downloadItemButton);
        progress = new ProgressDialog(self);

        downloadItemButton.setEnabled(false);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();

                DownloadListAsyncTask task = new DownloadListAsyncTask(self);
                task.execute("http://vocabulary.neptuo.com/api/v1/list.xml");
            }
        });

        downloadItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                ServiceProvider.getDetails().clear();

                for (DownloadModel model : definitions) {
                    if(model.isSelected()) {
                        new DownloadDetailAsyncTask(self).execute(model);
                        downloadCount++;
                    }
                }

                if(downloadCount == 0)
                    progress.hide();
            }
        });
    }

    public void downloadingCompleted(DownloadListAsyncTaskResult result) {
        progress.hide();

        if(result == null) {
            Toast.makeText(this, "Chyba při pokusu o stažení dat a jejich zpracování.", Toast.LENGTH_SHORT).show();
        } else {
            if(result.isSuccessfull()) {
                if(result.getContent().size() > 0) {
                    definitions.clear();
                    definitions.addAll(result.getContent());

                    DownloadItemListAdapter adapter = new DownloadItemListAdapter(this, result.getContent());
                    adapter.setItemSelectedListener(new DownloadItemListAdapter.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(DownloadModel model, boolean isChecked) {
                            downloadItemButton.setEnabled(true);
                        }

                        @Override
                        public void onNothingSelected() {
                            downloadItemButton.setEnabled(false);
                        }
                    });

                    listView.setAdapter(adapter);
                    downloadItemButton.setEnabled(false);
                }
                else {
                    Toast.makeText(this, "Na serveru nejsou žádné slovníky.", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void downloadingCompleted(DownloadDetailAsyncTaskResult result) {
        downloadCount--;
        if(downloadCount == 0)
            progress.hide();

        if(result == null) {
            Toast.makeText(this, "Chyba při pokusu o stažení dat a jejich zpracování.", Toast.LENGTH_SHORT).show();
        } else {
            if(result.isSuccessfull()) {

                int index = ServiceProvider.getDetails().indexOf(result.getContent());
                if(index >= 0)
                    ServiceProvider.getDetails().get(index).getItems().addAll(result.getContent().getItems());
                else
                    ServiceProvider.getDetails().add(result.getContent());

                Toast.makeText(this, "Staženo celkem položek: " + result.getContent().getItems().size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
