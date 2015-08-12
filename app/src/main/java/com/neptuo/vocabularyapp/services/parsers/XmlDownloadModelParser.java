package com.neptuo.vocabularyapp.services.parsers;

import com.neptuo.vocabularyapp.services.models.DownloadModel;
import com.neptuo.vocabularyapp.services.models.LanguageModel;

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
                    String sourceLanguage = null;
                    String targetLanguage = null;
                    List<String> urls = new ArrayList<String>();

                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            String subName = parser.getName();
                            if(subName.equals("source")) {
                                sourceLanguage = parser.getAttributeValue(null, "name");
                            } else if(subName.equals("target")) {
                                targetLanguage = parser.getAttributeValue(null, "name");
                            } else if(subName.equals("content")) {
                                urls.add(parser.getAttributeValue(null, "url"));
                            }
                            parser.next();
                        }
                    }

                    DownloadModel model = new DownloadModel(new LanguageModel(sourceLanguage, null), new LanguageModel(targetLanguage, null));
                    model.getUrls().addAll(urls);
                    result.add(model);

                    parser.next();
                }
            }
        }

        return result;
    }
}
