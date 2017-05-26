package com.lmasi.lmasi.yous;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by lmasi on 2017. 5. 23..
 */

public class CrossResearch extends SearchBox{

    ImageView crossLine;
    ImageView clickPoint;

    private boolean clickFlag = true;

    private CrossTextView top;
    private CrossTextView left;
    private CrossTextView right;
    private CrossTextView bottom;

    int index;

    private int margin = 20;

    public CrossResearch(Context context, int index) {
        super(context);

        this.index = index;

        top = new CrossTextView(getContext(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        top.addRules(RelativeLayout.CENTER_HORIZONTAL);
        top.addView(this);

        crossLine = new ImageView(getContext());
        crossLine.setBackground(getResources().getDrawable(R.drawable.cross));
        crossLine.setLayoutParams(new YousParameter(617, 617)
                                .addRules(RelativeLayout.CENTER_HORIZONTAL)
                                .addRules(RelativeLayout.BELOW, top.getId())
                                .setMargin(0, margin)
        );
        crossLine.setId(crossLine.hashCode());
        this.addView(crossLine);

        clickPoint = new ImageView(getContext());
        clickPoint.setBackground(getResources().getDrawable(R.drawable.dot));
        clickPoint.setLayoutParams(new YousParameter(15, 15)
                .addRules(RelativeLayout.CENTER_IN_PARENT)
        );
        this.addView(clickPoint);

        bottom = new CrossTextView(getContext(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        bottom.addRules(RelativeLayout.CENTER_HORIZONTAL)
                .addRules(RelativeLayout.BELOW, crossLine.getId())
                .setMargin(0, margin);
        bottom.addView(this);

        left = new CrossTextView(getContext(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        left
                .addRules(RelativeLayout.ALIGN_LEFT, crossLine.getId())
                .setMargin(0, 338 - margin, 0, 0);
        left.addView(this);

        right = new CrossTextView(getContext(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        right
                .addRules(RelativeLayout.ALIGN_RIGHT, crossLine.getId())
                .setMargin(0, 345 + margin, 0, 0);
        right.addView(this);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    clickEvent(event);
                }

                return true;
            }
        });



        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(index));
    }


    public void setClear()
    {
        this.clickFlag = false;
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)(this.clickPoint.getLayoutParams());
        param.width = (int)(41 * ScreenParameter.getScreenparam_x());
        param.height = (int)(41 * ScreenParameter.getScreenparam_y());
        clickPoint.setLayoutParams(param);
        clickPoint.setBackground(getResources().getDrawable(R.drawable.btn_yes));
        Animation anim = AnimationUtils.loadAnimation
                (getContext(), // 현재화면 제어권자
                        R.anim.show_transparent);      // 에니메이션 설정한 파일
        clickPoint.startAnimation(anim);

        GetDots getDots = new GetDots();
        getDots.execute(Integer.toString(index));

        UpLoad[] upLoad = new UpLoad[1];
        for(int i=0; i < upLoad.length; i++)
        {
            upLoad[i] = new UpLoad();
            upLoad[i].execute(Integer.toString(index), Float.toString(clickPoint.getX()), Float.toString(clickPoint.getY()));
        }
        clickPoint.bringToFront();
    }

    private void addDot(float x, float y)
    {

        clickPoint = new ImageView(getContext());
        clickPoint.setBackground(getResources().getDrawable(R.drawable.dot));
        clickPoint.setLayoutParams(new YousParameter(15, 15));
        clickPoint.setX(x);
        clickPoint.setY(y);

        Animation anim = AnimationUtils.loadAnimation
            (getContext(), // 현재화면 제어권자
                    R.anim.show_transparent);      // 에니메이션 설정한 파일
        clickPoint.startAnimation(anim);
        this.addView(clickPoint);

    }

    private class CrossTextView extends YousTextView
    {
        int DEFAULT_COLOR = Color.parseColor("#666666");
        int DEFAULT_SIZE = 28;

        private YousParameter params;

        public CrossTextView(Context context, float width, float height) {
            super(context);

            params = new YousParameter(width, height);
            this.setLayoutParams(params);
            this.setId(this.hashCode());
            this.setTypeface(YousResource.KOPUB_MID);
        }

        public CrossTextView addRules(int verb) {
            params.addRules(verb);
            this.setLayoutParams(params);

            return this;
        }

        public CrossTextView addRules(int verb, int subject) {
            params.addRules(verb, subject);
            this.setLayoutParams(params);

            return this;
        }

        public CrossTextView setMargin(double left, double top, double right, double bottom) {
            params.setMargin(left, top, right, bottom);
            this.setLayoutParams(params);

            return this;
        }

        public CrossTextView setMargin(double left, double top) {
            params.setMargin(left, top);
            this.setLayoutParams(params);

            return this;
        }

        public void addView(RelativeLayout main)
        {
            main.addView(this);
        }
    }

    public void clickEvent(MotionEvent event)
    {
        if(!clickFlag) return;

        clickPoint.setX(event.getX());
        clickPoint.setY(event.getY());
    }


    public class MyAsyncTask extends AsyncTask<String, Integer,String> {

        private JSONArray ja;
        private  int index;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                index = Integer.parseInt(params[0]);
                URL url = new URL("http://119.202.36.218/yous/php/get_crossName.php");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;)
                        {

                            String line = br.readLine();
                            if(line == null) break;

                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

            return jsonHtml.toString();

        }


        protected void onPostExecute(String str){

            Log.e("message", str);


            try{
                JSONObject root = new JSONObject(str);
                ja = root.getJSONArray("result");

                for(int i=0; i<ja.length(); i++)
                    if(ja.getJSONObject(i).getString("total_index").equals(""+index))
                    {
                        top.setText(ja.getJSONObject(i).getString("top"));
                        bottom.setText(ja.getJSONObject(i).getString("bottom"));
                        left.setText(ja.getJSONObject(i).getString("left"));
                        right.setText(ja.getJSONObject(i).getString("right"));
                    }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }


    }


    public class GetDots extends AsyncTask<String, Integer,String> {

        private JSONArray ja;
        private String index;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try{

                index = params[0];
                URL url = new URL("http://119.202.36.218/yous/php/get_crossPosition.php");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;)
                        {

                            String line = br.readLine();
                            if(line == null) break;

                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }

            return jsonHtml.toString();

        }

        protected void onPostExecute(String str){

            String name;

            try{
                JSONObject root = new JSONObject(str);
                ja = root.getJSONArray("result"); //get the JSONArray which I made in the php file. the name of JSONArray is "results"

                for(int i=0; i<ja.length(); i++)
                {
                    JSONObject j = ja.getJSONObject(i);

                    if(j.getString("total_index").equals(index))
                    {
                        try {
                            float x = Float.parseFloat(j.getString("x"));
                            float y = Float.parseFloat(j.getString("y"));

                            addDot(x, y);

                        }catch (Exception e)
                        {

                        }
                    }
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        public JSONArray getData()
        {
            return this.ja;
        }

    }

    public class UpLoad extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                String index = (String)params[0];
                String num_index = (String)params[1];
                String rate = (String)params[2];

                String link="http://" + YousResource.URL + "/yous/php/set_crossPosition.php";
                String data  = URLEncoder.encode("total_index", "UTF-8") + "=" + URLEncoder.encode(index, "UTF-8");
                data += "&" + URLEncoder.encode("x", "UTF-8") + "=" + URLEncoder.encode(num_index, "UTF-8");
                data += "&" + URLEncoder.encode("y", "UTF-8") + "=" + URLEncoder.encode(rate, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
    }
}
