package com.example.lmasi.yous;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 24..
 */

public class YousImageView extends ImageView {

    private YousParameter params;

    public YousImageView(Context context, float width, float height, int image) {
        super(context);

        params = new YousParameter(width, height);
        this.setLayoutParams(params);

        this.setBackground(getResources().getDrawable(image));
    }

    public YousImageView addRules(int verb) {
        params.addRules(verb);
        this.setLayoutParams(params);

        return this;
    }

    public YousImageView addRules(int verb, int subject) {
        params.addRules(verb, subject);
        this.setLayoutParams(params);

        return this;
    }

    public YousImageView setMargin(double left, double top, double right, double bottom) {
        params.setMargin(left, top, right, bottom);
        this.setLayoutParams(params);

        return this;
    }

    public YousImageView setMargin(double left, double top) {
        params.setMargin(left, top);
        this.setLayoutParams(params);

        return this;
    }
}
