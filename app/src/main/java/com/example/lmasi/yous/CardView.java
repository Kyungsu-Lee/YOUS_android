package com.example.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class CardView extends RelativeLayout {

    ImageView img;
    TextView tv_title;
    TextView tv_subtitle;

    public CardView(Context context, String fileName, String title, String subTitle) {
        super(context);

        img = new ImageView(getContext());
        img.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setImageBitmap(new ImageRoader().
                getBitmapImg("/yous/img/" + fileName));
        this.addView(img);

        tv_title = new TextView(getContext());
        tv_title.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    .addRules(CENTER_IN_PARENT)
        );
        tv_title.setText(title);
        tv_title.setId(tv_title.hashCode());
        this.addView(tv_title);

        tv_subtitle = new TextView(getContext());
        tv_subtitle.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .addRules(CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, tv_title.getId())
                .setMargin(0, 10, 0, 0)
        );
        tv_subtitle.setText(subTitle);
        tv_subtitle.setId(tv_subtitle.hashCode());
        this.addView(tv_subtitle);


        RelativeLayout block = new RelativeLayout(getContext());
        block.setBackgroundColor(Color.argb(165, 0, 0, 0));
        block.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(block);
    }

    public void setTitleColor(int color)
    {
        this.tv_title.setTextColor(color);
    }

    public void setSubTitleColor(int color)
    {
        this.tv_subtitle.setTextColor(color);
    }


}
