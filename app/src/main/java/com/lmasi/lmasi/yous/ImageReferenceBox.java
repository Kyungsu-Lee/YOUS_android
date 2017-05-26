package com.lmasi.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lmasi on 2017. 5. 25..
 */

public class ImageReferenceBox extends RelativeLayout {
    private final int padding = 60;
    private final int DEFALUT_TITLE_SIZE      = 30;
    private final int DEFAULT_CONTENT_SIZE    = 28;

    ImageView line;

    YousTextView title;
    YousTextView contents;

    String text = "";


    public ImageReferenceBox(Context context, int idx, boolean agree) {
        super(context);

        this.setPadding(padding, 0, padding, padding);


        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(idx), agree ? "agree" : "disagree");
    }



    public class MyAsyncTask extends AsyncTask<String, Integer,String>
    {
        private JSONArray ja;
        private  int index;


        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try
            {


                index = Integer.parseInt(params[0]);
                URL url = new URL("http://119.202.36.218/yous/php/get_image_reference.php");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;)
                        {

                            String line = br.readLine();
                            if(line == null) break;

                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

            return jsonHtml.toString();
        }


        @Override
        protected void onPostExecute(String str){

            String content = "";

            try {
                JSONObject root = new JSONObject(str);
                ja = root.getJSONArray("result");


                for (int i = 0; i < ja.length(); i++) {
                    if (ja.getJSONObject(i).getString("total_index").equals("" + index))
                        content = ja.getJSONObject(i).getString("reference");
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            ImageReferenceBox.this.title = new YousTextView(getContext());
            ImageReferenceBox.this.title.setText("이미지 출처");
            ImageReferenceBox.this.title.setTextColor(Color.rgb(102, 102, 102));
            ImageReferenceBox.this.title.setTextSize(DEFALUT_TITLE_SIZE);
            ImageReferenceBox.this.title.setTypeface(YousResource.KOPUB_MID);
            ImageReferenceBox.this.title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setMargin(0, padding, 0, 0)
            );
            ImageReferenceBox.this.title.setId(title.hashCode());
            ImageReferenceBox.this.addView(ImageReferenceBox.this.title);


            ImageReferenceBox.this.contents = new YousTextView(getContext());
            ImageReferenceBox.this.contents.setText(content);
            ImageReferenceBox.this.contents.setTextColor(Color.rgb(102, 102, 102));
            ImageReferenceBox.this.contents.setTextSize(DEFAULT_CONTENT_SIZE);
            ImageReferenceBox.this.contents.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .addRules(RelativeLayout.BELOW, title.getId())
                    .setMargin(0, 48, 0, 0)
            );
            ImageReferenceBox.this.contents.setId(contents.hashCode());
            ImageReferenceBox.this.addView(ImageReferenceBox.this.contents);


            RelativeLayout margin = new RelativeLayout(getContext());
            margin.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 30)
                    .addRules(RelativeLayout.BELOW, contents.getId())
            );
            ImageReferenceBox.this.addView(margin);
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