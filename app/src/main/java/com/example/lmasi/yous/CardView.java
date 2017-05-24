package com.example.lmasi.yous;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class CardView extends RelativeLayout {

    ImageView img;
    YousTextView tv_title;
    YousTextView tv_subtitle;

    String agree;
    String disagree;

    private String title;
    private int index;

    private int color;

    private YousParameter params;

    public YousParameter getParams() {
        return params;
    }

    public void setParams(YousParameter params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public CardView(final Context context, String title, String subTitle, int index, int color, String agree, String disagree) {
        super(context);

        this.title = title;
        this.index = index;
        this.agree = agree;
        this.disagree = disagree;

        RelativeLayout block = new RelativeLayout(getContext());
        block.setBackgroundColor(Color.argb(130, 0, 0, 0));
        block.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        this.addView(block);

        img = new ImageView(getContext());
        img.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        img.setImageBitmap(new ImageRoader().
                getBitmapImg("/yous/content/img/yous/" + index + "/" + index + ".png"));
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(img);

        block.bringToFront();

        tv_title = new YousTextView(getContext());
        tv_title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(CENTER_IN_PARENT)
        );
        tv_title.setText(title);
        tv_title.setTextSize(35);
        tv_title.setTextColor(color);
        tv_title.setId(tv_title.hashCode());
        block.addView(tv_title);

        tv_subtitle = new YousTextView(getContext());
        tv_subtitle.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, tv_title.getId())
                .setMargin(0, 10, 0, 0)
        );
        tv_subtitle.setText(subTitle);
        tv_subtitle.setTextColor(Color.WHITE);
        tv_subtitle.setTextSize(28);
        tv_subtitle.setId(tv_subtitle.hashCode());
        block.addView(tv_subtitle);




        this.params = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        params.addRules(RelativeLayout.CENTER_HORIZONTAL);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    getContext().startActivity(new Intent(getContext(), Activity_Summary.class)
                            .putExtra("title", CardView.this.title)
                            .putExtra("index", CardView.this.index)
                            .putExtra("agree", CardView.this.agree)
                            .putExtra("disagree", CardView.this.disagree)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    );

                    clickAction();
                }



                return true;
            }
        });

    }

    public void setTitleColor(int color)
    {
        this.tv_title.setTextColor(color);
    }

    public void setSubTitleColor(int color)
    {
        this.tv_subtitle.setTextColor(color);
    }

    public void clickAction()
    {

    }

}
