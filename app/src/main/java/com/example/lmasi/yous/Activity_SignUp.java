package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class Activity_SignUp extends Activity {

    RelativeLayout main;
    EditText etext;

    ImageView getNumber;
    ImageView resend;
    ImageView infos;

    int sentNumber;
    String tmp_ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);


        sentNumber =randomRange(100000, 999999);
        Log.d("send number", Integer.toString(sentNumber));

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
                    startActivity(new Intent(Activity_SignUp.this, Activity_home.class));
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
        getNumber = new ImageView(getApplicationContext());
        getNumber.setBackground(getResources().getDrawable(R.drawable.get_number));
        getNumber.setLayoutParams(new YousParameter(478,85).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 676.5));
        main.addView(getNumber);
        getNumber.setOnTouchListener(new View.OnTouchListener() {

            boolean clicked = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    if((!clicked) && !checkPhoneNumber(etext.getText().toString()))
                    {
                            etext.setText("");
                            return true;
                    }

                    else if(!clicked) {

                        SMSManager sm = new SMSManager(getApplicationContext());
                        tmp_ph = etext.getText().toString();
                        sm.sendSMS(tmp_ph, "인증번호는 " +Integer.toString(sentNumber) +" 입니다");

                        main.addView(infos);
                        main.addView(resend);
                        etext.setHint("인증번호");
                        etext.setText("");

                        clicked = !clicked;
                    }

                    else
                    {
                        if(etext.getText().toString().equals(Integer.toString(sentNumber)))
                        {
                            startActivity(new Intent(Activity_SignUp.this, Activity_SignUp2.class));
                            finish();
                        }
                        else
                            etext.setText("");
                    }
                }

                return true;
            }
        });
        getNumber.setId(getNumber.hashCode());


        //EditText
        etext = new EditText(getApplicationContext());
        etext.setLayoutParams(new   YousParameter(479, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 553, 0, 0)
        );
        etext.setHint("휴대전화번호");
        main.addView(etext);


        //texts
        resend = new ImageView(getApplicationContext());
        resend.setBackground(getResources().getDrawable(R.drawable.resend));
        resend.setLayoutParams(new YousParameter(209,28).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 815));
        resend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    sentNumber = randomRange(100000, 999999);
                    SMSManager sm = new SMSManager(getApplicationContext());
                    sm.sendSMS(tmp_ph, "인증번호는 " +Integer.toString(sentNumber) +" 입니다");
                }

                return true;
            }
        });

        infos = new ImageView(getApplicationContext());
        infos.setBackground(getResources().getDrawable(R.drawable.infos));
        infos.setLayoutParams(new YousParameter(345,25).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM).setMargin(0,0,0,25));
    }

    private boolean checkPhoneNumber(String number)
    {
        try
        {
            Integer.parseInt(number);
        }
        catch (Exception e)
        {
            return false;
        }

        return number.length() == 11 && number.substring(0,3).equals("010");
    }

    public static int randomRange(int n1, int n2) {
        return (int) (Math.random() * (n2 - n1 + 1)) + n1;
    }


    public String getPhoneNumber() {
        TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber ="";

        try {
            if (telephony.getLine1Number() != null) {
                phoneNumber = telephony.getLine1Number();
            }
            else {
                if (telephony.getSimSerialNumber() != null) {
                    phoneNumber = telephony.getSimSerialNumber();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return phoneNumber;
    }
}
