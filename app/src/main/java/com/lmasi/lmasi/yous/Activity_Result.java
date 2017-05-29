package com.lmasi.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        title_agree = getIntent().getStringExtra("agree");
        title_disagree = getIntent().getStringExtra("disagree");

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
        back.setBackground(getResources().getDrawable(R.drawable.btn_back_black));
        back.setLayoutParams(new YousParameter(110,109).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(15,0));
        titleLayout.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Result.this, Activity_disagree.class)
                            .putExtra("title", title)
                            .putExtra("index", index)
                            .putExtra("agree", title_agree)
                            .putExtra("disagree", title_disagree));
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_in_right);
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
        titleTextView.setText("자 이제,\n" + title + "에 대한 너의 \n입장을 보여줘!");
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





        final RelativeLayout later = new RelativeLayout(getApplicationContext());
        later.setBackground(getResources().getDrawable(R.drawable.btn_later));
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

        YousTextView laterTv = new YousTextView(getApplicationContext());
        laterTv.setText("좀 이따가");
        laterTv.setLayoutParams(new YousParameter(428, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        laterTv.setTextSize(50);
        laterTv.setLineSpacing(0, 1.3f);
        laterTv.setTextColor(Color.rgb(102, 102, 102));
        laterTv.setTypeface(YousResource.KOPUB_LIGHT);
        laterTv.setGravity(Gravity.CENTER);
        laterTv.setId(laterTv.hashCode());
        later.addView(laterTv);



        final RelativeLayout confirm = new RelativeLayout(getApplicationContext());
        confirm.setBackground(getResources().getDrawable(R.drawable.btn_confirm_final));
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

        YousTextView confirmTv = new YousTextView(getApplicationContext());
        confirmTv.setText("확정했어");
        confirmTv.setLayoutParams(new YousParameter(428, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        confirmTv.setTextSize(50);
        confirmTv.setLineSpacing(0, 1.3f);
        confirmTv.setTextColor(Color.rgb(102, 102, 102));
        confirmTv.setTypeface(YousResource.KOPUB_LIGHT);
        confirmTv.setGravity(Gravity.CENTER);
        confirmTv.setId(laterTv.hashCode());
        confirm.addView(confirmTv);


        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(index));
    }

    public void show_later()
    {
        RelativeLayout block = new RelativeLayout(getApplicationContext());
        block.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        block.setBackgroundColor(Color.argb(180, 35, 31, 32));
        main.addView(block);
        block.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        block.addView(titleLayout);

        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.btn_back_white));
        back.setLayoutParams(new YousParameter(110,109).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(15,0));
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

        RelativeLayout another = new RelativeLayout(getApplicationContext());
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

        YousTextView tt = new YousTextView(getApplicationContext());
        tt.setTextSize(50);
        tt.setText("그래, 나중에 동참할게!");
        tt.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        tt.setLineSpacing(0, 1.3f);
        tt.setTextColor(Color.WHITE);
        tt.setGravity(Gravity.CENTER);
        another.addView(tt);
    }


    public class MyAsyncTask extends AsyncTask<String, Integer,String> {

        private JSONArray ja;
        private  int index;

        ArrayList<String> result_titles = new ArrayList<>();

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

            try{

                index = Integer.parseInt(params[0]);

                URL url_title = new URL("http://119.202.36.218/yous/result_title");
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

                            result_titles.add(line.replaceAll(":::", "\n"));
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

                titleTextView.setText(result_titles.get(index));

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
        overridePendingTransition(R.anim.slide_right, R.anim.slide_in_right);
        finish();
    }

}
