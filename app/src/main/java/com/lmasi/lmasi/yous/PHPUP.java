package com.lmasi.lmasi.yous;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class PHPUP extends AsyncTask<String, Void, String> {

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
            String email = (String)params[0];
            String passwd = (String)params[1];
            String name = (String)params[2];
            String birth = (String)params[3];
            String gender = (String)params[4];

            String link="http://" + YousResource.URL + "/yous/insert.php";
            String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(passwd, "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("birth", "UTF-8") + "=" + URLEncoder.encode(birth, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");

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
