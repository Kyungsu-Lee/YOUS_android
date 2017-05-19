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

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

/**
 * Created by lmasi on 2017. 5. 12..
 */

public class Activity_Login extends Activity {

    private RelativeLayout main;

    private CallbackManager callbackManager;

    ImageView infos;
    ImageView login;
    ImageView facebook;
    EditText email;
    EditText pwd;

    PHPDown phpDown;

    HashMap<String, String> passwd = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
                        SystemManager.setIsLogged(true);
                        SystemManager.setUserId(email.getText().toString());
                        startActivity(new Intent(Activity_Login.this, Activity_Main.class));
                        finish();
                    }
                }

                return true;
            }
        });
        login.setId(login.hashCode());

        facebook = new ImageView(getApplicationContext());
        facebook.setBackground(getResources().getDrawable(R.drawable.login_facebook));
        facebook.setLayoutParams(new YousParameter(527,85).addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, login.getId())
                .setMargin(0, 20));
        main.addView(facebook);
        facebook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    FacebookSdk.sdkInitialize(getApplicationContext());
                    callbackManager = CallbackManager.Factory.create();

                    LoginManager.getInstance().logInWithReadPermissions(Activity_Login.this,
                            Arrays.asList("public_profile", "email", "user_status"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                        @Override
                        public void onSuccess(final LoginResult result) {

                            GraphRequest request;
                            request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject user, GraphResponse response) {
                                    if (response.getError() != null) {

                                    } else {
                                        Log.i("TAG", "user: " + user.toString());
                                        Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                                        Log.i("TAG", "AccessToken: " + result.getAccessToken().getUserId());
                                        setResult(RESULT_OK);
                                    }
                                }
                            });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender,birthday");
                            request.setParameters(parameters);
                            request.executeAsync();



                            startActivity(new Intent(Activity_Login.this, Activity_Main.class));
                            finish();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.e("test", "Error: " + error);
                            //finish();
                        }

                        @Override
                        public void onCancel() {
                            //finish();
                        }
                    });

                }

                return true;

            }
        });


        //EditText
        email = new EditText(getApplicationContext());
        email.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 513, 0, 0)
        );
        email.setHint("이메일 주소");
        email.setHintTextColor(Color.argb(102, 102, 102, 102));
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
