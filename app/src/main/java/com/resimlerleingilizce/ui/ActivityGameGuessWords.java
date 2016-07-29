package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Logy;
import com.resimlerleingilizce.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ActivityGameGuessWords extends Activity implements View.OnTouchListener {

    private Typeface mTtypeface1;
    ModelCard mModelCard;
    LinearLayout mContainerSelection;
    int[] mRandomIDs, mIdForPeriodAr;
    ImageView mImageViewPhotoContent, mImageViewPhotoResult;
    TextView mTextViewLabel;
    private int mPeriodIndex;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_words);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "luckiest_guy.ttf");

        initModelCard();
        initViews();
        initQuestionsViews();
        initSelections();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        Logy.l("onResume ActivityGameGuessWords");
    }

    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        Logy.l("onPause ActivityGameGuessWords");
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void initQuestionsViews() {
        mTextViewLabel.setText(mModelCard.getTurkish());
        Picasso.with(getBaseContext())
                .load(mModelCard.getImagePath())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(mImageViewPhotoContent);
    }

    private void initSelections() {
        mContainerSelection.removeAllViews();
        mRandomIDs = getRandomIDs();

        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            RelativeLayout layoutSelection = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_button_game_selection, null);
            layoutSelection.setOnTouchListener(this);
            initButtonSelection(layoutSelection, mRandomIDs[i]);
            mContainerSelection.addView(layoutSelection);
        }
    }

    private void initButtonSelection(View parent, int index) {
        ModelCard model = Utils.getModelCardFromId(index);
        parent.setTag(model.getTurkish());
        TextView textViewLabel = (TextView) parent.findViewById(R.id.textViewSelection);
        textViewLabel.setText(model.getEnglish());
        textViewLabel.setTypeface(mTtypeface1);
    }

    private void initViews() {
        mContainerSelection = (LinearLayout) findViewById(R.id.containerSelections);
        RelativeLayout containerPhoto = (RelativeLayout) findViewById(R.id.includeForGuess);
        mImageViewPhotoContent = (ImageView) containerPhoto.findViewById(R.id.imageViewPhotoContent);
        mTextViewLabel = (TextView) containerPhoto.findViewById(R.id.textViewPhotoLabel);
        mImageViewPhotoResult = (ImageView) containerPhoto.findViewById(R.id.imageViewAnswerResult);

        mTextViewLabel.setTypeface(mTtypeface1);
        mImageViewPhotoResult.setAlpha(0f);

        initSound();
        initAdmob();
    }

    private void initAdmob() {
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("1C959E3ADE6D3A915703D337BBC8BBFE")
                .build();
        mAdView.loadAd(adRequest);
    }

    public void initModelCard() {
        mIdForPeriodAr = new int[0];
        mPeriodIndex = 0;
        try {
            mIdForPeriodAr = getIntent().getExtras().getIntArray(AppConstants.REASON_KEY_CARD_PERIOD_ID_ARRAY);
        }catch (Exception e) {
            e.printStackTrace();
        }

        mModelCard = loadSingleton()[mIdForPeriodAr[mPeriodIndex]];
    }

    @Override
    public boolean onTouch(final View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // önceden denenen yanlış cevap verildiyse dön
                if (v.getAlpha() == 0.7f) {
                    return true;
                }

                final boolean isAnswetCorrect = v.getTag().toString().equals(mTextViewLabel.getText().toString());
                mImageViewPhotoResult.setAlpha(1f);
                AnimateUtils.startButtonAnimation(v, 80);

                // doğru cevap ise
                if (isAnswetCorrect) {
                    Utils.playSound(mSoundCorrect, mSoundPool);
                    mImageViewPhotoResult.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_correct));
                }
                // yanlış cevap ise
                else {
                    Utils.playSound(mSoundWrong, mSoundPool);
                    mImageViewPhotoResult.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_incorrect));
                }

                // butona tıklama efekti
                AnimateUtils.startButtonAnimation(v, 80);

                // Cevap doğru / yanlış yazısının yükselmesi için slider'da kullanılan aynı efekti kullanıyorum
                AnimateUtils.startSliderAnimation(mImageViewPhotoResult, ActivityMain.AnimationTypes.ARISE)
                        .setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                // doğru / yanlış yazısının yükselmesi tamamlandıktan sonra fade effect ile kaybolmasını sağlıyorum
                                Animation fadeAnimation = AnimateUtils.getFadeAnimation(1f, 0f, 1000);
                                fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        // Eğer cevap doğru ise yine fade animation sonunda yeni soruya geçeceğim
                                        if (isAnswetCorrect) {
                                            // TODO new question
                                            Logy.l("!!!!Doğru Cevap: " + v.getTag());
                                            prepeareNewQuestion();
                                        }
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                mImageViewPhotoResult.setAnimation(fadeAnimation);
                                mImageViewPhotoResult.startAnimation(fadeAnimation);

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                if (!isAnswetCorrect) {
                    v.setAlpha(0.7f);
                }
                break;
        };

        return true;
    }

    private void prepeareNewQuestion() {

        prepareNewModelCard();
        refreshViewsWithNewParams();
    }

    private void prepareNewModelCard() {
        if (mPeriodIndex >= mIdForPeriodAr.length - 1) {
            Logy.l("mModelCard next: " + -1);
            goToGameLearningActivity();
            return;
        }
        mPeriodIndex++;
        mModelCard = loadSingleton()[mIdForPeriodAr[mPeriodIndex]];
        Logy.l("mModelCard next: " + mModelCard.getEnglish());
    }

    private ModelCard[] loadSingleton() {
        if (SingletonJSON.getInstance().getData() == null) {
            SingletonJSON.getInstance().setData(Utils.loadModelAr(getBaseContext()));
        }
        return SingletonJSON.getInstance().getData();
    }

    private void refreshViewsWithNewParams() {
        initQuestionsViews();
        initSelections();
    }

    public int[] getRandomIDs() {
        int[] randomIDs = new int[AppConstants.SELECTION_COUNT];
        int rightAnswerIndex = new Random().nextInt(3) + 0;
        int rightAnswerID = mModelCard.getId() - 1;
        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            if (i == rightAnswerIndex) {
                randomIDs[i] = rightAnswerID;
            }
            else {
                randomIDs[i] = new Random().nextInt(loadSingleton().length) + 0;
            }
        }

        return randomIDs;
    }

    private void goToGameLearningActivity() {
        Intent intent = new Intent(ActivityGameGuessWords.this, ActivityGameLearnWords.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(AppConstants.REASON_KEY_CATEGORY, getIntent().getIntExtra(AppConstants.REASON_KEY_CATEGORY, 1));
        Logy.l("mCategoryPosition: " + getIntent().getIntExtra(AppConstants.REASON_KEY_CATEGORY, 1));
        startActivity(intent);
        finish();
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
