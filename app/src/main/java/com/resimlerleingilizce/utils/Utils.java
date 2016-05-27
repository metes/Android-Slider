package com.resimlerleingilizce.utils;

import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mete on 27.05.2016.
 */
public class Utils {

    public static ModelCard getModelCardFromId(int id) {
        return SingletonJSON.getInstance().getData()[id];
    }

    public static void loadJSONData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "nullll";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(AppConstants.URL_JSON_DATA)
                        .addHeader("Content-Type", "json")
                        .build();
                try
                {
                    Response response = client.newCall(request).execute();
                    InputStream in = response.body().byteStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = reader.readLine();
                    result = line;
                    while((line = reader.readLine()) != null) {
                        result += line;
                    }

                    response.body().close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("json result: " + result);
            }
        }).start();
    }
}
