package com.resimlerleingilizce.ui;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.Logy;
import com.resimlerleingilizce.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActivityGameLearnWords extends AppCompatActivity  {

    private View[] mTopImageViews;
    private Typeface mTtypeface1;
    private TextView mTextViewMeaning;
    private RelativeLayout mRelativeLayoutPhotoContainer;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer = null;

    private int mPhotoViewIndex;
    private boolean[] mIsImagesAreLoaded;
    private long mCountDownTime = -1;
    private ProgressDialog mProgressDilog;

    private ModelCard[] mModelCardsOfSelectedCategory, mModelCardsOfPeriod;
    AdView mAdView;
    int mCategoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_learn_words);

        mCategoryPosition = getIntent().getIntExtra(AppConstants.REASON_KEY_CATEGORY, 1);
        loadCategoryCardsJSONData(mCategoryPosition);
        generatePeriodsCards();
        initViews();
        fillPhotoContainer();

        if (isNetworkAvailable()) {
            startCountDownWhenImagesReady();
        }
        else {
            showDialogInternetCheck();
        }
    }

    private void showDialogInternetCheck() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityGameLearnWords.this);
        builder.setTitle(getResources().getString(R.string.warning));
        builder.setMessage(getResources().getString(R.string.check_internet_connection));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void startCountDownWhenImagesReady() {
        mProgressDilog = ProgressDialog.show(ActivityGameLearnWords.this, "", getResources().getString(R.string.message_loading), true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( !isAllImagesLoaded()) {
                    try{
                        Thread.sleep(2000);

                    }catch (Exception e) { e.printStackTrace(); }
                }
                mProgressDilog.cancel();
                startCountDownTimer();
            }
        }).start();
    }



    private boolean isAllImagesLoaded() {
        for (int i = 0; i < mIsImagesAreLoaded.length; i++) {
            if (!mIsImagesAreLoaded[i]) {
                Logy.l("mModelCardsOfPeriod[i].getImagePath(): " + mModelCardsOfPeriod[i].getImagePath());
                return false;
            }
        }
        return true;
    }

    private void generatePeriodsCards() {
        mModelCardsOfPeriod = getRandomIDs(mModelCardsOfSelectedCategory);
        Logy.l("mModelCardsOfPeriod: " + mModelCardsOfPeriod[0]);
    }

    public ModelCard[] getRandomIDs(ModelCard[] mModelCardsOfSelectedCategory) {
        List<ModelCard> randomModelList = new ArrayList<>();
        ModelCard[] returnData = new ModelCard[AppConstants.GUESS_CARD_COUNT_OF_PERIOD];
        for (int i = 0; i < AppConstants.GUESS_CARD_COUNT_OF_PERIOD ; i++) {
            randomModelList.add(Utils.getUniqueModelCard(randomModelList, mModelCardsOfSelectedCategory));
            Logy.l("getRandomIDs i: " + i + " / " + "returnData[i].getEnglish(): " + randomModelList.get(i).getId());
        }
        Logy.l("getRandomIDs i: ok!");
        return randomModelList.toArray(returnData);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        Logy.l("onResume game Activity");
        continueCountDownTimer();
    }

    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();

        Logy.l("onPause game Activity");
        stopCountDownTimer();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void fillPhotoContainer() {
        // tüm state'ler false ile başla
        for (int i = 0; i < mIsImagesAreLoaded.length; i++) {
            mIsImagesAreLoaded[i] = false;
        }
        mRelativeLayoutPhotoContainer.removeAllViews();
        float angle = 0, additionalAngle = 3;
        mTopImageViews = new View[AppConstants.GUESS_CARD_COUNT_OF_PERIOD];
        mPhotoViewIndex = AppConstants.GUESS_CARD_COUNT_OF_PERIOD - 1;

        for (int i = 0; i < AppConstants.GUESS_CARD_COUNT_OF_PERIOD; i++)
        {
            RelativeLayout layoutPhoto = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_photo, null);
            setModelDataToCard(layoutPhoto, i);

            angle = (i % 2 == 0) ? angle + additionalAngle : angle - (additionalAngle * 2);
            layoutPhoto.setRotation(angle);
            layoutPhoto.setOnTouchListener(new MyTouchListener());
            mRelativeLayoutPhotoContainer.addView(layoutPhoto);
            mTopImageViews[i] = layoutPhoto;
        }

        // en üst kart için
        ((TextView) findViewById(R.id.textViewMeaning)).setText(mModelCardsOfPeriod[mPhotoViewIndex].getEnglish());
    }

    private void setModelDataToCard(RelativeLayout layoutPhoto, final int index) {
        ModelCard modelCard = mModelCardsOfPeriod[index];
        ImageView imageViewPhoto = (ImageView) layoutPhoto.findViewById(R.id.imageViewPhotoContent);
        TextView textViewLabel = (TextView) layoutPhoto.findViewById(R.id.textViewPhotoLabel);
        final ImageView mImageViewPhoto = (ImageView) layoutPhoto.findViewById(R.id.imageViewAnswerResult);

        Picasso.with(getBaseContext())
                .load(modelCard.getImagePath())
                .placeholder( android.R.drawable.ic_menu_report_image)
                .into(imageViewPhoto, new Callback() {

                    @Override
                    public void onSuccess() {
                        // TODO indirme tamamlandı aksiyonu
                        mIsImagesAreLoaded[index] = true;
                    }

                    @Override
                    public void onError() {

                    }

                });


        textViewLabel.setText(modelCard.getTurkish());
        textViewLabel.setTypeface(mTtypeface1);
        mImageViewPhoto.setVisibility(View.GONE);
    }

    private void initViews() {
        mTextViewMeaning = (TextView) findViewById(R.id.textViewMeaning);
        mRelativeLayoutPhotoContainer = (RelativeLayout) findViewById(R.id.relativeLayoutPhotoContainer);
        mTextViewCountDown = (TextView) findViewById(R.id.textViewCountDown);

        initCountDownTimer(mTextViewCountDown, AppConstants.COUNTDOWN_DURATION);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "luckiest_guy.ttf");
        mTextViewMeaning.setTypeface(mTtypeface1);

        mIsImagesAreLoaded = new boolean[AppConstants.GUESS_CARD_COUNT_OF_PERIOD];

        initSound();
        initAdmob();
    }

    private void initAdmob() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1C959E3ADE6D3A915703D337BBC8BBFE")
                .build();
        mAdView.loadAd(adRequest);
    }

    public void loadCategoryCardsJSONData(int catID) {
        Logy.l("mCategoryPosition: " + mCategoryPosition);
        ModelCard[] modelCardsALL = loadSingleton();
        List<ModelCard> modelCardsOfPeriod = new ArrayList<>();

        for (ModelCard m : modelCardsALL) {
            if (m.getCategory() == catID) {
                modelCardsOfPeriod.add(m);
            }
        }
        mModelCardsOfSelectedCategory = modelCardsOfPeriod.toArray(new ModelCard[modelCardsOfPeriod.size()]);
    }

    private ModelCard[] loadSingleton() {
        if (SingletonJSON.getInstance().getData() == null) {
            SingletonJSON.getInstance().setData(Utils.loadModelAr(getBaseContext()));
        }
        return SingletonJSON.getInstance().getData();
    }

    // Örneğin alındığı proje: http://www.vogella.com/tutorials/AndroidDragAndDrop/article.html
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("" + view.getId(), "" + view.getId());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                changeCardAction();
                return true;
            } else {
                return false;
            }
        }
    }

    private void gotoGameActivity() {
        Intent intent = new Intent(ActivityGameLearnWords.this, ActivityGameGuessWords.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.REASON_KEY_CARD_PERIOD_ID_ARRAY, generatePeriodIDs());
        intent.putExtra(AppConstants.REASON_KEY_CATEGORY, mCategoryPosition);
        startActivity(intent);
        finish();
    }

    private int[] generatePeriodIDs() {
        int[] idAr = new int[mModelCardsOfPeriod.length];
        for (int i = 0; i < mModelCardsOfPeriod.length; i++) {
            idAr[i] = mModelCardsOfPeriod[i].getId() - 1; // database'deki id'ler 1'den başladığı için
        }
        return idAr;
    }

    private void continueCountDownTimer() {
        if (mCountDownTime > -1) {
            stopCountDownTimer();
            initCountDownTimer(mTextViewCountDown, mCountDownTime);
            startCountDownTimer();
        }
    }

    private void restartCountDownTimer() {
        stopCountDownTimer();
        initCountDownTimer(mTextViewCountDown, AppConstants.COUNTDOWN_DURATION);
        startCountDownTimer();
    }

    private void startCountDownTimer() {
        if (mCountDownTimer != null) {
            stopCountDownTimer();
        }
        mCountDownTimer.start();
    }

    private void stopCountDownTimer() {
        mCountDownTimer.cancel();
    }

    public void initCountDownTimer(final TextView textViewCountDown, final long totalTimeMilis) {
        mCountDownTimer = new CountDownTimer(totalTimeMilis, 1000) {
            public void onTick(long millisUntilFinished) {
                mCountDownTime = millisUntilFinished;
                String seconds = "" + Math.round((float)millisUntilFinished / 1000.0f);
                textViewCountDown.setText(seconds);
                textViewCountDown.setTypeface(mTtypeface1);
            }

            public void onFinish() {
                changeCardAction();
            }
        };
    }

    private void changeCardAction() {
        Utils.playSound(mSoundSlide, mSoundPool);
        if (mPhotoViewIndex <= 0) {
            // TODO sonraki activity
            gotoGameActivity();
            return;
        } else {
            restartCountDownTimer();
            mTopImageViews[mPhotoViewIndex--].setVisibility(View.GONE);
            mTextViewMeaning.setText(mModelCardsOfPeriod[mPhotoViewIndex].getEnglish());
        }
        Logy.l("textViewCountDown / onFinish mPhotoViewIndex: " + mPhotoViewIndex);
    }

    SoundPool mSoundPool;
    int mSoundClick, mSoundCorrect, mSoundWrong, mSoundSlide;
    private void initSound() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mSoundClick = mSoundPool.load(this, R.raw.sound_click, 1);
        mSoundCorrect = mSoundPool.load(this, R.raw.sound_correct_answer, 1);
        mSoundWrong = mSoundPool.load(this, R.raw.sound_wrong_answer, 1);
        mSoundSlide = mSoundPool.load(this, R.raw.sound_photo_slide, 1);
    }
}
