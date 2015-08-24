package com.neptuo.vocabularyapp.services.parsers;

import com.neptuo.vocabularyapp.data.Sql;
import com.neptuo.vocabularyapp.services.models.DetailItemModel;
import com.neptuo.vocabularyapp.services.models.DetailModel;
import com.neptuo.vocabularyapp.services.models.DownloadModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows10 on 8/9/2015.
 */
public class XmlDetailModelParser {

    public static DetailModel parse(XmlPullParser parser, DownloadModel download) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "vocabulary");
        DetailModel result = new DetailModel(download);

        while (parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() == XmlPullParser.START_TAG) {
                String itemName = parser.getName();
                if (itemName.equals("item")) {
                    String sourceText = null;
                    String sourceDescription = null;
                    String targetText = null;
                    String targetDescription = null;
                    List<String> tags = new ArrayList<String>();

                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            String subName = parser.getName();
                            if (subName.equals("source")) {
                                sourceText = parser.getAttributeValue(null, "text");
                                sourceDescription = parser.getAttributeValue(null, "description");
                                parser.next();
                            } else if (subName.equals("target")) {
                                targetText = parser.getAttributeValue(null, "text");
                                targetDescription = parser.getAttributeValue(null, "description");
                                parser.next();
                            } else if(subName.equals("tag")) {
                                tags.add(parser.getText());
                                parser.next();
                            }

                            //if(sourceText != null && targetText != null)
                            //    break;
                        }
                    }

                    DetailItemModel model = new DetailItemModel(sourceText, targetText, sourceDescription, targetDescription);
                    model.getTags().addAll(tags);
                    result.getItems().add(model);
                    parser.next();
                    parser.next();
                }
            }
        }

        return result;
    }

}
