package com.example.lmasi.yous;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class LogicBox extends RelativeLayout {

    private ArrayList<LogicText> boxes = new ArrayList<>();

    String title = "";
    String contents = "";

    String[] titles;
    String[] contentss;

    LogicText[] logicTexts;

    public LogicBox(Context context, int number, boolean agree) {
        super(context);

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(Integer.toString(number), agree ? "agree" : "disagree");

    }


    // AsyncTask클래스는 항상 Subclassing 해서 사용 해야 함.
    // 사용 자료형은
    // background 작업에 사용할 data의 자료형: String 형
    // background 작업 진행 표시를 위해 사용할 인자: Integer형
    // background 작업의 결과를 표현할 자료형: Long
    // 인자를 사용하지 않은 경우 Void Type 으로 지정.
    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                URL url_title = new URL("http://119.202.36.218/yous/content/texts/" + params[0] + "/agree_title");
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
                    titles = title.split("==-==");

                }

            } catch(Exception ex){
                ex.printStackTrace();
            }

            try
            {
                URL url_contnet = new URL("http://119.202.36.218/yous/content/texts/" + params[0] + "/" + params[1] + "_content");
                HttpURLConnection conn = (HttpURLConnection)url_contnet.openConnection();

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

                            Log.d("aaaa", line);
                            contents += line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                    contentss = contents.split("=.=");
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            logicTexts = new LogicText[titles.length];


            for(int i=0; i<logicTexts.length; i++)
            {
                logicTexts[i] = new LogicText(getContext(), titles[i], contentss[i], i);
                logicTexts[i].setId(logicTexts[i].hashCode());
                if(i > 0) logicTexts[i].addRule(RelativeLayout.BELOW, logicTexts[i-1].getId());
                LogicBox.this.addView(logicTexts[i]);
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
