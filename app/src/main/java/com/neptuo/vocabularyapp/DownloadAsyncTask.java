package com.neptuo.vocabularyapp;

import android.os.AsyncTask;
import android.util.Xml;

import com.neptuo.vocabularyapp.services.Vocabulary;
import com.neptuo.vocabularyapp.services.VocabularyItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
    private DownloadActivity activity;

    public DownloadAsyncTask(DownloadActivity activity) {
        this.activity = activity;
    }

    @Override
    protected DownloadAsyncTaskResult doInBackground(String... urls) {
        HttpClient client = new DefaultHttpClient();
        HttpUriRequest request = new HttpGet(urls[0]);
        HttpResponse response = null;

        try {
            response = client.execute(request);

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(response.getEntity().getContent(), "utf-8");
            parser.nextTag();

            return new DownloadAsyncTaskResult(true, parseVocabulary(parser), null);
        } catch (ClientProtocolException e) {
            return new DownloadAsyncTaskResult(false, null, "Chyba komunikace.");
        } catch (IOException e) {
            return new DownloadAsyncTaskResult(false, null, "Nelze navázat spojení. Pravděpodobně adresa neexistuje.");
        } catch (XmlPullParserException e) {
            return new DownloadAsyncTaskResult(false, null, "Chyba formátování souboru staženého ze serveru.");
        }
    }

    private Vocabulary parseVocabulary(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "vocabulary");

        String sourceLanguageCode = parser.getAttributeValue(null, "sourceLanguage");
        String targetLanguageCode = parser.getAttributeValue(null, "targetLanguage");
        Vocabulary result = new Vocabulary(sourceLanguageCode, targetLanguageCode);

        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if (name.equals("item")) {
                    String originalText = parser.getAttributeValue(null, "originalText");
                    String translatedText = parser.getAttributeValue(null, "translatedText");
                    String translatedDescription = parser.getAttributeValue(null, "translatedDescription");

                    result.getItems().add(new VocabularyItem(originalText, translatedText, translatedDescription));

                    parser.next();
                }
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(DownloadAsyncTaskResult result) {
        activity.downloadingCompleted(result);
    }
}
