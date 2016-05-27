package com.resimlerleingilizce.ui;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.Logy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ActivityGameLearnWords extends AppCompatActivity  {

    private Typeface mTtypeface1, mTtypeface2;
    private TextView mTextViewMeaning;
    private RelativeLayout mRelativeLayoutPhotoContainer;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer = null;

    private int mPhotoViewIndex;
    private View[] mTopImageViews;

    private ModelCard[] mModelCardsOfCategory, mModelCardsOfPeriod;
    private long mCountDownTime = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_learn_words);

        int catID = getIntent().getIntExtra(AppConstants.REASON_KEY_CATEGORY, 1);
        mModelCardsOfCategory = getJSONData(catID);
        generatePeriodsCards();
        initViews();
        fillPhotoContainer();
        startCountDownTimer();
    }

    private void generatePeriodsCards() {
        mModelCardsOfPeriod = new ModelCard[AppConstants.GUESS_CARD_COUNT_OF_PERIOD];
        for (int i = 0; i < AppConstants.GUESS_CARD_COUNT_OF_PERIOD; i++) {
            mModelCardsOfPeriod[i] = mModelCardsOfCategory[new Random().nextInt(mModelCardsOfCategory.length) + 0];
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logy.l("onResume game Activity");
        continueCountDownTimer();
    }

    protected void onPause() {
        super.onPause();
        Logy.l("onPause game Activity");
        stopCountDownTimer();
    }

    private void fillPhotoContainer() {
        mRelativeLayoutPhotoContainer.removeAllViews();
        float angle = 0, additionalAngle = 3;
        mTopImageViews = new View[AppConstants.GUESS_CARD_COUNT_OF_PERIOD];
        mPhotoViewIndex = AppConstants.GUESS_CARD_COUNT_OF_PERIOD - 1;

        for (int i = 0; i < AppConstants.GUESS_CARD_COUNT_OF_PERIOD; i++)
        {
            RelativeLayout layoutPhoto = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_photo, null);
            setModelDataToCard(layoutPhoto, mModelCardsOfPeriod[i]);

            angle = (i % 2 == 0) ? angle + additionalAngle : angle - (additionalAngle * 2);
            layoutPhoto.setRotation(angle);
            layoutPhoto.setOnTouchListener(new MyTouchListener());
            mRelativeLayoutPhotoContainer.addView(layoutPhoto);
            mTopImageViews[i] = layoutPhoto;
        }
        mPhotoViewIndex = AppConstants.GUESS_CARD_COUNT_OF_PERIOD - 1;
        // en üst kart için
        ((TextView) findViewById(R.id.textViewMeaning)).setText(mModelCardsOfPeriod[mPhotoViewIndex].getEnglish());
    }

    private void setModelDataToCard(RelativeLayout layoutPhoto, ModelCard modelCard) {
        ImageView imageViewPhoto = (ImageView) layoutPhoto.findViewById(R.id.imageViewPhotoContent);
        TextView textViewLabel = (TextView) layoutPhoto.findViewById(R.id.textViewPhotoLabel);

        Picasso.with(getBaseContext())
                .load(modelCard.getImagePath())
                .placeholder( android.R.drawable.ic_menu_report_image)//R.drawable.ic_category_meyvevesebze)
                //.centerCrop()
                .into(imageViewPhoto);
        textViewLabel.setText(modelCard.getTurkish());
        textViewLabel.setTypeface(mTtypeface2);
    }

    private void initViews() {
        mTextViewMeaning = (TextView) findViewById(R.id.textViewMeaning);
        mRelativeLayoutPhotoContainer = (RelativeLayout) findViewById(R.id.relativeLayoutPhotoContainer);
        mTextViewCountDown = (TextView) findViewById(R.id.textViewCountDown);

        initCountDownTimer(mTextViewCountDown, AppConstants.COUNTDOWN_DURATION);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "coopbl.ttf");
        mTtypeface2 = Typeface.createFromAsset(getAssets(), "homework_normal.ttf");
        mTextViewMeaning.setTypeface(mTtypeface1);
    }

    public ModelCard[] getJSONData(int catID) {
        ModelCard[] mModelCardsALL = SingletonJSON.getInstance().getData();
        List<ModelCard> modelCardsOfPeriod = new ArrayList<>();

        for (ModelCard m : mModelCardsALL)
            if (m.getCategory() == catID)
                modelCardsOfPeriod.add(m);

        return modelCardsOfPeriod.toArray(new ModelCard[modelCardsOfPeriod.size()]);
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
            } else
            {
                return false;
            }
        }
    }

    private void gotoGameActivity() {

        finish();
        Intent intent = new Intent(ActivityGameLearnWords.this, ActivityGameGuessWords.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.REASON_KEY_CARD_PERIOD_ID_ARRAY, generatePeriodIDs());
        startActivity(intent);
    }

    private int[] generatePeriodIDs() {
        int[] idAr = new int[mModelCardsOfPeriod.length];
        for (int i = 0; i < mModelCardsOfPeriod.length; i++) {
            idAr[i] = mModelCardsOfPeriod[i].getId();
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
                String seconds = "" + (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                textViewCountDown.setText(seconds);
                textViewCountDown.setTypeface(mTtypeface1);
                //textViewCountDown.setTextColor(getResources().getColor(R.color.material_white));
                //AnimateUtils.countDownNumberAnimation(textViewCountDown, 900);
            }

            public void onFinish() {
                changeCardAction();
            }
        };
    }

    private void changeCardAction() {
        mPhotoViewIndex--;
        if (mPhotoViewIndex <= 0) {
            // TODO sonraki activity
            gotoGameActivity();
            return;
        }
        else {
            restartCountDownTimer();
            mTopImageViews[mPhotoViewIndex ].setVisibility(View.GONE);
        }

        ((TextView) findViewById(R.id.textViewMeaning)).setText(mModelCardsOfPeriod[mPhotoViewIndex].getEnglish());
        Logy.l("textViewCountDown / onFinish mPhotoViewIndex: " + mPhotoViewIndex);
    }

}
