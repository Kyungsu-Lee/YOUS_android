package com.lmasi.lmasi.yous;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 19..
 */

public class DateText extends  RelativeLayout{

    private YousTextView date;
    private YousTextView contents;

    private final double padding = 60;

    private YousParameter yousParameter;

    private final int DEFALUT_TITLE_SIZE      = 27;
    private final int DEFAULT_CONTENT_SIZE    = 27;

    private final int HEIGHT = 343;

    /**
     * logical text box.
     *
     * @param  context
     *         context of this view
     *
     * @param  date
     *         title of this box
     *
     * @param  contents
     *         content of this box.
     *
     * @param index
     *          index of this box.
     */
    public DateText(Context context, final String date, final String contents, int index) {
        super(context);

        this.setPadding((int)(padding), 0, (int)(padding), 0);

        this.date = new YousTextView(getContext());
        this.date.setTextSize(DEFALUT_TITLE_SIZE);
        this.date.setText(date);
        this.date.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        this.date.setGravity(Gravity.CENTER);
        this.date.setId(this.date.hashCode());
        this.addView(this.date);
        this.date.setTypeface(YousResource.KOPUB_MID);

        this.contents = new YousTextView(getContext());
        this.contents.setTextSize(DEFAULT_CONTENT_SIZE);
        this.contents.setText(contents);
        this.contents.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, this.date.getId())
                .setMargin(0, 20)
        );
        this.contents.setGravity(Gravity.CENTER);
        this.contents.setId(this.contents.hashCode());
        this.addView(this.contents);
        this.contents.setLineSpacing(0, 1.4f);


        //  yousParameter = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT,343);
        yousParameter = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(yousParameter);

    }

    public void setDateSize(float size)
    {
        this.date.setTextSize(size);
    }

    public void setContentsSize(float size)
    {
        this.contents.setTextSize(size);
    }

    public DateText addRule(int verb, int view)
    {
        this.setLayoutParams(yousParameter.addRules(verb, view));

        return this;
    }

    public DateText setMargin(double left, double top, double right, double bottom)
    {
        this.setLayoutParams(yousParameter.setMargin(left, top, right, bottom));

        return this;
    }
}
