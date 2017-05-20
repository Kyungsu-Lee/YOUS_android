package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by lmasi on 2017. 5. 12..
 */

public class YousTextView extends TextView {

    public YousTextView(Context context) {
        super(context);

        setTextColor(Color.rgb(102, 102, 102));
        setTypeface(YousResource.KOPUB_LIGHT);
    }

    private Handler hd;

    @Override
    public void setTextSize(float size) {
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(size * ScreenParameter.getScreenparam_y()));
    }

    public void setTextWithHandler(final String str)
    {
        hd = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg) {

                setText(str);
            }
        };

        hd.sendEmptyMessageDelayed(0, 10);
    }

    public void setTextWithHandler(final Spanned str)
    {
        hd = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg) {

                setText(str);
            }
        };

        hd.sendEmptyMessageDelayed(0, 10);
    }

    @Override
    public void setText(CharSequence text, BufferType type)
    {
        super.setText(text.toString().replace(" ", "\u00A0"), type);
    }
}
