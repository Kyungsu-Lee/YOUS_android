package com.example.lmasi.yous;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lmasi on 2017. 5. 22..
 */

public class HyperLinkTextView extends RelativeLayout {

    YousTextView title;
    YousTextView url;

    public HyperLinkTextView(Context context, String title, final String url) {
        super(context);

        this.title = new YousTextView(getContext());
        this.title.setTextColor(Color.parseColor("#666666"));
        this.title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.title.setTextSize(28);
        this.title.setId(this.title.hashCode());
        this.title.setText(title);
        this.addView(this.title);

        this.url = new YousTextView(getContext());
        this.url.setTextColor(Color.parseColor("#666666"));
        this.url.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.BELOW, this.title.getId())
                                    .setMargin(0, 10)
        );
        this.url.setTextSize(28);
        this.url.setLineFlag(false);
        this.url.setLineSpacing(0, 1.4f);
        this.url.setText(fromHtml("<u>" + url + "</h1>"));
        this.url.setId(this.url.hashCode());
        this.url.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    try {

                        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        browser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(browser);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                return  true;
            }
        });
        this.url.setLinksClickable(true);
        this.addView(this.url);
    }

    public static Spanned fromHtml(String source) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            return Html.fromHtml(source);
        }
        return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
    }

    public Spanned converTxtToHtml(String txt) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(txt,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(txt);
        }
        return result;
    }


    public SpannableStringBuilder convertTxtToLink(Context context, String html) {
        CharSequence sequence = converTxtToHtml(html);
        SpannableStringBuilder buffer = new SpannableStringBuilder(sequence);
        URLSpan[] currentSpans = buffer.getSpans(0, sequence.length(), URLSpan.class);

        for (URLSpan span : currentSpans) {
            ClickUrlSpan clickUrlSpan = new ClickUrlSpan(context, span.getURL());
            int start = buffer.getSpanStart(span);
            int end = buffer.getSpanEnd(span);
            buffer.setSpan(clickUrlSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return buffer;
    }

    private class ClickUrlSpan extends ClickableSpan {
        private Context mContext;
        private String mUrl;

        public ClickUrlSpan(@NonNull Context context, String url) {
            super();
            this.mContext = context;
            this.mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
        }
    }


}
