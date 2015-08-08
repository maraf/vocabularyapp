package com.neptuo.vocabularyapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Windows10 on 8/8/2015.
 */
public class DownloadAsyncTask extends AsyncTask<String, Void, DownloadAsyncTaskResult> {
    @Override
    protected DownloadAsyncTaskResult doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();

            InputStream content = connection.getInputStream();

            int totalSize = connection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            StringBuilder contentBuilder = new StringBuilder(totalSize);
            while ((bufferLength = content.read(buffer)) > 0) {

                downloadedSize += bufferLength;
                int progress = (int) (downloadedSize * 100 / totalSize);
                contentBuilder.append(buffer);
            }

            return new DownloadAsyncTaskResult(true, contentBuilder.toString(), null);
        } catch (MalformedURLException e) {
            return new DownloadAsyncTaskResult(false, null, "Nevalidní URL adresa.");
        } catch (ProtocolException e) {
            return new DownloadAsyncTaskResult(false, null, "Chyba komunikace.");
        } catch (IOException e) {
            return new DownloadAsyncTaskResult(false, null, "Nelze navázat spojení. Pravděpodobně adresa neexistuje.");
        }
    }
}
