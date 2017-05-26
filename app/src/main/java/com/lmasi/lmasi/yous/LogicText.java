package com.lmasi.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class LogicText extends RelativeLayout {

    private YousTextView title;
    private YousTextView contents;

    private final double padding = 60;

    private YousParameter yousParameter;

    private final int DEFALUT_TITLE_SIZE      = 35;
    private final int DEFAULT_CONTENT_SIZE    = 30;

    private final int HEIGHT = 343;

    /**
     * logical text box.
     *
     * @param  context
     *         context of this view
     *
     * @param  title
     *         title of this box
     *
     * @param  contents
     *         content of this box.
     *
     * @param index
     *          index of this box.
     */
    public LogicText(Context context, final String title, final String contents, int index) {
        super(context);

        this.setPadding((int)(padding), (int)(padding), (int)(padding), (int)(padding));

        this.setBackgroundColor(index%2 == 0? Color.WHITE : Color.rgb(242, 242, 242));

        this.title = new YousTextView(getContext());
        this.title.setTextSize(DEFALUT_TITLE_SIZE);
        this.title.setText(Character.toString((char)('a' + index)) +"  " + title);
        this.title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(RelativeLayout.CENTER_HORIZONTAL)
        );
        this.title.setGravity(Gravity.LEFT);
        this.title.setId(this.title.hashCode());
        this.addView(this.title);
        this.title.setTypeface(YousResource.KOPUB_MID);
        this.title.setTextSize(32);

        this.contents = new YousTextView(getContext());
        this.contents.setTextSize(DEFAULT_CONTENT_SIZE);
        this.contents.setText(contents);
        this.contents.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, this.title.getId())
                .setMargin(0, 28)
        );
        this.contents.setGravity(Gravity.LEFT);
        this.contents.setId(this.contents.hashCode());
        this.addView(this.contents);
        this.contents.setTextSize(28);
        this.contents.setLineSpacing(0, 1.4f);


      //  yousParameter = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT,343);
        yousParameter = new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(yousParameter);

    }

    public void setTitleSize(float size)
    {
            this.title.setTextSize(size);
    }

    public void setContentsSize(float size)
    {
        this.contents.setTextSize(size);
    }

    public LogicText addRule(int verb, int view)
    {
        this.setLayoutParams(yousParameter.addRules(verb, view));

        return this;
    }

}
