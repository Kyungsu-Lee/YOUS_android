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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class Activity_Main extends Activity {

    RelativeLayout Rmain;

    ImageView menu;
    ImageView logo;
    ImageView search;

    PHPDown phpDown;

    String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Rmain = (RelativeLayout)findViewById(R.id.main);
        Rmain.setBackgroundColor(Color.WHITE);

        searchText = getIntent().getStringExtra("search");
        if(searchText == null) searchText = "";

        final yScrollView main = new yScrollView(getApplicationContext());
        main.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        ContentBox contentBox = new ContentBox(this, searchText){
            @Override
            public void clickAction() {
                finish();
            }
        };
        contentBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        main.addView(contentBox);

        RelativeLayout titleLayout = new RelativeLayout(getApplicationContext());
        titleLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 134));
        titleLayout.setId(titleLayout.hashCode());
        Rmain.addView(titleLayout);

        menu = new ImageView(getApplicationContext());
        menu.setBackground(getResources().getDrawable(R.drawable.main_full_menu));
        menu.setLayoutParams(new YousParameter(129,118).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(0,0));
        titleLayout.addView(menu);
        menu.setOnTouchListener(new View.OnTouchListener() {

            RelativeLayout relativeLayout;
            ImageView menu_box;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    openWindow();
                }

                return true;
            }
        });

        logo = new ImageView(getApplicationContext());
        logo.setBackground(getResources().getDrawable(R.drawable.main_title_full));
        logo.setLayoutParams(new YousParameter(98, 23).addRules(RelativeLayout.CENTER_IN_PARENT).setMargin(65,0));
        titleLayout.addView(logo);

        search = new ImageView(getApplicationContext());
        search.setBackground(getResources().getDrawable(R.drawable.main_full_search));
        search.setLayoutParams(new YousParameter(129,118).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT).setMargin(0, 0, 0, 0));
        titleLayout.addView(search);
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    Log.e("error", "clicked");
                    startActivity(new Intent(Activity_Main.this, Activity_SearchMain.class));
                    finish();
                }

                return true;
            }
        });

        Rmain.addView(main);
        titleLayout.bringToFront();
    }


    private RelativeLayout relativeLayout;
    private RelativeLayout newWindow;
    private YousTextView[] contents;

    private RelativeLayout upperLayout;
    private RelativeLayout lowerLayout;
    private RelativeLayout contentsBoxes;

    private final String[] contentText =
            {
                    "주요 이슈",
                    "정치",
                    "경제",
                    "국제",
                    "사회",
                    "IT",
                    "라이프"
            };

    private ImageView line;
    private ImageView profileImage;
    private YousTextView profileName;

    private YousTextView[] issueTextView;
    private String[] issueText =
            {
                    "이슈 보관함",
                    "튜표함"
            };

    private RelativeLayout settingBox;
    private LinearLayout settingLayout;

    private void openWindow()
    {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        relativeLayout = new RelativeLayout(getApplicationContext());    //black screen
        relativeLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relativeLayout.setBackgroundColor(Color.argb(160, 0, 0, 0));
        Rmain.addView(relativeLayout);
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                    closeWindow();

                return true;
            }
        });


        int padding = 0;

        newWindow = new RelativeLayout(getApplicationContext());
        newWindow.setLayoutParams(new YousParameter(300, ViewGroup.LayoutParams.MATCH_PARENT));
        newWindow.setBackgroundColor(Color.WHITE);
        newWindow.setPadding(padding, padding, padding, padding);
        relativeLayout.addView(newWindow);
        newWindow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return true;
            }
        });



        upperLayout = new RelativeLayout(getApplicationContext());
        upperLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 652));
        upperLayout.setId(upperLayout.hashCode());
        newWindow.addView(upperLayout);  newWindow.setAnimation(animation);


        contentsBoxes = new RelativeLayout(getApplicationContext());
        contentsBoxes.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        .addRules(RelativeLayout.CENTER_VERTICAL)
        );
        upperLayout.addView(contentsBoxes);

        contents = new YousTextView[contentText.length];
        for(int i=0; i<contents.length; i++)
        {
            contents[i] = new YousTextView(getApplicationContext());
            YousParameter param = new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param = i == 0 ? param : param.addRules(RelativeLayout.BELOW, contents[i-1].getId()).setMargin(0, 50);
            param.addRules(RelativeLayout.CENTER_HORIZONTAL);
            contents[i].setGravity(Gravity.CENTER);
            contents[i].setTextSize(textSize);
            contents[i].setLayoutParams(param);
            contents[i].setText(contentText[i]);
            contents[i].setId(contents[i].hashCode());
            contents[i].setOnTouchListener(yetListener);
            contentsBoxes.addView(contents[i]);
        }

        line = new ImageView(getApplicationContext());
        line.setLayoutParams(new YousParameter(200, 1).addRules(RelativeLayout.BELOW, upperLayout.getId()).addRules(RelativeLayout.CENTER_HORIZONTAL));
        line.setBackgroundColor(Color.parseColor("#666666"));
        line.setId(line.hashCode());
        newWindow.addView(line);

        lowerLayout = new RelativeLayout(getApplicationContext());
        lowerLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 453)
                                    .addRules(RelativeLayout.BELOW, line.getId())
        );
        lowerLayout.setId(lowerLayout.hashCode());
        newWindow.addView(lowerLayout);

        RelativeLayout lowerBox = new RelativeLayout(getApplicationContext());
        lowerBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        lowerLayout.addView(lowerBox);

        profileImage = new ImageView(getApplicationContext());
        profileImage.setBackground(getResources().getDrawable(R.drawable.profile));
        profileImage.setLayoutParams(new YousParameter(116, 116)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        profileImage.setId(profileImage.hashCode());
        lowerBox.addView(profileImage);

        profileName = new YousTextView(getApplicationContext());
        profileName.setText("나");
        profileName.setTextSize(20);
        profileName.setGravity(Gravity.CENTER);
        profileName.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                .addRules(RelativeLayout.BELOW, profileImage.getId())
                                .setMargin(0, 8)
        );
        profileName.setId(profileName.hashCode());
        lowerBox.addView(profileName);

        int lower_margin = 40;
        issueTextView = new YousTextView[issueText.length];
        for(int i=0; i<issueTextView.length; i++)
        {
            issueTextView[i] = new YousTextView(getApplicationContext());
            YousParameter param = new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param = i == 0 ?
                    param.addRules(RelativeLayout.BELOW, profileName.getId()).setMargin(0, lower_margin)
                    : param.addRules(RelativeLayout.BELOW, issueTextView[i-1].getId()).setMargin(0, lower_margin);
            param.addRules(RelativeLayout.CENTER_HORIZONTAL);
            issueTextView[i].setGravity(Gravity.CENTER);
            issueTextView[i].setTextSize(textSize);
            issueTextView[i].setLayoutParams(param);
            issueTextView[i].setText(issueText[i]);
            issueTextView[i].setId(issueTextView[i].hashCode());
            issueTextView[i].setOnTouchListener(yetListener);
            lowerBox.addView(issueTextView[i]);
        }

        settingBox = new RelativeLayout(getApplicationContext());
        settingBox.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 453)
                .addRules(RelativeLayout.BELOW, lowerLayout.getId())
        );
        settingBox.setId(settingBox.hashCode());
        newWindow.addView(settingBox);

        settingLayout = new LinearLayout(getApplicationContext());
        settingLayout.setWeightSum(2);
        settingLayout.setOrientation(LinearLayout.VERTICAL);
        settingLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        settingBox.addView(settingLayout);
        settingLayout.setBackgroundColor(Color.parseColor("#F2F2F2"));

        ImageView line2 = new ImageView(getApplicationContext());
        line2.setLayoutParams(new YousParameter(200, 1).addRules(RelativeLayout.CENTER_IN_PARENT).addRules(RelativeLayout.CENTER_HORIZONTAL));
        line2.setBackgroundColor(Color.parseColor("#666666"));
        line2.setId(line.hashCode());
        settingBox.addView(line2);

        YousTextView setting = new YousTextView(getApplicationContext());
        setting.setTextSize(textSize);
        setting.setText("설정");
        //setting.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams settingParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        settingParam.weight = 1;
        settingParam.gravity = Gravity.CENTER;
        setting.setLayoutParams(settingParam);
        setting.setGravity(Gravity.CENTER);
        settingLayout.addView(setting);

        YousTextView logout = new YousTextView(getApplicationContext());
        logout.setTextSize(textSize);
        logout.setText("로그아웃");
       // logout.setTextColor(Color.parseColor("#FFFFFF"));
        LinearLayout.LayoutParams logoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        logoutParam.weight = 1;
        logoutParam.gravity = Gravity.CENTER;
        logout.setGravity(Gravity.CENTER);
        logout.setLayoutParams(logoutParam);
        settingLayout.addView(logout);

        logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    DbResource.update("login", false);
                    startActivity(new Intent(Activity_Main.this, Activity_Start.class));

                    if(Profile.getCurrentProfile() != null)
                    {
                        LoginManager.getInstance().logOut();
                    }
                }

                return true;
            }
        });
    }
    public int textSize = 28;

    private void closeWindow()
    {
            Rmain.removeView(relativeLayout);
    }

    private View.OnTouchListener yetListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_UP)
            {
                startActivity(new Intent(Activity_Main.this, Activity_PopUp.class));
            }

            return true;
        }
    };
}
