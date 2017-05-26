package com.lmasi.lmasi.yous;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 24..
 */

public class Activity_PopUp extends Activity {

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        main = (RelativeLayout)findViewById(R.id.main);

        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
        relativeLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(Color.argb(160, 0, 0, 0));
        main.addView(relativeLayout);

        YousImageView popUp = new YousImageView(getApplicationContext(), (int)(473 * 1.2), (int)(153 * 1.2), R.drawable.popup);
        popUp.addRules(RelativeLayout.CENTER_IN_PARENT);
        main.addView(popUp);

        Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {


                Activity_PopUp.this.finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 1000);
    }
}
