package com.lmasi.lmasi.yous;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import java.util.ArrayList;

/**
 * Created by lmasi on 2017. 5. 24..
 */

public class ScrollBox extends SearchBox{


    ScrollBar[] scrollBars;
    int index;

    final ArrayList<String> top = new ArrayList<>();
    ArrayList<String> bottom = new ArrayList<>();

    public ScrollBox(Context context, int index) {
        super(context);

        this.index = index;
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(Integer.toString(index));
    }

    public void setClear()
    {
        for(ScrollBar s : scrollBars)
            s.setClear();

        GetDots getDots = new GetDots();
        getDots.execute(Integer.toString(index));

        UpLoad[] upLoad = new UpLoad[top.size()];
        for(int i=0; i < upLoad.length; i++)
        {
            upLoad[i] = new UpLoad();
            upLoad[i].execute(Integer.toString(index), "" + i, Integer.toString(getRate(i)));
        }

    }


    public int getCount() {
        return this.top.size();
    }


    public int getRate(int index) {
        return this.scrollBars[index].getRate();
    }

    public class MyAsyncTask extends AsyncTask<String, Integer,String> {

        private JSONArray ja;
        private  int index;

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                index = Integer.parseInt(params[0]);
                URL url = new URL("http://119.202.36.218/yous/php/get_scrollName.php");

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



                for(int i=0; i< ja.length(); i++)
                {
                    if(ja.getJSONObject(i).getString("total_index").equals(Integer.toString(index)))
                    {
                        top.add(ja.getJSONObject(i).getString("top"));
                        bottom.add(ja.getJSONObject(i).getString("bottom"));
                    }
                }

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setLayoutParams(new YousParameter(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .addRules(RelativeLayout.CENTER_HORIZONTAL)
                );
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(top.size());
                ScrollBox.this.addView(linearLayout);

                scrollBars = new ScrollBar[top.size()];
                for(int i=0; i<scrollBars.length; i++)
                {
                    scrollBars[i] = new ScrollBar(getContext(), (char)('a' + i), top.get(i), bottom.get(i), i);
                    scrollBars[i].setId(scrollBars[i].hashCode());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    params.weight = 1;
                    scrollBars[i].setLayoutParams(params);
                    linearLayout.addView(scrollBars[i]);
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
                URL url = new URL("http://119.202.36.218/yous/php/get_position.php");

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
                            int idx = Integer.parseInt(j.getString("num_index"));
                            float position = Float.parseFloat(j.getString("rate"));

                            scrollBars[idx].addDot(position);

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

                String link="http://" + YousResource.URL + "/yous/php/insert_position.php";
                String data  = URLEncoder.encode("total_index", "UTF-8") + "=" + URLEncoder.encode(index, "UTF-8");
                data += "&" + URLEncoder.encode("num_index", "UTF-8") + "=" + URLEncoder.encode(num_index, "UTF-8");
                data += "&" + URLEncoder.encode("rate", "UTF-8") + "=" + URLEncoder.encode(rate, "UTF-8");

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
