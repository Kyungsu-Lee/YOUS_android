package com.lmasi.lmasi.yous;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class yScrollView extends ScrollView {

    private RelativeLayout main;

    public yScrollView(Context context) {
        super(context);

        main = new RelativeLayout(getContext());
        main.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addLayout(main);

        setScrollbarFadingEnabled(false);
    }

    public void addLayout(View view)
    {
        super.addView(view);
    }

    public void addView(View view)
    {
        this.main.addView(view);
    }
}
