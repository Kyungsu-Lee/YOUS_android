package com.example.lmasi.yous;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.Profile;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class Activity_Start extends Activity {

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        ScreenParameter.setScreenparam_x(ScreenSize().x / ScreenParameter.getDefaultsizeX());
        ScreenParameter.setScreenparam_y(ScreenSize().y / ScreenParameter.getDefaultsizeY());
        ScreenParameter.setScreen_x(ScreenSize().x);
        ScreenParameter.setScreen_y(ScreenSize().y);

        YousResource.KOPUB_LIGHT = Typeface.createFromAsset(getAssets(), "font/KoPubDotum_Pro Light.otf");
        YousResource.KOPUB_MID = Typeface.createFromAsset(getAssets(), "font/KoPubDotum_Pro Medium.otf");

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        ImageView logo = new ImageView(getApplicationContext());
        logo.setBackground(getResources().getDrawable(R.drawable.logo));
        logo.setLayoutParams(new YousParameter(131, 140).addRules(RelativeLayout.CENTER_IN_PARENT));
        main.addView(logo);



        if(
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                        &&ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                )
        {
            toNextActivity();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new
                    String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.INTERNET
            }, 0);
        }


    }

    private void toNextActivity()
    {
        Handler hd = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {

                if(Profile.getCurrentProfile() != null)
                {
                    startActivity(new Intent(Activity_Start.this, Activity_Main.class));
                    finish();
                    return;
                }

                startActivity(new Intent(Activity_Start.this, Activity_home.class));
               // startActivity(new Intent(Activity_Start.this, Activity_SignUp2.class));
                finish();
            }

        };
        hd.sendEmptyMessageDelayed(0, 1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        //위 예시에서 requestPermission 메서드를 썼을시 , 마지막 매개변수에 0을 넣어 줬으므로, 매칭
        if(requestCode == 0){
            // requestPermission의 두번째 매개변수는 배열이므로 아이템이 여러개 있을 수 있기 때문에 결과를 배열로 받는다.
            // 해당 예시는 요청 퍼미션이 한개 이므로 i=0 만 호출한다.
            if(grantResult[0] == 0){
               toNextActivity();
            }else{
                //해당 권한이 거절된 경우.
            }
        }
    }



    public Point ScreenSize() { //현재 스크린의 사이즈를 가져오는 메서드. 정형화된 틀이다.

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Point size = new Point(width, height);

        return size;

    }
}
