package com.lmasi.lmasi.yous;

import android.content.Context;
import android.view.View;

/**
 * Created by lmasi on 2017. 5. 24..
 */

public class YousView extends View {

    private YousParameter params;

    public YousView(Context context, float width, float height, int image) {
        super(context);

        params = new YousParameter(width, height);
        this.setLayoutParams(params);

        this.setBackground(getResources().getDrawable(image));
    }

    public YousView addRules(int verb) {
        params.addRules(verb);
        this.setLayoutParams(params);

        return this;
    }

    public YousView addRules(int verb, int subject) {
        params.addRules(verb, subject);
        this.setLayoutParams(params);

        return this;
    }

    public YousView setMargin(double left, double top, double right, double bottom) {
        params.setMargin(left, top, right, bottom);
        this.setLayoutParams(params);

        return this;
    }

    public YousView setMargin(double left, double top) {
        params.setMargin(left, top);
        this.setLayoutParams(params);

        return this;
    }
}
