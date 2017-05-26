package com.lmasi.lmasi.yous;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class NetFileReader {

    private StringBuilder builder;

    public NetFileReader(final String file)
    {
        builder = new StringBuilder();

        new Thread()
        {
            @Override
            public void run() {
                try {
                    URL url = new URL(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    String str;
                    while((str = br.readLine()) != null)
                    {
                        Log.e("ssss", str);
                        builder.append(str + "\n");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public StringBuilder getData()
    {
        return this.builder;
    }
}
