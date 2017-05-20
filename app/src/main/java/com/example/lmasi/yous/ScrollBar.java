package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 18..
 */

public class ScrollBar extends RelativeLayout {

    private ImageView scrollLine;
    private RelativeLayout scrollCircle;
    private YousTextView alphabet;

    private YousParameter params;
    RelativeLayout scrollLayout;

    private YousTextView top;
    private YousTextView bottom;

    private final int DEFALUT_TITLE_SIZE      = 35;
    private final int DEFAULT_CONTENT_SIZE    = 30;
    private final int padding                 = 20;

    private int local_index;

    public int getLocal_index() {
        return local_index;
    }

    public void setLocal_index(int local_index) {
        this.local_index = local_index;
    }

    public ScrollBar(Context context, char alphabet, String top, final String bottom, int local_index)
    {
        super(context);

        setLocal_index(local_index);

        this.top = new YousTextView(getContext());
        this.top.setText(top);
        this.top.setTextSize(DEFAULT_CONTENT_SIZE);
        this.top.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, 70)
                                    .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        this.top.setGravity(Gravity.CENTER);
        this.top.setTextSize(27);
        this.top.setLineSpacing(0, 1.2f);
        this.top.setId(this.top.hashCode());
        this.addView(this.top);

        scrollLayout = new RelativeLayout(getContext());
        scrollLayout.setLayoutParams(new YousParameter(130, 538)
                                        .addRules(RelativeLayout.BELOW, this.top.getId())
                                        .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                        .setMargin(0, padding)

        );
        scrollLayout.setId(scrollLayout.hashCode());
        this.addView(scrollLayout);


        scrollLine = new ImageView(getContext());
        scrollLine.setBackground(getResources().getDrawable(R.drawable.scroll_line));
        scrollLine.setLayoutParams(new YousParameter(4, 538)
                                    .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        scrollLine.setId(scrollLine.hashCode());
        scrollLayout.addView(scrollLine);

        scrollCircle = new RelativeLayout(getContext());
        scrollCircle.setBackground(getResources().getDrawable(R.drawable.scroll_circle));
        scrollCircle.setLayoutParams(new YousParameter(48, 48)
                                        .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        scrollLayout.addView(scrollCircle);

        this.alphabet = new YousTextView(getContext());
        this.alphabet.setTextSize(30);
        this.alphabet.setText(Character.toString(alphabet));
        this.alphabet.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                        .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        this.alphabet.setTextColor(Color.rgb(255, 255, 255));
        scrollCircle.addView(this.alphabet);


        this.bottom = new YousTextView(getContext());
        this.bottom.setText(bottom);
        this.bottom.setTextSize(DEFAULT_CONTENT_SIZE);
        this.bottom.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, scrollLayout.getId())
                .setMargin(0, padding)
        );
        this.bottom.setGravity(Gravity.CENTER);
        this.bottom.setId(this.bottom.hashCode());
        this.bottom.setTextSize(27);
        this.bottom.setLineSpacing(0, 1.2f);
        this.addView(this.bottom);
        scrollCircle.bringToFront();

        params = new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //if(event.getAction() == MotionEvent.ACTION_UP)
                {

                    float x = event.getX();
                    float y = event.getY();

                    if((scrollLayout.getY() <= y) && (y <= ScrollBar.this.bottom.getY()))
                    {
                        float rate = ((Math.abs(y - scrollLayout.getY())) / scrollLine.getHeight()) * 100;

                        setRate(rate);
                    }
                }

                return true;
            }
        });
    }

    public ScrollBar addRule(int verb)
    {
        this.setLayoutParams(params.addRules(verb));

        return this;
    }

    public ScrollBar addRule(int verb, int view)
    {
        this.setLayoutParams(params.addRules(verb, view));

        return this;
    }

    public ScrollBar setMargin(double left, double top, double right, double bottom)
    {
        this.setLayoutParams(params.setMargin(left, top, right, bottom));

        return this;
    }

    public void setRate(float rate)
    {
        float y = scrollLine.getY() + (scrollLine.getHeight() -  scrollCircle.getHeight()) * (rate / 100.0f);

        scrollCircle.setY(y);
    }

    public int getRate()
    {
        return (int)((this.scrollCircle.getY() - scrollLine.getY()) * 1.0f / (scrollLine.getHeight() - scrollCircle.getHeight()) * 100);
    }

    public void addDot(float rate)
    {
        ImageView dot = new ImageView(getContext());
        dot.setBackground(getResources().getDrawable(R.drawable.dot));
        dot.setLayoutParams(new YousParameter(15, 15)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        scrollLayout.addView(dot);
        float y = scrollLine.getY() + (scrollLine.getHeight() -  scrollCircle.getHeight()) * (rate / 100.0f);

        dot.setY(y);
        scrollCircle.bringToFront();
    }

    public void setClear()
    {
        this.scrollCircle.setBackground(getResources().getDrawable(R.drawable.btn_yes));
        scrollCircle.removeView(alphabet);
        this.setOnTouchListener(null);
    }
}
