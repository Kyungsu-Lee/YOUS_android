package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class Activity_Result extends Activity {

    int scroll_padding = 40;

    RelativeLayout main;
    String title;
    int index;

    RelativeLayout scrolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scroll_padding *= ScreenParameter.getScreenparam_x();

        title = getIntent().getStringExtra("title");
        index = getIntent().getIntExtra("index", 0);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        yScrollView scrollView = new yScrollView(getApplicationContext());
        scrollView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        RelativeLayout title_layout = new RelativeLayout(getApplicationContext());
        title_layout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 420));
        title_layout.setId(title_layout.hashCode());
        scrollView.addView(title_layout);

        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        scrollView.addView(titleLayout);

        //back btn
        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.back));
        back.setLayoutParams(new YousParameter(19,37).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(65,0));
        titleLayout.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Result.this, Activity_Main.class));
                    finish();
                }
                return true;
            }
        });

        YousTextView titleView = new YousTextView(getApplicationContext());
        titleView.setText(title);
        titleView.setTextSize(34);
        titleView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        titleView.setTextColor(Color.rgb(102, 102, 102));
        titleLayout.addView(titleView);

        YousTextView titleTextView = new YousTextView(getApplicationContext());
        titleTextView.setText("자 이제 " + title + "에 대한 너의 입장을 보여줘!");
        titleTextView.setLayoutParams(new YousParameter(428, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                        .addRules(RelativeLayout.BELOW, titleLayout.getId())
                                        .setMargin(0, 10)
        );
        titleTextView.setTextSize(49);
        titleTextView.setTypeface(YousResource.KOPUB_LIGHT);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setId(titleTextView.hashCode());
        scrollView.addView(titleTextView);

        scrolls = new RelativeLayout(getApplicationContext());
        scrolls.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, titleTextView.getId()));
        scrolls.setPadding(scroll_padding, scroll_padding, scroll_padding, scroll_padding);
        scrollView.addView(scrolls);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(index));

        main.addView(scrollView);
    }


    public class MyAsyncTask extends AsyncTask<String, Integer,String> {

        private JSONArray ja;

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{

                URL url = new URL("http://119.202.36.218/yous/php/get_scrollName.php");

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


        protected void onPostExecute(String str){

            Log.e("message", str);

            String name;

            try{
                JSONObject root = new JSONObject(str);
                ja = root.getJSONArray("result");

                final ScrollBar scrollBar2 = new ScrollBar(getApplicationContext(), 'b', ja.getJSONObject(1).getString("top"), ja.getJSONObject(1).getString("bottom"));
                scrollBar2.setId(scrollBar2.hashCode());
                scrolls.addView(scrollBar2.addRule(ReferenceBox.CENTER_HORIZONTAL));
               // scrollBar2.setBackgroundColor(Color.YELLOW);



                final ScrollBar scrollBar = new ScrollBar(getApplicationContext(), 'a', ja.getJSONObject(0).getString("top"), ja.getJSONObject(0).getString("bottom"));
                scrollBar.setId(scrollBar.hashCode());
                scrolls.addView(scrollBar.addRule(RelativeLayout.LEFT_OF, scrollBar2.getId()).setMargin(0, 0, 2*scroll_padding, 0));
               // scrollBar.setBackgroundColor(Color.BLACK);



                final ScrollBar scrollBar3 = new ScrollBar(getApplicationContext(), 'c', ja.getJSONObject(2).getString("top"), ja.getJSONObject(2).getString("bottom"));
                scrollBar3.setId(scrollBar3.hashCode());
                scrolls.addView(scrollBar3.addRule(RelativeLayout.RIGHT_OF, scrollBar2.getId()).setMargin(2 * scroll_padding, 0, 0, 0));
              //  scrollBar3.setBackgroundColor(Color.BLUE);


                ImageView later = new ImageView(getApplicationContext());
                later.setBackground(getResources().getDrawable(R.drawable.later));
                later.setLayoutParams(new YousParameter(750/2, 141)
                                        .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
                );
                later.setId(later.hashCode());
                main.addView(later);

                ImageView confirm = new ImageView(getApplicationContext());
                confirm.setBackground(getResources().getDrawable(R.drawable.confirm));
                confirm.setLayoutParams(new YousParameter(750/2, 141)
                        .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        .addRules(RelativeLayout.RIGHT_OF, later.getId())
                );
                main.addView(confirm);
                confirm.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if(event.getAction() == MotionEvent.ACTION_UP)
                        {
                            UpLoad upLoad = new UpLoad();
                            UpLoad upLoad2 = new UpLoad();
                            UpLoad upLoad3 = new UpLoad();
                            upLoad.execute(Integer.toString(index), "0", Integer.toString(scrollBar.getRate()));
                            upLoad2.execute(Integer.toString(index), "1", Integer.toString(scrollBar2.getRate()));
                            upLoad3.execute(Integer.toString(index), "2", Integer.toString(scrollBar3.getRate()));
                        }

                        return true;
                    }
                });

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public class UpLoad extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                String index = (String)params[0];
                String num_index = (String)params[1];
                String rate = (String)params[2];

                String link="http://" + YousResource.URL + "/yous/php/insert_position.php";
                String data  = URLEncoder.encode("total_index", "UTF-8") + "=" + URLEncoder.encode(index, "UTF-8");
                data += "&" + URLEncoder.encode("num_index", "UTF-8") + "=" + URLEncoder.encode(num_index, "UTF-8");
                data += "&" + URLEncoder.encode("rate", "UTF-8") + "=" + URLEncoder.encode(rate, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
    }

}
