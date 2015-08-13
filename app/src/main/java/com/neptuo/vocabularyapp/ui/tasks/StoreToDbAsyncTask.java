package com.neptuo.vocabularyapp.ui.tasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.DetailItemRepository;
import com.neptuo.vocabularyapp.data.DownloadRepository;
import com.neptuo.vocabularyapp.data.LanguageRepository;
import com.neptuo.vocabularyapp.data.Sql;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.DownloadActivity;

import java.util.List;

/**
 * Created by Windows10 on 8/13/2015.
 */
public class StoreToDbAsyncTask extends AsyncTask<List<DetailModel>, Void, Void> {
    private DownloadActivity activity;
    private DbContext dbContext;

    public StoreToDbAsyncTask(DownloadActivity activity, DbContext dbContext) {
        this.activity = activity;
        this.dbContext = dbContext;
    }

    @Override
    protected Void doInBackground(List<DetailModel>... params) {

        SQLiteDatabase db = dbContext.getWritableDatabase();
        DetailItemRepository detailItems = new DetailItemRepository(db);
        DownloadRepository downloads = new DownloadRepository(db);

        // Drop content of db.
        downloads.truncate();
        detailItems.truncate();

        // Store new items.
        for(DetailModel model : params[0]) {
            DownloadModel download = model.getDownload();
            int downloadId = downloads.save(download);

            for(DetailItemModel detailItem : model.getItems()) {
                detailItems.save(downloadId, detailItem);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        activity.storeCompleted();
    }
}
