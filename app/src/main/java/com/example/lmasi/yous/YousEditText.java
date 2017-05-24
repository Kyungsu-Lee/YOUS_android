package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.widget.EditText;

/**
 * Created by lmasi on 2017. 5. 22..
 */

public class YousEditText extends EditText {

    public YousEditText(Context context) {
        super(context);

        this.setTextColor(Color.parseColor("#666666"));
        this.setHintTextColor(Color.argb(102, 102, 102, 102));
        this.setTypeface(YousResource.KOPUB_LIGHT);
        this.setSingleLine();
    }
}
