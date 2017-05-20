package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
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

    YousTextView search_title;
    YousTextView search_text;
    DateBox recent_text;

    String title;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
        titleView.setTextColor(Color.parseColor("#666666"));
        titleView.setTypeface(YousResource.KOPUB_MID);
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
        search.setTextColor(Color.rgb(102, 102, 102));
        scrollView.addView(search);

        ImageView line1 = new ImageView(getApplicationContext());
        line1.setBackground(getResources().getDrawable(R.drawable.content_line));
        line1.setLayoutParams(new YousParameter(1, 180)
                                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                .addRules(RelativeLayout.BELOW, search.getId())
                                .setMargin(0, 40)
                            );
        line1.setId(line1.hashCode());
        scrollView.addView(line1);

        RelativeLayout content_box = new RelativeLayout(getApplicationContext());
        content_box.setBackgroundColor(Color.rgb(242, 242, 242));
        content_box.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                    .addRules(RelativeLayout.BELOW, line1.getId())
                                    .setMargin(0, 48)
        );
        int margin = 56;
        content_box.setPadding(margin, margin, margin, margin);
        content_box.setId(content_box.hashCode());
        scrollView.addView(content_box);

        RelativeLayout searchd = new RelativeLayout(getApplicationContext());
        searchd.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL));
        content_box.addView(searchd);

        search_title = new YousTextView(getApplicationContext());
        search_title.setTextSize(32);
        search_title.setLayoutParams(new YousParameter(600, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setMargin(0, 0, 0, 0) // 위아래옆 패딩 동일하게!
        );
        search_title.setId(search_title.hashCode());
        search_title.setTypeface(YousResource.KOPUB_MID);
        search_title.setTextColor(Color.rgb(102, 102, 102));
        searchd.addView(search_title);
        new Thread()
        {
            private StringBuilder builder;

            @Override
            public void run() {

                try {
                    builder = new StringBuilder("");
                    URL url = new URL("http://119.202.36.218/yous/content/texts/" + index + "/title");
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

                String text = builder.toString();
                text = replaceLast(text, "\n", "");
                search_title.setTextWithHandler(text);
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

        }.start();

        search_text = new YousTextView(getApplicationContext());
        search_text.setTextSize(28);
        search_text.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, search_title.getId())
                .setMargin(0, 28, 0, 0)
        );
        search_text.setId(search_text.hashCode());
        search_text.setLineSpacing(0, 1.4f);
        search_text.setTextColor(Color.rgb(102, 102, 102));
        searchd.addView(search_text);
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

                String text = builder.toString();
                text = replaceLast(text, "\n", "");
                search_text.setTextWithHandler(text);
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

        final YousTextView recent = new YousTextView(getApplicationContext());
        recent.setText("정황을 간단히 보자면");
        recent.setTextSize(32);
        recent.setTypeface(YousResource.KOPUB_MID);
        recent.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, line2.getId())
                .setMargin(0, 55)
        );
        recent.setId(recent.hashCode());
        recent.setTextColor(Color.parseColor("#666666"));
        scrollView.addView(recent);

        recent_text = new DateBox(getApplicationContext(), index);
       // recent_text.setTextSize(27);
        recent_text.setLayoutParams(new YousParameter(600, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
                .addRules(RelativeLayout.BELOW, recent.getId())
                .setMargin(0, 50)
        );
      //  recent_text.setGravity(Gravity.CENTER);
     //   recent_text.setTextColor(Color.rgb(102, 102, 102));
        recent_text.setId(recent_text.hashCode());
     //   recent_text.setLineSpacing(0, 1.45f);
        scrollView.addView(recent_text);

        ImageView btn_ok = new ImageView(getApplicationContext());  //title과 사이즈 동일하게
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Activity_Main.class));
        finish();
    }
}

