package com.example.lmasi.yous;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class ImageRoader {

    private final String serverUrl = "http://119.202.36.218";

    public ImageRoader() {
        new ThreadPolicy();
    }

    public Bitmap getBitmapImg(String imgStr) {

        Bitmap bitmapImg = null;

        try {
            URL url = new URL(serverUrl + imgStr);

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmapImg = BitmapFactory.decodeStream(is);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return bitmapImg;
    }

    /*  example code

     ImageView iv = new ImageView(getApplicationContext());
        iv.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        iv.setImageBitmap(new ImageRoader().
                getBitmapImg("/hgushop/hgulist/img/1/3.jpg"));
        main.addView(iv);

     */
}