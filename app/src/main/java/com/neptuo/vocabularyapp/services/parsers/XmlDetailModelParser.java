package com.neptuo.vocabularyapp.services.parsers;

import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class XmlDetailModelParser {

    public static DetailModel parse(XmlPullParser parser, DownloadModel download) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "vocabulary");
        DetailModel result = new DetailModel(download);

        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if (name.equals("item")) {
                    String originalText = parser.getAttributeValue(null, "originalText");
                    String translatedText = parser.getAttributeValue(null, "translatedText");
                    String translatedDescription = parser.getAttributeValue(null, "translatedDescription");

                    result.getItems().add(new DetailItemModel(originalText, translatedText, translatedDescription));

                    parser.next();
                }
            }
        }

        return result;
    }

}
