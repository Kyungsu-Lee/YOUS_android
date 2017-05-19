package com.example.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import me.srodrigo.androidhintspinner.HintAdapter;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class Activity_SignUp2 extends Activity {

    private CallbackManager callbackManager;

    RelativeLayout main;

    EditText name;
    EditText pwd;
    EditText email;

    String birth;

    Spinner sp;
    YousSpinner<String> sp_year;
    YousSpinner<String> sp_month;
    YousSpinner<String> sp_day;

    int textSize = 23;

    PHPDown phpDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);



        //back btn
        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.back));
        back.setLayoutParams(new YousParameter(19,37).setMargin(65,65));
        main.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_SignUp2.this, Activity_SignUp.class));
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


        ImageView getNumber = new ImageView(getApplicationContext());
        getNumber.setBackground(getResources().getDrawable(R.drawable.get_number));
        getNumber.setLayoutParams(new YousParameter(527,85)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .setMargin(0, 746)
        );
        getNumber.setId(getNumber.hashCode());
        main.addView(getNumber);
        getNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    try
                    {
                        HashSet<String> set = new HashSet<String>();

                        for(int i=0; i<phpDown.getData().length(); i++)
                        {
                            set.add(phpDown.getData().getJSONObject(i).getString("email"));
                        }

                        if (set.contains(email.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(), "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    birth = sp_year.getSelectedItem().toString() + sp_month.getSelectedItem().toString() + sp_day.getSelectedItem().toString();
                    insertToDatabase(email.getText().toString(), pwd.getText().toString(), name.getText().toString(), birth, sp.getSelectedItem().toString());
                    finish();
                    startActivity(new Intent(Activity_SignUp2.this, Activity_Main.class));
                }

                return true;

            }
        });

        ImageView facebook = new ImageView(getApplicationContext());
        facebook.setBackground(getResources().getDrawable(R.drawable.sign_in_facebook));
        facebook.setLayoutParams(new YousParameter(527,85)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, getNumber.getId())
                .setMargin(0, 30)
        );
        facebook.setId(facebook.hashCode());
        main.addView(facebook);
        facebook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    FacebookSdk.sdkInitialize(getApplicationContext());
                    callbackManager = CallbackManager.Factory.create();

                    LoginManager.getInstance().logInWithReadPermissions(Activity_SignUp2.this,
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

                            startActivity(new Intent(Activity_SignUp2.this, Activity_Main.class));
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




        ArrayList<String> arr = new ArrayList<>();
        arr.add("남");
        arr.add("여");


        sp = new Spinner(getApplicationContext());
        sp.setLayoutParams(new YousParameter(170, 100)
                                .addRules(RelativeLayout.ALIGN_LEFT, getNumber.getId())
                                .addRules(RelativeLayout.ALIGN_TOP, getNumber.getId())
                                .setMargin(-25, -30-85, 0, 0)

        );
        main.addView(sp);
        sp.setGravity(Gravity.RIGHT);
        sp.setPadding((int)(50 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        sp.setId(sp.hashCode());

        YousHintSpinner<String> hintSpinner = new YousHintSpinner<>(
                sp,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<String>(getApplicationContext(), "성별", arr),
                new YousHintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        hintSpinner.setTextColor(Color.rgb(128,128,128));
        hintSpinner.setTextSize(textSize);
        hintSpinner.init();


        YousTextView tv = new YousTextView(getApplicationContext());
        tv.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                .addRules(RelativeLayout.RIGHT_OF, sp.getId())
                                .addRules(RelativeLayout.ALIGN_TOP, sp.getId())
                                .setMargin(10, 35, 0, 0)

        );
        tv.setId(tv.hashCode());
        tv.setTextColor(Color.rgb(100, 100, 100));
        tv.setText("생일");
        tv.setTextSize(textSize);
        main.addView(tv);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ArrayList<String> arr_year = new ArrayList<>();
        for(int i=1950; i <= 2017; i++)
            arr_year.add(Integer.toString(i));


        sp_year = new YousSpinner<String>(getApplicationContext(), arr_year) {
            @Override
            public void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelectedListener(AdapterView<?> parent) {

            }
        };
        sp_year.setLayoutParams(new YousParameter(170, 100)
                .addRules(RelativeLayout.RIGHT_OF, tv.getId())
                .addRules(RelativeLayout.ALIGN_TOP, sp.getId())
                .setMargin(5-30, 0, 0, 0)
        );
        sp_year.setPadding((int)(50*ScreenParameter.getScreenparam_x()), 0, 0, 0);
        main.addView(sp_year);
        sp_year.setId(sp_year.hashCode());

        YousHintSpinner<String> hintSpinner_year = new YousHintSpinner<>(
                sp_year,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<String>(getApplicationContext(), "연도", arr_year),
                new YousHintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        hintSpinner_year.setTextColor(Color.rgb(128,128,128));
        hintSpinner_year.setTextSize(textSize);
        hintSpinner_year.init();


        ArrayList<String> arr_month = new ArrayList<>();
        for(int i=1; i <= 12; i++) {
            if(i<10) arr_month.add("0" + i);
            else arr_month.add(Integer.toString(i));
        }


        sp_month = new YousSpinner<String>(getApplicationContext(), arr_month) {
            @Override
            public void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelectedListener(AdapterView<?> parent) {

            }
        };
        sp_month.setLayoutParams(new YousParameter(110, 100)
                .addRules(RelativeLayout.RIGHT_OF, sp_year.getId())
                .addRules(RelativeLayout.ALIGN_TOP, sp.getId())
                .setMargin(5-30, 0, 0, 0)
        );
        sp_month.setPadding((int)(10*ScreenParameter.getScreenparam_x()), 0, 0, 0);
        main.addView(sp_month);
        sp_month.setId(sp_month.hashCode());

        YousHintSpinner<String> hintSpinner_month = new YousHintSpinner<>(
                sp_month,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<String>(getApplicationContext(), "월", arr_month),
                new YousHintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        hintSpinner_month.setTextColor(Color.rgb(128,128,128));
        hintSpinner_month.setTextSize(textSize);
        hintSpinner_month.init();

        ArrayList<String> arr_day = new ArrayList<>();
        for(int i=1; i <= 31; i++) {
            if(i<10)    arr_day.add("0" + i);
            else arr_day.add(Integer.toString(i));
        }


        sp_day = new YousSpinner<String>(getApplicationContext(), arr_day) {
            @Override
            public void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelectedListener(AdapterView<?> parent) {

            }
        };
        sp_day.setLayoutParams(new YousParameter(110, 100)
                .addRules(RelativeLayout.RIGHT_OF, sp_month.getId())
                .addRules(RelativeLayout.ALIGN_TOP, sp.getId())
                .setMargin(5-30, 0, 0, 0)
        );
        sp_day.setPadding((int)(10*ScreenParameter.getScreenparam_x()), 0, 0, 0);
        main.addView(sp_day);
        sp_day.setId(sp_day.hashCode());

        YousHintSpinner<String> hintSpinner_day = new YousHintSpinner<>(
                sp_day,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<String>(getApplicationContext(), "일", arr_day),
                new YousHintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected event)
                    }
                });
        hintSpinner_day.setTextColor(Color.rgb(128,128,128));
        hintSpinner_day.setTextSize(textSize);
        hintSpinner_day.init();

        sp_year.bringToFront();
        tv.bringToFront();



        int margin = 90;
        int padding = 20;

        //EditText
        name = new EditText(getApplicationContext());
        name.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.ALIGN_TOP, sp.getId())
                .setMargin(0, -margin, 0, 0)
        );
        name.setHint("이름");
        name.setId(name.hashCode());
        name.setPadding((int)(padding * ScreenParameter.getScreenparam_x()), name.getPaddingTop(), name.getPaddingRight(), name.getPaddingBottom());
        main.addView(name);

        //EditText
        pwd = new EditText(getApplicationContext());
        pwd.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.ALIGN_TOP, name.getId())
                .setMargin(0, -margin, 0, 0)
        );
        pwd.setHint("비밀번호");
        pwd.setId(pwd.hashCode());
        pwd.setPadding((int)(padding * ScreenParameter.getScreenparam_x()), pwd.getPaddingTop(), pwd.getPaddingRight(), pwd.getPaddingBottom());
        main.addView(pwd);

        //EditText
        email = new EditText(getApplicationContext());
        email.setLayoutParams(new   YousParameter(527, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.ALIGN_TOP, pwd.getId())
                .setMargin(0, -margin, 0, 0)
        );
        email.setHint("이메일 주소");
        email.setId(email.hashCode());
        email.setPadding((int)(padding * ScreenParameter.getScreenparam_x()), email.getPaddingTop(), email.getPaddingRight(), email.getPaddingBottom());
        main.addView(email);

        main.setFocusable(true);
        main.setFocusableInTouchMode(true);


        phpDown = new PHPDown();
        phpDown.execute("http://" + YousResource.URL + "/yous/get.php");


    }


    private void insertToDatabase(String email, String pwd, String name, final String birth, final String gender){

        PHPUP task = new PHPUP();
        task.execute(email, pwd, name,birth, gender);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
