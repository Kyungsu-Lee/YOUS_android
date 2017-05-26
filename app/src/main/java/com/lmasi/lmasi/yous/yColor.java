package com.lmasi.lmasi.yous;

import android.graphics.Color;

/**
 * Created by lmasi on 2017. 5. 15..
 */

public class yColor extends Color {

    public static int yrgb(int r, int g, int b)
    {
        return rgb((r * 255) / 100, (g * 255)/ 100, (b*255) / 100);
    }
}
