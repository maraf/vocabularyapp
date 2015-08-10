package com.neptuo.vocabularyapp.ui.tasks;

import android.os.AsyncTask;
import android.util.Xml;

import com.neptuo.vocabularyapp.services.parsers.XmlDownloadModelParser;
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

/**
 * Created by Windows10 on 8/9/2015.
 */
public class DownloadListAsyncTask extends AsyncTask<String, Void, DownloadListAsyncTaskResult> {
    private DownloadActivity activity;

    public DownloadListAsyncTask(DownloadActivity activity) {
        this.activity = activity;
    }


    @Override
    protected DownloadListAsyncTaskResult doInBackground(String... urls) {
        HttpClient client = new DefaultHttpClient();
        HttpUriRequest request = new HttpGet(urls[0]);
        HttpResponse response = null;

        try {
            response = client.execute(request);

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(response.getEntity().getContent(), "utf-8");
            parser.nextTag();

            return new DownloadListAsyncTaskResult(true, XmlDownloadModelParser.parseList(parser), null);
        } catch (ClientProtocolException e) {
            return new DownloadListAsyncTaskResult(false, null, "Chyba komunikace.");
        } catch (IOException e) {
            return new DownloadListAsyncTaskResult(false, null, "Nelze navázat spojení. Pravděpodobně adresa neexistuje.");
        } catch (XmlPullParserException e) {
            return new DownloadListAsyncTaskResult(false, null, "Chyba formátování souboru staženého ze serveru.");
        }
    }

    @Override
    protected void onPostExecute(DownloadListAsyncTaskResult result) {
        activity.downloadingCompleted(result);
    }
}
