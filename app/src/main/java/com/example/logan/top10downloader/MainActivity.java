package com.example.logan.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        Log.d(TAG, "onCreate: starting ASyncTask");
        DownloadData dd = new DownloadData();
        dd.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onCreate: done");
    }

    //URL, progess(not used), XML string
    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";
        
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApps parsApps = new ParseApps();
            parsApps.parse(s);

            //ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<FeedEntry>(MainActivity.this, R.layout.list_item, parsApps.getApp());
            //listApps.setAdapter(arrayAdapter);

            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this, R.layout.list_record, parsApps.getApp());
            listApps.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: starts with " + params[0]);

            String rssFeed = downloadXML(params[0]);

            if(rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }

            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                int response = connection.getResponseCode();

                Log.d(TAG, "downloadXML: the response code was " + response);

                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputReader);

                int charsRead;
                char[] inputBuffer = new char[500];
                while(true) {
                    charsRead = reader.read(inputBuffer);

                    if(charsRead < 0) break;

                    if(charsRead > 0) xmlResult.append(String.copyValueOf(inputBuffer, 0 , charsRead));
                }
                reader.close();
            } catch (MalformedURLException e){
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch(IOException e) {
                Log.e(TAG, "downloadXML:  IO Exception reading data: " + e.getMessage());
            } catch(SecurityException e) {
                Log.e(TAG, "downloadXML: Security exception, needs permission? " + e.getMessage());
            }

            return xmlResult.toString();
        }
    }
}
