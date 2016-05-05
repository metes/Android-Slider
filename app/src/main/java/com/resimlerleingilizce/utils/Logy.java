package com.resimlerleingilizce.utils;

import android.util.Log;

import com.resimlerleingilizce.BuildConfig;


/**
 * Created by Mete on 18.04.2016.
 */
public class Logy {

    public Logy(){ }

    /**
    * @param clazz l gönderilen sınıf
    * @param log l String
    */
    public static void l(Class clazz, String log){
        if (BuildConfig.DEBUG) {
            Log.d(clazz.getSimpleName(), log);
        }
    }

    public static void l(String log){
        if (BuildConfig.DEBUG) {
            Log.d("Logy", log);
        }
    }


    /**
     * @param clazz l gönderilen sınıf
     * @param log l String
     * @param logLevel 0 debug, 1 info, 2 warning, 3 error
     */
    public static void l(Class clazz, String log, int logLevel){
        if (BuildConfig.DEBUG) {
            switch (logLevel) {
                case 0:
                    Log.d(clazz.getSimpleName(), log);
                    break;
                case 1:
                    Log.i(clazz.getSimpleName(), log);
                    break;
                case 2:
                    Log.w(clazz.getSimpleName(), log);
                    break;
                default:
                    Log.e(clazz.getSimpleName(), log);
                    break;
            }
        }
    }


}
