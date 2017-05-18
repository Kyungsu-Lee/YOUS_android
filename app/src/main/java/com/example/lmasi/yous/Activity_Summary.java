package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class Activity_Summary extends Activity {

    RelativeLayout main;

    YousTextView search_text;
    YousTextView recent_text;

    String title;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_summary);

        title = "" + getIntent().getStringExtra("title");
        index = getIntent().getIntExtra("index", 0);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.rgb(255, 255,255));

        yScrollView scrollView = new yScrollView(getApplicationContext());
        scrollView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
                    startActivity(new Intent(Activity_Summary.this, Activity_Main.class));
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
        titleView.setTextColor(yColor.yrgb(66, 66, 66));
        titleLayout.addView(titleView);

        YousTextView search = new YousTextView(getApplicationContext());
        search.setText("찬찬히 살펴가볼까?");
        search.setTextSize(50);
        search.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, titleLayout.getId())
                .setMargin(0, 80)
        );
        search.setId(search.hashCode());
        search.setTextColor(yColor.yrgb(66, 66, 66));
        scrollView.addView(search);

        ImageView line1 = new ImageView(getApplicationContext());
        line1.setBackground(getResources().getDrawable(R.drawable.content_line));
        line1.setLayoutParams(new YousParameter(1, 128)
                                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                .addRules(RelativeLayout.BELOW, search.getId())
                                .setMargin(0, 48)
                            );
        line1.setId(line1.hashCode());
        scrollView.addView(line1);

        RelativeLayout content_box = new RelativeLayout(getApplicationContext());
        content_box.setBackgroundColor(Color.rgb(242, 242, 242));
        content_box.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.BELOW, line1.getId())
                                    .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                    .setMargin(0, 48)
        );
        int margin = 50;
        content_box.setPadding(margin, margin, margin, margin);
        content_box.setId(content_box.hashCode());
        scrollView.addView(content_box);

        search_text = new YousTextView(getApplicationContext());
        search_text.setTextSize(30);
        search_text.setLayoutParams(new YousParameter(600, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        search_text.setId(search_text.hashCode());
        search_text.setTextColor(Color.rgb(102, 102, 102));
        content_box.addView(search_text);
        new Thread()
        {
            private StringBuilder builder;

            @Override
            public void run() {

                try {
                    builder = new StringBuilder("");
                    URL url = new URL("http://119.202.36.218/yous/content/texts/" + index + "/search");
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    String str;
                    while((str = br.readLine()) != null)
                    {
                        builder.append(str + "\n");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                search_text.setTextWithHandler(builder.toString());
            }
        }.start();

        ImageView line2 = new ImageView(getApplicationContext());
        line2.setBackground(getResources().getDrawable(R.drawable.content_line));
        line2.setLayoutParams(new YousParameter(1, 128)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, content_box.getId())
                .setMargin(0, 48)
        );
        line2.setId(line2.hashCode());
        scrollView.addView(line2);

        YousTextView recent = new YousTextView(getApplicationContext());
        recent.setText("정황을 간단히 보자면");
        recent.setTextSize(50);
        recent.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, line2.getId())
                .setMargin(0, 80)
        );
        recent.setId(recent.hashCode());
        recent.setTextColor(yColor.yrgb(66, 66, 66));
        scrollView.addView(recent);

        recent_text = new YousTextView(getApplicationContext());
        recent_text.setTextSize(30);
        recent_text.setLayoutParams(new YousParameter(600, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
                .addRules(RelativeLayout.BELOW, recent.getId())
                .setMargin(0, 40)
        );
        recent_text.setGravity(Gravity.CENTER);
        recent_text.setTextColor(Color.rgb(102, 102, 102));
        recent_text.setId(recent_text.hashCode());
        scrollView.addView(recent_text);
        new Thread()
        {
            private StringBuilder builder;

            @Override
            public void run() {

                try {
                    builder = new StringBuilder("");
                    URL url = new URL("http://119.202.36.218/yous/content/texts/" + index + "/recent");
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    String str;
                    while((str = br.readLine()) != null)
                    {
                        builder.append(str + "\n");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                recent_text.setTextWithHandler(builder.toString());
            }
        }.start();

        ImageView btn_ok = new ImageView(getApplicationContext());
        btn_ok.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 157)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, recent_text.getId())
                .setMargin(0, 80)
        );
        btn_ok.setBackground(getResources().getDrawable(R.drawable.btn_ok));
        btn_ok.setId(btn_ok.getId());
        scrollView.addView(btn_ok);
        btn_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Summary.this, Activity_Agree.class).putExtra("title", title).putExtra("index", index));
                    finish();
                }

                return true;
            }
        });





        main.addView(scrollView);
    }
}
