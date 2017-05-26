package com.lmasi.lmasi.yous;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2016-11-07.
 */
public class YousParameter extends RelativeLayout.LayoutParams {


    public YousParameter(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public YousParameter(ViewGroup.LayoutParams source) {
        super(source);
    }

    public YousParameter(RelativeLayout.LayoutParams source) {
        super(source);
    }

    public YousParameter(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    public YousParameter(int w, int h) {

       // super((int)(w * ScreenParameter.getScreenparam_x()), (int)(h * ScreenParameter.getScreenparam_y()));
        super((int)(w * (w >= 0 ? ScreenParameter.getScreenparam_x() : 1)),(int)(h * (h >= 0 ? ScreenParameter.getScreenparam_y() : 1)));
    }

    public YousParameter(int w, double h)
    {
        super(w, (int)(h * ScreenParameter.getScreenparam_y()));
    }

    public YousParameter(double w, int h)
    {
        super((int)(w * ScreenParameter.getScreenparam_x()), h);
    }

    public YousParameter(double w, double h)
    {
        super((int)w, (int)h);
    }

    public YousParameter setMargin(double left, double top, double right, double bottom)
    {
        super.setMargins((int)(left * ScreenParameter.getScreenparam_x()), (int)(top * ScreenParameter.getScreenparam_y()), (int)(right*ScreenParameter.getScreenparam_x()), (int)(bottom*ScreenParameter.getScreenparam_y()));
        return this;
    }

    public YousParameter setMargin(double left, double top)
    {
        super.setMargins((int)(left * ScreenParameter.getScreenparam_x()), (int)(top * ScreenParameter.getScreenparam_y()), 0, 0);
        return this;
    }


    public YousParameter addRules(int verb) {
        super.addRule(verb);

        return this;
    }


    public YousParameter addRules(int verb, int subject) {
        super.addRule(verb, subject);

        return this;
    }
}
