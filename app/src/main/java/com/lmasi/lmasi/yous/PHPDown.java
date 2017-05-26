package com.lmasi.lmasi.yous;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class PHPDown extends AsyncTask<String, Integer,String> {

    private JSONArray ja;

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder jsonHtml = new StringBuilder();
        try{

// 연결 url 설정
            URL url = new URL(urls[0]);

// 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

// 연결되었으면.
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);

// 연결되었음 코드가 리턴되면.
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                    for(;;)
                    {

// 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                        String line = br.readLine();
                        if(line == null) break;

// 저장된 텍스트 라인을 jsonHtml에 붙여넣음
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

/*
protected void onPostExecute(String str){
txtView.setText(str);
}
*/

    protected void onPostExecute(String str){

        String name;

        try{
            JSONObject root = new JSONObject(str);
            ja = root.getJSONArray("result"); //get the JSONArray which I made in the php file. the name of JSONArray is "results"
/*
            for(int i=0;i<ja.length();i++){
                JSONObject jo = ja.getJSONObject(i);
                name = jo.getString("name");
                Log.d("name", name);
            }
*/
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public JSONArray getData()
    {
        return this.ja;
    }

}