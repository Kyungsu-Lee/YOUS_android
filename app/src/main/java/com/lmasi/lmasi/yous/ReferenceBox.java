package com.lmasi.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by lmasi on 2017. 5. 18..
 */

public class ReferenceBox extends RelativeLayout {

    private final int padding = 60;
    private final int DEFALUT_TITLE_SIZE      = 30;
    private final int DEFAULT_CONTENT_SIZE    = 28;

    ImageView line;

    YousTextView title;
    HyperLinkTextView[] contents;

    String text = "";

    public ReferenceBox(Context context, int idx, boolean agree) {
        super(context);

        this.setPadding(padding, 0, padding, padding);




        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(idx), agree ? "agree" : "disagree");
    }



    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();

        String title = "";

        String[] titless;

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                URL url_title = new URL("http://119.202.36.218/yous/content/texts/" + params[0] + "/" + params[1] + "_title");
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

                            title += line + "==-==";
                        }
                        br.close();
                    }
                    conn.disconnect();
                    title = replaceLast(title, "==-==", "");
                    titless = title.split("==-==");

                }

            } catch(Exception ex){
                ex.printStackTrace();
            }


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
                            if(line.equals("")) continue;

                            text += line + "\n";

                            titles.add(line.split("::::")[0]);
                            urls.add(line.split("::::")[1]);
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

            line = new ImageView(getContext());
            line.setBackgroundColor(Color.rgb(102, 102, 102));
            line.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 1).addRules(RelativeLayout.CENTER_HORIZONTAL));

            if(titless.length%2 == 1) {

                line.setId(line.hashCode());
                ReferenceBox.this.addView(line);
            }

            ReferenceBox.this.title = new YousTextView(getContext());
            ReferenceBox.this.title.setText("출처");
            ReferenceBox.this.title.setTextColor(Color.rgb(102, 102, 102));
            ReferenceBox.this.title.setTextSize(DEFALUT_TITLE_SIZE);
            ReferenceBox.this.title.setTypeface(YousResource.KOPUB_MID);
            ReferenceBox.this.title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .addRules(RelativeLayout.BELOW, line.getId())
                    .setMargin(0, padding, 0, 0)
            );
            ReferenceBox.this.title.setId(title.hashCode());
            ReferenceBox.this.addView(ReferenceBox.this.title);



            contents = new HyperLinkTextView[titles.size()];


            for(int i=0; i<contents.length; i++)
            {
                contents[i] = new HyperLinkTextView(getContext(), titles.get(i), urls.get(i));
                YousParameter param = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                contents[i].setId(contents[i].hashCode());
                if(i == 0)
                contents[i].setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .addRules(RelativeLayout.BELOW, ReferenceBox.this.title.getId())
                        .setMargin(0, 48)
                );
                if(i > 0) {
                    param.addRules(RelativeLayout.BELOW, contents[i-1].getId()).setMargin(0, 35);
                    contents[i].setLayoutParams(param);
                }
                ReferenceBox.this.addView(contents[i]);
            }

            RelativeLayout margin = new RelativeLayout(getContext());
            margin.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 30)
                                    .addRules(RelativeLayout.BELOW, contents[contents.length-1].getId())
            );
            ReferenceBox.this.addView(margin);
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
