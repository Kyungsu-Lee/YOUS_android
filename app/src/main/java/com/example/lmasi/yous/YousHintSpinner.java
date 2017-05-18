package com.example.lmasi.yous;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import me.srodrigo.androidhintspinner.HintAdapter;

/**
 * Created by lmasi on 2017. 5. 12..
 */

public class YousHintSpinner<T> {
    private static final String TAG = me.srodrigo.androidhintspinner.HintSpinner.class.getSimpleName();

    /**
     * Used to handle the spinner events.
     *
     * @param <T> Type of the data used by the spinner
     */
    public interface Callback<T> {
        /**
         * When a spinner item has been selected.
         *
         * @param position Position selected
         * @param itemAtPosition Item selected
         */
        void onItemSelected(int position, T itemAtPosition);
    }

    private final Spinner spinner;
    private final HintAdapter<T> adapter;
    private final Callback<T> callback;
    private int textColor = Color.rgb(0, 0, 0);
    private int textSize = 10;

    public YousHintSpinner(Spinner spinner, HintAdapter<T> adapter, Callback<T> callback) {
        this.spinner = spinner;
        this.adapter = adapter;
        this.callback = callback;

    }

    /**
     * Initializes the hint spinner.
     *
     * By default, the hint is selected when calling this method.
     */
    public void init() {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position selected: " + position);
                if (YousHintSpinner.this.callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if (!isHintPosition(position)) {
                    Object item = YousHintSpinner.this.spinner.getItemAtPosition(0);
                    YousHintSpinner.this.callback.onItemSelected(position, (T) item);
                }

                ((TextView) parent.getChildAt(0)).setTextColor(textColor);
                ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(textSize * ScreenParameter.getScreenparam_y()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });
        selectHint();
    }

    public void setTextColor(int color)
    {
        this.textColor = color;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    private boolean isHintPosition(int position) {
        return position == adapter.getHintPosition();
    }

    /**
     * Selects the hint element.
     */
    public void selectHint() {
        spinner.setSelection(adapter.getHintPosition());
    }
}
