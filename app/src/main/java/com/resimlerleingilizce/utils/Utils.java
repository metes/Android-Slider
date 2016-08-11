package com.resimlerleingilizce.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;

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
import java.util.List;
import java.util.Random;

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



    public static ModelCard[] parseJSONToModel(JSONArray jsonArray) {
        ModelCard[] modelCards = new ModelCard[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                JSONObject o = jsonArray.getJSONObject(i);
                modelCards[i] = new ModelCard(o.getInt("id"), o.getString("tr"), o.getString("en"), o.getString("path"), o.getInt("cat") );
//                Logy.l("json: " + o.getString("tr"));
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
        prefsEditor.putString("myJson", json);
        prefsEditor.commit();
    }

    // ok
    public static ModelCard[] loadModelAr(Context context) {
        try {
            SharedPreferences mPrefs = context.getSharedPreferences(AppConstants.REASON_KEY_SINGLETON_JSON, context.MODE_PRIVATE);
            Gson gson = new Gson();
            String oldJsonString = mPrefs.getString("myJson", "");

            if (!oldJsonString.isEmpty() ) {
                Type type = new TypeToken<ModelCard[]>() { }.getType();
                return gson.fromJson(oldJsonString, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getUniqueID(List list, int[] periodAr) {
        int newID = -1;
        while( newID == -1 || list.contains(newID)){
            newID = periodAr[new Random().nextInt(periodAr.length - 1) + 0];
        }
        return newID;
    }

    public static ModelCard getUniqueModelCard(List<ModelCard> list, ModelCard[] periodAr) {
        ModelCard newModel;
        boolean isUnique;

        do {
            isUnique = true;
            newModel = periodAr[new Random().nextInt(periodAr.length - 1) + 0];
            for (ModelCard model : list) {
                if (model.getId() == newModel.getId() ){
                    isUnique = false;
                }
            }
        } while (!isUnique);

        return newModel;
    }
    
}
