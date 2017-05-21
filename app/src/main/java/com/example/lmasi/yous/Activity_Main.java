package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class Activity_Main extends Activity {

    RelativeLayout Rmain;

    ImageView menu;
    ImageView logo;
    ImageView search;

    PHPDown phpDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Rmain = (RelativeLayout)findViewById(R.id.main);
        Rmain.setBackgroundColor(Color.WHITE);

        final yScrollView main = new yScrollView(getApplicationContext());
        main.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        ContentBox contentBox = new ContentBox(getApplicationContext()){
            @Override
            public void clickAction() {
                finish();
            }
        };
        contentBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        main.addView(contentBox);


        Log.e("ID ::: ", DbResource.get_cId());


 /*       ImageView card = new ImageView(getApplicationContext());
        card.setBackground(getResources().getDrawable(R.drawable.test1));
        card.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400)
                        .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        card.setId(card.hashCode());
        main.addView(card);
        card.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Main.this, Activity_Summary.class).putExtra("title", "노홍철 무한도전 복귀").putExtra("index", 0));
                    finish();
                }

               return true;
            }
        });


        //CardView card2 = new CardView(getApplicationContext(), "img2.png", "노홍철", "찬성-반대");
        ImageView card2 = new ImageView(getApplicationContext());
        card2.setBackground(getResources().getDrawable(R.drawable.test2));
        card2.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, card.getId())
        );
        card2.setId(card2.hashCode());
        main.addView(card2);
        card2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Main.this, Activity_Summary.class).putExtra("title", "혼전동거").putExtra("index", 1));
                    finish();
                }

                return true;
            }
        });

        //CardView card2 = new CardView(getApplicationContext(), "img2.png", "노홍철", "찬성-반대");
        ImageView card3 = new ImageView(getApplicationContext());
        card3.setBackground(getResources().getDrawable(R.drawable.test3));
        card3.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, card2.getId())
        );
        card3.setId(card3.hashCode());
        main.addView(card3);
        card3.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Main.this, Activity_Summary.class).putExtra("title", "혼전동거"));
                    finish();
                }

                return true;
            }
        });


        //CardView card2 = new CardView(getApplicationContext(), "img2.png", "노홍철", "찬성-반대");
        ImageView card4 = new ImageView(getApplicationContext());
        card4.setBackground(getResources().getDrawable(R.drawable.test4));
        card4.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, card3.getId())
        );
        card4.setId(card4.hashCode());
        main.addView(card4);
        card4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Main.this, Activity_Summary.class).putExtra("title", "혼전동거"));
                    finish();
                }

                return true;
            }
        });
        */


        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        main.addView(titleLayout);


        menu = new ImageView(getApplicationContext());
        menu.setBackground(getResources().getDrawable(R.drawable.menu_main));
        menu.setLayoutParams(new YousParameter(38,20).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(65,0));
        titleLayout.addView(menu);
        menu.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout relativeLayout;
            ImageView menu_box;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                   relativeLayout = new RelativeLayout(getApplicationContext());
                    relativeLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    relativeLayout.setBackgroundColor(Color.argb(160, 0, 0, 0));
                    Rmain.addView(relativeLayout);
                    relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            if(event.getAction() == MotionEvent.ACTION_UP){
                                Rmain.removeView(relativeLayout);
                                relativeLayout.removeView(menu_box);
                            }

                            return true;
                        }
                    });

                    menu_box = new ImageView(getApplicationContext());
                    menu_box.setBackground(getResources().getDrawable(R.drawable.menu_box));
                    menu_box.setLayoutParams(new YousParameter(300, ViewGroup.LayoutParams.MATCH_PARENT));
                    relativeLayout.addView(menu_box);
                }

                return true;
            }
        });

        logo = new ImageView(getApplicationContext());
        logo.setBackground(getResources().getDrawable(R.drawable.logo_main));
        logo.setLayoutParams(new YousParameter(90, 23).addRules(RelativeLayout.CENTER_IN_PARENT).setMargin(65,0));
        titleLayout.addView(logo);

        search = new ImageView(getApplicationContext());
        search.setBackground(getResources().getDrawable(R.drawable.search_main));
        search.setLayoutParams(new YousParameter(32,33).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT).setMargin(0, 0, 65, 0));
        titleLayout.addView(search);
        titleLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {

                }

                return  true;
            }
        });

        Rmain.addView(main);
    }
}
