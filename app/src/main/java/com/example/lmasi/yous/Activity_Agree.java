package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class Activity_Agree extends Activity {

    RelativeLayout main;
    String title;
    int index;
    String title_agree;
    String title_disagree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_agree);

        title = getIntent().getStringExtra("title");
        index = getIntent().getIntExtra("index", 0);
        title_agree = getIntent().getStringExtra("agree");
        title_disagree = getIntent().getStringExtra("disagree");

      //  title = "贊";

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.rgb(255, 255,255));

        yScrollView scrollView = new yScrollView(getApplicationContext());
        scrollView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        RelativeLayout title_layout = new RelativeLayout(getApplicationContext());
        title_layout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 420));
        title_layout.setId(title_layout.hashCode());
        scrollView.addView(title_layout);

        ImageView title_img = new ImageView(getApplicationContext());
        title_img.setImageBitmap(new ImageRoader().getBitmapImg("/yous/content/img/yous/"+ index + "/agree.png"));
        title_img.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        title_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        title_layout.addView(title_img);

        RelativeLayout title_box = new RelativeLayout(getApplicationContext());
        title_box.setBackgroundColor(Color.argb(255/2, 0, 113, 188));
        title_box.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        title_layout.addView(title_box);
        title_layout.setId(title_layout.hashCode());


        YousTextView titleTextView = new YousTextView(getApplicationContext());
        titleTextView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        titleTextView.setTextSize(125);
        titleTextView.setTypeface(YousResource.KOPUB_MID);
        titleTextView.setTextColor(Color.parseColor("#F2F2F2"));
        title_layout.addView(titleTextView);
        titleTextView.setText(title_agree);

        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        scrollView.addView(titleLayout);

        //back btn
        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.btn_white_full));
        back.setLayoutParams(new YousParameter(19,37).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(65,0));
        titleLayout.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Agree.this, Activity_Summary.class)
                            .putExtra("title", title)
                            .putExtra("index", index)
                            .putExtra("agree", title_agree)
                            .putExtra("disagree", title_disagree)
                    );
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
        titleView.setTextColor(Color.parseColor("#F2F2F2"));
        titleView.setTypeface(YousResource.KOPUB_MID);
        titleLayout.addView(titleView);



        RelativeLayout cover = new RelativeLayout(getApplicationContext());
        cover.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        ImageView btn_disagree = new ImageView(getApplicationContext());
        btn_disagree.setLayoutParams(new YousParameter(185, 185)
                                        .setMargin(750 - 185/2, 1333 * 2/ 4 + 100, -1000, -1000)
        );
        btn_disagree.setBackground(getResources().getDrawable(R.drawable.disagree));
        cover.addView(btn_disagree);
        btn_disagree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Agree.this, Activity_disagree.class)
                            .putExtra("title", title)
                            .putExtra("index", index)
                            .putExtra("agree", title_agree)
                            .putExtra("disagree", title_disagree));
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                    finish();
                }

                return true;
            }
        });


 /*       ImageView image = new ImageView(getApplicationContext());
        image.setBackground(getResources().getDrawable(R.drawable.agreetest));
        image.setLayoutParams(new YousParameter(750, 1922).addRules(RelativeLayout.BELOW, title_layout.getId()));
        scrollView.addView(image);
*/

        LogicBox logicBox = new LogicBox(getApplicationContext(), index, true);
        logicBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.BELOW, title_layout.getId())
        );
        logicBox.setId(logicBox.hashCode());
        scrollView.addView(logicBox);

        ReferenceBox referenceBox = new ReferenceBox(getApplicationContext(), index, true);
        referenceBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.BELOW, logicBox.getId())
        );
        referenceBox.setId(referenceBox.hashCode());
        scrollView.addView(referenceBox);

        main.addView(scrollView);
        main.addView(cover);

    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(this, Activity_Summary.class).putExtra("title", title).putExtra("index", index));
            finish();
    }
}
