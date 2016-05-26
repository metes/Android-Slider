package com.resimlerleingilizce.ui;

import android.content.ClipData;
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

import java.util.concurrent.TimeUnit;

public class ActivityGameLearnWords extends AppCompatActivity  {

    private Typeface mTtypeface1, mTtypeface2;
    private TextView mTextViewMeaning;
    private RelativeLayout mRelativeLayoutPhotoContainer;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer = null;

    private int mPhotoViewIndex;
    private View[] mTopImageViews;
    private ModelCard[] mModelCards;
    private long mCountDownTime = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_learn_words);

        mModelCards = getJSONData();
        initViews();
        fillPhotoContainer();
        startCountDownTimer();
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
        mTopImageViews = new View[AppConstants.PHOTO_AR_LENGTH];
        mPhotoViewIndex = AppConstants.PHOTO_AR_LENGTH - 1;
        for (int i = 0; i < AppConstants.PHOTO_AR_LENGTH; i++)
        {
            RelativeLayout layoutPhoto = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_photo, null);
            setModelDataToCard(layoutPhoto, mModelCards[i]);

            angle = (i % 2 == 0) ? angle + additionalAngle : angle - (additionalAngle * 2);
            layoutPhoto.setRotation(angle);
            layoutPhoto.setOnTouchListener(new MyTouchListener());
            mRelativeLayoutPhotoContainer.addView(layoutPhoto);
            mTopImageViews[i] = layoutPhoto;
        }
        mPhotoViewIndex = AppConstants.PHOTO_AR_LENGTH;
        // en üst kart için
        ((TextView) findViewById(R.id.textViewMeaning)).setText(mModelCards[mPhotoViewIndex].getEnglish());
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

    public ModelCard[] getJSONData() {
        mModelCards = SingletonJSON.getInstance().getData();
        for (int i = 0; i < mModelCards.length; i++) {
            Logy.l(i + ". modelCards: " + mModelCards[i].getTurkish() + " " + mModelCards[i].getEnglish()  + " " + mModelCards[i].getImagePath());
        }
        return mModelCards;
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
        // TODO ActivityGameGuessWords
        finish();
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

        ((TextView) findViewById(R.id.textViewMeaning)).setText(mModelCards[mPhotoViewIndex].getEnglish());
        Logy.l("textViewCountDown / onFinish mPhotoViewIndex: " + mPhotoViewIndex);
    }

}
