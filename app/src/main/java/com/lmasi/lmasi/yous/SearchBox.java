package com.lmasi.lmasi.yous;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by lmasi on 2017. 5. 24..
 */

public abstract class SearchBox extends RelativeLayout {

    public SearchBox(Context context) {
        super(context);
    }

    public abstract void setClear();
}
