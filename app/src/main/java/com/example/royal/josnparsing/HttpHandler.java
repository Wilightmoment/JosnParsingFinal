package com.example.royal.josnparsing;

/**
 * Created by Royal on 2017/7/19.
 */
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    //取得類別名稱 TAG=HttpHandler
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            //初始化URL
            URL url = new URL(reqUrl);
            //建立一個HttpURLConnection物件，並利用URL的openConnection()來建立連線
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //GET表示從伺服器上取得數據
            conn.setRequestMethod("GET");
            // 讀取response 即讀取內容 Stream表示位元組數據
            /*InputStream inputStream = conn.getInputStream();
                           BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
                            可以簡寫成下面那行*/
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
