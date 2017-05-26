package com.lmasi.lmasi.yous;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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
    private String searchTitle;

    public ContentBox(Context context, String searchTitle) {
        super(context);

        this.searchTitle = searchTitle;

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("http://119.202.36.218/yous/titles");
    }

    public void clickAction()
    {

    }

    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        ProgressDialog asyncDialog;

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();
        ArrayList<String> subtitle = new ArrayList<>();
        ArrayList<String> agreeTitles = new ArrayList<>();
        ArrayList<String> disagreeTitles = new ArrayList<>();
        ArrayList<String> agreeContent = new ArrayList<>();
        ArrayList<String> disagreeContent = new ArrayList<>();
        @Override
        protected void onPreExecute() {
         //   asyncDialog = new ProgressDialog(getContext());
           // asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
          //  asyncDialog.show();

            super.onPreExecute();
        }

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
                            subtitle.add(tmp[1]);
                            agreeTitles.add(tmp[2]);
                            disagreeTitles.add(tmp[3]);
                            colors.add(tmp[4]);
                            agreeContent.add(tmp[5]);
                            disagreeContent.add(tmp[6]);
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


            ArrayList<String> search_title = new ArrayList<>();
            for(String s : titles)
                if(s.indexOf(searchTitle) >= 0) search_title.add(s);

            cardViews = new CardView[search_title.size()];

            for(int i=0; i< search_title.size(); i++)
            {
                cardViews[i] = new CardView(getContext(),
                        search_title.get(i),
                        subtitle.get(i),
                        titles.indexOf(search_title.get(i)),
                        Color.parseColor("#"+colors.get(i)),
                        agreeTitles.get(i),
                        disagreeTitles.get(i),
                        agreeContent.get(i),
                        disagreeContent.get(i)
                )
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
           // asyncDialog.dismiss();
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
