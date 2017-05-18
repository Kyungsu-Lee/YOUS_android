package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by lmasi on 2017. 5. 18..
 */

public class ReferenceBox extends RelativeLayout {

    private final int padding = 60;
    private final int DEFALUT_TITLE_SIZE      = 35;
    private final int DEFAULT_CONTENT_SIZE    = 30;

    ImageView line;

    YousTextView title;
    YousTextView contents;

    String text = "";

    public ReferenceBox(Context context, int idx) {
        super(context);

        this.setPadding(padding, padding, padding, padding);

        line = new ImageView(getContext());
        line.setBackgroundColor(Color.rgb(102, 102, 102));
        line.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 2).addRules(RelativeLayout.CENTER_HORIZONTAL));
        line.setId(line.hashCode());
        this.addView(line);

        title = new YousTextView(getContext());
        title.setText("출처");
        title.setTextColor(Color.rgb(102, 102, 102));
        title.setTextSize(DEFALUT_TITLE_SIZE);
        title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.BELOW, line.getId())
                                    .setMargin(0, padding, 0, 0)
        );
        title.setId(title.hashCode());
        this.addView(title);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(idx));
    }



    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                URL url_title = new URL("http://119.202.36.218/yous/content/texts/" + params[0] + "/agree_reference");
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
                            //    Log.d("aaa", line);
                            if(line == null) break;

                            text += line + "\n";
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

            contents = new YousTextView(getContext());
            contents.setTextSize(DEFAULT_CONTENT_SIZE);
            contents.setText(text);
            contents.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        .addRules(RelativeLayout.BELOW, title.getId())
                                        .setMargin(0, 5, 0, 0)
            );
            contents.setTextColor(Color.rgb(102, 102, 102));
            ReferenceBox.this.addView(contents);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

}
