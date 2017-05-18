package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * Created by lmasi on 2017. 5. 12..
 */

public class Activity_Login extends Activity {

    private RelativeLayout main;

    ImageView infos;
    ImageView login;
    EditText email;
    EditText pwd;

    PHPDown phpDown;

    HashMap<String, String> passwd = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        //back btn
        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.back));
        back.setLayoutParams(new YousParameter(19,37).setMargin(65,65));
        main.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Login.this, Activity_home.class));
                    finish();
                }
                return true;
            }
        });


        //logo
        ImageView logo = new ImageView(getApplicationContext());
        logo.setBackground(getResources().getDrawable(R.drawable.logo_signup));
        logo.setLayoutParams(new YousParameter(116,31).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 72));
        main.addView(logo);


        //btn_getNumber
        login = new ImageView(getApplicationContext());
        login.setBackground(getResources().getDrawable(R.drawable.get_number));
        login.setLayoutParams(new YousParameter(527,85).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 713));
        main.addView(login);
        login.setOnTouchListener(new View.OnTouchListener() {

            boolean clicked = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean flag = true;

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if(flag)
                    {
                        try {
                            for (int i = 0; i < phpDown.getData().length(); i++) {
                                passwd.put(phpDown.getData().getJSONObject(i).getString("email"), phpDown.getData().getJSONObject(i).getString("pwd"));
                                flag = false;
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                    if(passwd.containsKey(email.getText().toString()) && passwd.get(email.getText().toString()).equals(pwd.getText().toString()))
                    {
                        startActivity(new Intent(Activity_Login.this, Activity_Main.class));
                        finish();
                    }
                }

                return true;
            }
        });
        login.setId(login.hashCode());


        //EditText
        email = new EditText(getApplicationContext());
        email.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 513, 0, 0)
        );
        email.setHint("이메일 주소");
        main.addView(email);

        pwd = new EditText(getApplicationContext());
        pwd.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 595, 0, 0)
        );
        pwd.setHint("비밀번호");
        main.addView(pwd);



        infos = new ImageView(getApplicationContext());
        infos.setBackground(getResources().getDrawable(R.drawable.infos));
        infos.setLayoutParams(new YousParameter(345,26).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM).setMargin(0,0,0,25));
        main.addView(infos);

        phpDown = new PHPDown();
        phpDown.execute("http://" + YousResource.URL + "/yous/get.php");

        passwd = new HashMap<String, String>();



    }
}
