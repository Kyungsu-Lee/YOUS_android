package com.lmasi.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Activity_home extends Activity {

    RelativeLayout main;

    ImageView log_in;
    ImageView sign_in;

    NetFileReader nf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);


        log_in = new ImageView(getApplicationContext());
        log_in.setBackground(getResources().getDrawable(R.drawable.log_in));
        log_in.setLayoutParams(new YousParameter(527,85).setMargin(0,555,0,0).addRules(RelativeLayout.CENTER_HORIZONTAL));
        main.addView(log_in);
        log_in.setId(log_in.hashCode());
        log_in.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_home.this, Activity_Login.class));
                    finish();
                }

                return true;
            }
        });

        sign_in = new ImageView(getApplicationContext());
        sign_in.setBackground(getResources().getDrawable(R.drawable.sign_up));
        sign_in.setLayoutParams(new YousParameter(527,85).setMargin(0,22,0,0)
                                    .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                    .addRules(RelativeLayout.BELOW, log_in.getId())
                                );
        main.addView(sign_in);
        sign_in.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_home.this, Activity_SignUp.class));
                    finish();
                }

                return true;
            }
        });
    }
}
