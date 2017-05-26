package com.lmasi.lmasi.yous;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 10..
 */

public abstract class YousSpinner<T> extends Spinner {

    public static final int DEFAULT_TEXT_SIZE = 12;

    private ArrayAdapter<T> adapter;
    private OnItemSelectedListener itemSelectedListener;
    private int textColor;
    private int textSize;

    public YousSpinner(Context context, ArrayList<T> list) {
        super(context);

        this.textSize = DEFAULT_TEXT_SIZE;

        this.adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        this.itemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(textColor);
                ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)(textSize * ScreenParameter.getScreenparam_y()));
               onItemSelectedListener(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onNothingSelectedListener(parent);
            }
        };

        this.setAdapter(this.adapter);
        this.setOnItemSelectedListener(this.itemSelectedListener);
    }

    public void setTextColor(int color)
    {
        this.textColor = color;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    public void setTitle(String title)
    {
        this.setPrompt(title);
    }

    public abstract void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id);
    public abstract void onNothingSelectedListener(AdapterView<?> parent);

/*

    final ArrayList<String> arraylist = new ArrayList<String>();
    arraylist.add("data0");
    arraylist.add("data1");
    arraylist.add("data2");
    arraylist.add("data3");


    YousSpinner<String> sp = new YousSpinner<String>(getApplicationContext(), arraylist) {
        @Override
        public void onItemSelectedListener(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), arraylist.get(position), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelectedListener(AdapterView<?> parent) {

        }
    };
    sp.setTextColor(Color.MAGENTA);
    sp.setLayoutParams(new YousParameter(500,ViewGroup.LayoutParams.WRAP_CONTENT));
    sp.setTextSize(30);
    main.addView(sp);
*/

}
