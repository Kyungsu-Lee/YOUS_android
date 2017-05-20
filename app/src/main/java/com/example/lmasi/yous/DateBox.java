package com.example.lmasi.yous;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class DateBox extends RelativeLayout {

    private ArrayList<LogicText> boxes = new ArrayList<>();


    DateText[] dateTexts;

    public DateBox(Context context, int number) {
        super(context);

        MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute(Integer.toString(number));

    }


    // AsyncTask클래스는 항상 Subclassing 해서 사용 해야 함.
    // 사용 자료형은
    // background 작업에 사용할 data의 자료형: String 형
    // background 작업 진행 표시를 위해 사용할 인자: Integer형
    // background 작업의 결과를 표현할 자료형: Long
    // 인자를 사용하지 않은 경우 Void Type 으로 지정.
    public class MyAsyncTask extends AsyncTask<String, Void, Void> {

        StringBuilder builder = new StringBuilder("");
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<String> contents = new ArrayList<>();

        @Override
        protected Void doInBackground(String... params) {

            try
            {
                builder = new StringBuilder("");
                URL url = new URL("http://119.202.36.218/yous/content/texts/" + params[0] + "/recent");
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String str;
                while((str = br.readLine()) != null)
                {
                    builder.append(str + "\n");
                }



            } catch(Exception ex){
                ex.printStackTrace();
            }

            String[] split = builder.toString().split("\n");
            for(String s : split)
            {
                String date = s.split("::")[0];
                String content = s.split("::")[1];

                dates.add(date);
                contents.add(content);
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dateTexts = new DateText[dates.size()];


            for(int i = 0; i< dateTexts.length; i++)
            {
                dateTexts[i] = new DateText(getContext(), dates.get(i), contents.get(i), i);
                dateTexts[i].setId(dateTexts[i].hashCode());
                if(i > 0) dateTexts[i].addRule(RelativeLayout.BELOW, dateTexts[i-1].getId()).setMargin(0, 60, 0, 0);
                DateBox.this.addView(dateTexts[i]);
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
