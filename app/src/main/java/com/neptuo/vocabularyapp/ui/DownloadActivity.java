package com.neptuo.vocabularyapp.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.neptuo.vocabularyapp.R;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DefinitionModel;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.VocabularyService;
import com.neptuo.vocabularyapp.ui.adapters.DefinitionModelListAdapter;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDefinitionListAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDefinitionListAsyncTaskResult;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTask;
import com.neptuo.vocabularyapp.ui.tasks.DownloadDetailAsyncTaskResult;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private VocabularyService service;
    private List<DefinitionModel> definitions;

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
                //DownloadDetailAsyncTask task = new DownloadDetailAsyncTask(self);
                //task.execute("http://home.neptuo.com/vocabulary/api/cs-de.xml");

                DownloadDefinitionListAsyncTask task = new DownloadDefinitionListAsyncTask(self);
                task.execute("http://home.neptuo.com/vocabulary/api/index.xml");
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                downloadItemButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                downloadItemButton.setEnabled(false);
            }
        });

        downloadItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();

                List<DefinitionModel> selectedModels = new ArrayList<DefinitionModel>();
                for (DefinitionModel model : definitions) {
                    if(model.isSelected()) {
                        for (String url : model.getUrls()) {
                            new DownloadDetailAsyncTask(self).execute(url);
                            downloadCount++;
                        }
                    }
                }

                if(downloadCount == 0)
                    progress.hide();
            }
        });
    }

    public void downloadingCompleted(DownloadDefinitionListAsyncTaskResult result) {
        progress.hide();

        if(result == null) {
            Toast.makeText(this, "Chyba při pokusu o stažení dat a jejich zpracování.", Toast.LENGTH_SHORT).show();
        } else {
            if(result.isSuccessfull()) {
                if(result.getContent().size() > 0) {
                    definitions.clear();
                    definitions.addAll(result.getContent());

                    listView.setAdapter(new DefinitionModelListAdapter(this, result.getContent()));
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
                service.getItems().clear();
                for (DetailItemModel item : result.getContent().getItems()) {
                    service.getItems().add(item);
                }

                Toast.makeText(this, "Staženo celkem položek: " + result.getContent().getItems().size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
