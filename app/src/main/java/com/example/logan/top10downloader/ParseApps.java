package com.example.logan.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by logan on 2017-07-07.
 */

public class ParseApps {

    private static final String TAG = "ParseApps";

    private ArrayList<FeedEntry> app;

    public ParseApps() {
        this.app = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApp() {
        return app;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";
        String tagName;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                tagName = xpp.getName();
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        if("entry".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry) {
                            if ("entry".equalsIgnoreCase(tagName)) {
                                app.add(currentRecord);
                                inEntry = false;
                            } else if ("name".equalsIgnoreCase(tagName)) {
                                currentRecord.setTitle(textValue);
                            } else if ("artist".equalsIgnoreCase(tagName)) {
                                currentRecord.setArtist(textValue);
                            } else if ("releaseDate".equalsIgnoreCase(tagName)) {
                                currentRecord.setReleaseDate(textValue);
                            } else if ("summary".equalsIgnoreCase(tagName)) {
                                currentRecord.setSummary(textValue);
                            } else if ("image".equalsIgnoreCase(tagName)) {
                                currentRecord.setImageURL(textValue);
                            }
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }

            for(FeedEntry application: app) {
                Log.d(TAG, "************");
                Log.d(TAG, application.toString());

            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        return status;
    }
}
