package com.neptuo.vocabularyapp.ui.tasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.neptuo.vocabularyapp.data.DbContext;
import com.neptuo.vocabularyapp.data.DetailItemRepository;
import com.neptuo.vocabularyapp.data.DownloadRepository;
import com.neptuo.vocabularyapp.data.Sql;
import com.neptuo.vocabularyapp.services.ServiceProvider;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Windows10 on 8/14/2015.
 */
public class LoadFromDbAsyncTask extends AsyncTask<Void, Void, List<DetailModel>> {
    private MainActivity activity;
    private DbContext dbContext;

    public LoadFromDbAsyncTask(MainActivity activity, DbContext dbContext) {
        this.activity = activity;
        this.dbContext = dbContext;
    }

    @Override
    protected List<DetailModel> doInBackground(Void... params) {

        List<DetailModel> result = new ArrayList<DetailModel>();

        try {
            SQLiteDatabase db = dbContext.getWritableDatabase();
            DetailItemRepository detailItems = new DetailItemRepository(db);
            DownloadRepository downloads = new DownloadRepository(db);


            Map<Integer, DownloadModel> models = downloads.getList();
            for (Map.Entry<Integer, DownloadModel> model : models.entrySet()) {
                List<DetailItemModel> itemModels = detailItems.getListByDownloadId(model.getKey());

                DetailModel detailModel = new DetailModel(model.getValue());
                detailModel.getItems().addAll(itemModels);
                result.add(detailModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<DetailModel> detailModels) {
        activity.dbLoadCompleted(detailModels);
    }
}
