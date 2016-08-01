package com.resimlerleingilizce.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

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

    public static JSONArray loadJSONData() {

        String result = "";
        JSONArray jsonArray = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(AppConstants.URL_JSON_API)
                .addHeader("Content-Type", "json")
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            InputStream in = response.body().byteStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            result = line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            result = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);
            jsonArray = new JSONArray(result);
            response.body().close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonArray;
    }

    /*
    {\"id\":5
07-29 03:12:52.265 8051-8077/com.resimlerleingilizce D/Logy: ob: \"tr\":\"Buğday\"
07-29 03:12:52.265 8051-8077/com.resimlerleingilizce D/Logy: ob: \"en\":\"Wheat\"
07-29 03:12:52.265 8051-8077/com.resimlerleingilizce D/Logy: ob: \"path\":\"buğday.png\"
07-29 03:12:52.265 8051-8077/com.resimlerleingilizce D/Logy: ob: \"cat\":3}
     */
//    private static String fixTheResult(String result) {
//        Logy.l("result: " + result);
//        String [] jsonOb = result.split(",");
//        for (int i = 0; i < jsonOb.length; i++) {
//
//            int obIndex = i % 5;
//            Logy.l(obIndex + ". ob: " + jsonOb[i]);
//
//            String[] ob = jsonOb[i].split(":");
//
//            if (i == 0 ){
//                ob[0] = ob[0].substring(3);
//                ob[0] = ob[0].substring(2, ob[0].length()-2);
//            }
//            else {
//                ob[0] = ob[0].substring(3, ob[0].length()-3);
//                ob[1] = ob[1].substring(3, ob[1].length()-3);
//            }
//
//            Logy.l(obIndex + ".  ob[0]: " + ob[0]);
//            Logy.l(obIndex + ".  ob[1]: " + ob[1]);
//
//
//        }
//
//        return null;
//    }

    public static ModelCard[] parseJSONToModel(JSONArray jsonArray) {
        ModelCard[] modelCards = new ModelCard[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                JSONObject o = jsonArray.getJSONObject(i);
                modelCards[i] = new ModelCard(o.getInt("id"), o.getString("tr"), o.getString("en"), o.getString("path"), o.getInt("cat") );
                Logy.l("json: " + o.getString("tr"));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return modelCards;
    }


    public static void playSound(int soundId, SoundPool soundPool) {
//        ModelGameState gameState = QuwoApp.getInstance().getGameState();
//        if (gameState.isSoundOnOff())
        {
//            soundPool.stop(soundIdKeyboard);
//            soundPool.stop(soundIdOther);
            soundPool.play(soundId, 1, 1, 0, 0, 1);
        }
    }

    // işlem yapılmadı
    public static void saveModelAr(Context context, ModelCard[] modelCard) {
        SharedPreferences mPrefs = context.getSharedPreferences(AppConstants.REASON_KEY_SINGLETON_JSON, context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(modelCard);
        Logy.l("saveModelAr: " + json);
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    // ok
    public static ModelCard[] loadModelAr(Context context) {
        try {
            SharedPreferences mPrefs = context.getSharedPreferences(AppConstants.REASON_KEY_SINGLETON_JSON, context.MODE_PRIVATE);
            Gson gson = new Gson();
            String oldJsonString = mPrefs.getString("myJson", "");
            Log.d("Utils", "loadModelAr Srtingjson: " + oldJsonString);

            if (!oldJsonString.isEmpty() )
            {
                Type type = new TypeToken<ModelCard[]>() { }.getType();
                return gson.fromJson(oldJsonString, type);

            }
            else {
                // TODO bağlantı kurulamadı
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    
}
