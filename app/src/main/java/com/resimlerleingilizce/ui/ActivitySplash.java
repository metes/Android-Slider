package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Utils;

import org.json.JSONArray;

public class ActivitySplash extends Activity {

    long mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new AsyncTaskLoadJSON().execute("");
    }

    private void goToGameActivity() {
        Intent in = new Intent(ActivitySplash.this, ActivityMain.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
    }

    private class AsyncTaskLoadJSON extends AsyncTask<String, Void, Boolean> {
       // AlertDialog dialog;
        @Override
        protected Boolean doInBackground(String... params) {

            JSONArray jAr = Utils.loadJSONData();
            if (jAr == null) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
            ModelCard[] modelCards = Utils.parseJSONToModel(jAr);
            SingletonJSON.getInstance().setData(modelCards);
            Utils.saveModelAr(getBaseContext(), modelCards);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
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
            else {
                AlertDialog.Builder db = new AlertDialog.Builder(getBaseContext());
                db.setIcon(getResources().getDrawable(android.R.drawable.stat_sys_warning));
                db.setMessage(getResources().getString(R.string.click_for_checking_connection));
                db.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncTaskLoadJSON().execute("");
                        return;
                    }
                });
                db.show();
            }

        }

        @Override
        protected void onPreExecute() {
            mStartTime = System.currentTimeMillis();
            View view = findViewById(R.id.imageViewSplashLogo);
            AnimateUtils.startSplashImageAnimation(view);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

}
