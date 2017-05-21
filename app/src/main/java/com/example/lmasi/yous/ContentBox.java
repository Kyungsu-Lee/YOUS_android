package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 21..
 */

public class ContentBox extends RelativeLayout {

    private CardView[] cardViews;

    public ContentBox(Context context) {
        super(context);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("http://119.202.36.218/yous/titles");
    }

    public void clickAction()
    {

    }

    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                URL url_title = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)url_title.openConnection();


                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);


                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;)
                        {

                            String line = br.readLine();
                            if(line == null ) break;
                            if(line.equals("") || line.equals("\n")) continue;


                            String[] tmp = line.split("::");

                            titles.add(tmp[0]);
                            colors.add(tmp[1]);
                        }
                        br.close();
                    }
                    conn.disconnect();

                }

            } catch(Exception ex){
                ex.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            cardViews = new CardView[titles.size()];

            for(int i=0; i< titles.size(); i++)
            {
                cardViews[i] = new CardView(getContext(), titles.get(i), "찬성 - 반대", i, Color.parseColor("#"+colors.get(i)))
                {
                    @Override
                    public void clickAction() {
                        ContentBox.this.clickAction();
                    }
                };
                cardViews[i].setId(cardViews[i].hashCode());
                if(i >= 1) cardViews[i].setLayoutParams(cardViews[i].getParams().addRules(RelativeLayout.BELOW, cardViews[i-1].getId()));
                ContentBox.this.addView(cardViews[i]);
            }

        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        private String replaceLast(String string, String toReplace, String replacement)
        {
            int pos = string.lastIndexOf(toReplace);

            if (pos > -1) {
                return string.substring(0, pos)+ replacement + string.substring(pos +   toReplace.length(), string.length());
            } else {
                return string;
            }
        }
    }

}
