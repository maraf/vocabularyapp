package com.neptuo.vocabularyapp.services.parsers;

import com.neptuo.vocabularyapp.services.models.DownloadModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class XmlDownloadModelParser {
    public static List<DownloadModel> parseList(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "vocabularies");

        List<DownloadModel> result = new ArrayList<DownloadModel>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if (name.equals("vocabulary")) {
                    String sourceLanguage = parser.getAttributeValue(null, "sourceLanguage");
                    String targetLanguage = parser.getAttributeValue(null, "targetLanguage");
                    String url = parser.getAttributeValue(null, "url");

                    DownloadModel model = new DownloadModel(sourceLanguage, targetLanguage);
                    model.getUrls().add(url);
                    result.add(model);

                    parser.next();
                }
            }
        }

        return result;
    }
}
