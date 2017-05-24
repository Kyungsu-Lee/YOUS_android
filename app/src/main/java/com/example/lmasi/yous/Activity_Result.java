package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class Activity_Result extends Activity {

    int scroll_padding = 40;

    RelativeLayout main;
    String title;
    int index;

    SearchBox scrolls;

    yScrollView scrollView;
    YousTextView titleView;
    YousTextView titleTextView;

    String title_disagree;
    String title_agree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scroll_padding *= ScreenParameter.getScreenparam_x();

        title = getIntent().getStringExtra("title");
        index = getIntent().getIntExtra("index", 0);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        scrollView = new yScrollView(getApplicationContext());
        scrollView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        main.addView(scrollView);


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
        back.setBackground(getResources().getDrawable(R.drawable.btn_back_full));
        back.setLayoutParams(new YousParameter(41,65).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(25,0));
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

        titleView = new YousTextView(getApplicationContext());
        titleView.setText(title);
        titleView.setTextSize(34);
        titleView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        titleView.setTextColor(Color.rgb(102, 102, 102));
        titleView.setTypeface(YousResource.KOPUB_MID);
        titleLayout.addView(titleView);


        titleTextView = new YousTextView(getApplicationContext());
        titleTextView.setText("자 이제 " + title + "에 대한 너의 입장을 보여줘!");
        titleTextView.setLayoutParams(new YousParameter(428, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                        .addRules(RelativeLayout.BELOW, titleLayout.getId())
                                        .setMargin(0, 10)
        );
        titleTextView.setTextSize(50);
        titleTextView.setLineSpacing(0, 1.3f);
        titleTextView.setTextColor(Color.rgb(102, 102, 102));
        titleTextView.setTypeface(YousResource.KOPUB_LIGHT);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setId(titleTextView.hashCode());
        scrollView.addView(titleTextView);





        final ImageView later = new ImageView(getApplicationContext());
        later.setBackground(getResources().getDrawable(R.drawable.later));
        later.setLayoutParams(new YousParameter(750/2, 141)
                .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
        );
        later.setId(later.hashCode());
        main.addView(later);
        later.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    show_later();
                }

                return true;
            }
        });

        final ImageView confirm = new ImageView(getApplicationContext());
        confirm.setBackground(getResources().getDrawable(R.drawable.confirm));
        confirm.setLayoutParams(new YousParameter(750/2, 141)
                .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
                .addRules(RelativeLayout.RIGHT_OF, later.getId())
        );
        main.addView(confirm);
        confirm.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    scrolls.setClear();

                    main.removeView(confirm);
                    main.removeView(later);

                    ImageView another = new ImageView(getApplicationContext());
                    another.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 157)
                            .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
                            .addRules(RelativeLayout.CENTER_HORIZONTAL)
                    );
                    another.setBackground(getResources().getDrawable(R.drawable.another));
                    another.setOnTouchListener(new View.OnTouchListener() {
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
                    main.addView(another);


                }

                return true;
            }
        });

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(index));
    }

    public void show_later()
    {
        RelativeLayout block = new RelativeLayout(getApplicationContext());
        block.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        block.setBackgroundColor(Color.argb(216, 35, 31, 32));
        main.addView(block);
        block.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.btn_back_agree));
        back.setLayoutParams(new YousParameter(19,37).setMargin(65,0));
        block.addView(back);
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


        YousTextView text = new YousTextView(getApplicationContext());
        text.setTextSize(50);
        text.setText("알겠어,\n바로 답하긴\n어려운 이슈지..\n\n\n조금 더\n시간을 가지고\n생각해볼래\n나중에 알람을 줘도\n놀라지 않기~");
        text.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 200)
        );
        text.setLineSpacing(0, 1.3f);
        text.setTextColor(Color.WHITE);
        text.setGravity(Gravity.CENTER);
        block.addView(text);

        ImageView another = new ImageView(getApplicationContext());
        another.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 157)
                .addRules(RelativeLayout.ALIGN_PARENT_BOTTOM)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        another.setBackground(getResources().getDrawable(R.drawable.btn_later));
        another.setOnTouchListener(new View.OnTouchListener() {
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
        block.addView(another);
    }


    public class MyAsyncTask extends AsyncTask<String, Integer,String> {

        private JSONArray ja;
        private  int index;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                index = Integer.parseInt(params[0]);
                URL url = new URL("http://119.202.36.218/yous/php/get_searchType.php");

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


            try{
                JSONObject root = new JSONObject(str);
                ja = root.getJSONArray("result");

                String flag = ja.getJSONObject(index).getString("searchType");


                if(flag != null && flag.equals("scroll"))
                scrolls = new ScrollBox(getApplicationContext(), index);
                else if(flag != null && flag.equals("cross"))
                    scrolls = new CrossResearch(getApplicationContext(), index);
                scrolls.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, titleTextView.getId()));
                scrolls.setPadding(scroll_padding, scroll_padding, scroll_padding, scroll_padding);
                scrollView.addView(scrolls);


            }catch (JSONException e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Activity_Result.this, Activity_disagree.class)
                .putExtra("title", title)
                .putExtra("index", index)
                .putExtra("agree", title_agree)
                .putExtra("disagree", title_disagree));
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();
    }

}
