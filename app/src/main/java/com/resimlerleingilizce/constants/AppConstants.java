package com.resimlerleingilizce.constants;

/**
 * Created by Mete on 24.05.2016.
 */
public class AppConstants {

    // PARAMS
    public static final int DURATION_MIN_SPLASH_TIME = 3 * 1000;
    public static final int SELECTION_COUNT = 3;
    public static final int GUESS_CARD_COUNT_OF_PERIOD = 7;
    public static final long COUNTDOWN_DURATION = (7 * 1000) + 250; // tam 4000 yapınca bazen 3'ten geri sayabiliyor.

    // REASON KEYS
    public static final String REASON_KEY_SINGLETON_JSON = "REASON_KEY_SINGLETON_JSON";
    public static final String REASON_KEY_CARD_PERIOD_ID_ARRAY = "CARD_ID_ARRAY";
    public static final String REASON_KEY_CATEGORY = "CATEGORY";

    // API
    public static final String URL_DOMAIN = "http://1ki3provider.com";
    public static final String URL_JSON_API = URL_DOMAIN + "/api/values";
    public static final String URL_IMAGES_PREFIX_JSON_DATA = URL_DOMAIN + "/image/";


}
