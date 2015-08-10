package com.neptuo.vocabularyapp.ui.tasks;

import android.os.AsyncTask;
import android.util.Xml;

import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.services.parsers.XmlDetailModelParser;
import com.neptuo.vocabularyapp.ui.DownloadActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadDetailAsyncTask extends AsyncTask<DownloadModel, Void, List<DownloadDetailAsyncTaskResult>> {
    private DownloadActivity activity;

    public DownloadDetailAsyncTask(DownloadActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<DownloadDetailAsyncTaskResult> doInBackground(DownloadModel... downloads) {
        HttpClient client = new DefaultHttpClient();

        List<DownloadDetailAsyncTaskResult> result = new ArrayList<DownloadDetailAsyncTaskResult>();
        for (DownloadModel download : downloads) {
            for (String url : download.getUrls()) {

                HttpUriRequest request = new HttpGet(url);
                HttpResponse response = null;

                try {
                    response = client.execute(request);

                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(response.getEntity().getContent(), "utf-8");
                    parser.nextTag();

                    result.add(new DownloadDetailAsyncTaskResult(true, XmlDetailModelParser.parse(parser, download), null));
                } catch (ClientProtocolException e) {
                    result.add(new DownloadDetailAsyncTaskResult(false, null, "Chyba komunikace."));
                } catch (IOException e) {
                    result.add(new DownloadDetailAsyncTaskResult(false, null, "Nelze navázat spojení. Pravděpodobně adresa neexistuje."));
                } catch (XmlPullParserException e) {
                    result.add(new DownloadDetailAsyncTaskResult(false, null, "Chyba formátování souboru staženého ze serveru."));
                }
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<DownloadDetailAsyncTaskResult> result) {
        for (DownloadDetailAsyncTaskResult item : result) {
            activity.downloadingCompleted(item);
        }
    }
}
