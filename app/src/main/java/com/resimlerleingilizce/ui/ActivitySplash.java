package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Utils;

import org.json.JSONArray;

public class ActivitySplash extends Activity {

    long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);

        new AsyncTaskLoadJSON().execute("");
    }

    private void goToGameActivity() {
        Intent in = new Intent(ActivitySplash.this, ActivityMain.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
    }

    private class AsyncTaskLoadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            JSONArray jAr = Utils.loadJSONData();
            SingletonJSON.getInstance().setData(Utils.parseJSONToModel(jAr));

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while ( System.currentTimeMillis() < AppConstants.DURATION_MIN_SPLASH_TIME + mStartTime) {
                        try{
                            Thread.sleep(100);
                        }catch (Exception e) { e.printStackTrace(); }
                    }
                    goToGameActivity();
                }
            }).start();
        }

        @Override
        protected void onPreExecute() {
            mStartTime = System.currentTimeMillis();
            View view = findViewById(R.id.imageViewSplashLogo);
            AnimateUtils.startSplashImageAnimation(view);
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
