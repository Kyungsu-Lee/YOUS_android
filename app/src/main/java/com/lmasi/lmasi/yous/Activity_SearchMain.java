package com.lmasi.lmasi.yous;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 23..
 */

public class Activity_SearchMain extends Activity {

    RelativeLayout main;

    ImageView back;
    ImageView logo;

    YousEditText editText;

    ContentBox contentBox;

    yScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmain);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        main.setFocusable(true);
        main.setFocusableInTouchMode(true);




        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        main.addView(titleLayout);

        ImageView back = new ImageView(getApplicationContext());
        back.setBackground(getResources().getDrawable(R.drawable.btn_back_black));
        back.setLayoutParams(new YousParameter(110,109).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(15,0));
        titleLayout.addView(back);
        back.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout relativeLayout;
            ImageView menu_box;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_SearchMain.this, Activity_Main.class));
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_in_right);
                    finish();
                }

                return true;
            }
        });

        logo = new ImageView(getApplicationContext());
        logo.setBackground(getResources().getDrawable(R.drawable.main_title_gray));
        logo.setLayoutParams(new YousParameter(98, 23).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.CENTER_HORIZONTAL));
        titleLayout.addView(logo);

        editText = new YousEditText(getApplicationContext());
        editText.setLayoutParams(new YousParameter(585, ViewGroup.LayoutParams.WRAP_CONTENT)
                                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                .addRules(RelativeLayout.BELOW, titleLayout.getId())
                                .setMargin(0, 30)
        );
        editText.setHint("검색");
        editText.setId(editText.hashCode());
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    if(contentBox != null)
                    {
                        main.removeView(contentBox);
                        contentBox = null;
                    }
                }
            }
        });
        main.addView(editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    clickEvent();

                    return true;
                }
                return false;
            }
        });

        final ImageView search = new ImageView(getApplicationContext());
        search.setLayoutParams(new YousParameter(35, 35)
                                .addRules(RelativeLayout.ALIGN_RIGHT, editText.getId())
                                .addRules(RelativeLayout.ALIGN_BOTTOM, editText.getId())
                                .setMargin(0, 0, 15, 25)
        );
        search.setBackground(getResources().getDrawable(R.drawable.search_search));
        main.addView(search);
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                  //  startActivity(new Intent(Activity_SearchMain.this, Activity_Main.class).putExtra("search", editText.getText().toString()));
                  //  finish();

                   clickEvent();
                }

                return true;
            }
        });

        scrollView = new yScrollView(getApplicationContext());
        scrollView.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .addRules(RelativeLayout.BELOW, editText.getId())
                .setMargin(0, 100)
        );
        main.addView(scrollView);

        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Activity_SearchMain.this, Activity_Main.class));
        overridePendingTransition(R.anim.slide_right, R.anim.slide_in_right);
        finish();
    }

    private void clickEvent()
    {
        contentBox = new ContentBox(getApplicationContext(), editText.getText().toString());
        contentBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        scrollView.addView(contentBox);
        main.requestFocus();

        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

}
