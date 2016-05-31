package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private Typeface mTtypeface1, mTtypeface2;
    ModelCard mModelCard;
    LinearLayout mContainerSelection;
    int[] mRandomIDs, mIdForPeriodAr;
    TextView mTextViewLabel;
    ImageView mImageViewPhoto;
    private int mPeriodIndex;
    private boolean isPeriodEnding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_words);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "coopbl.ttf");
        mTtypeface2 = Typeface.createFromAsset(getAssets(), "homework_normal.ttf");

        initParams();
        initViews();
        initSelections();
    }

    private void initSelections() {
        mContainerSelection.removeAllViews();
        mRandomIDs = getRandomIDs();
        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            RelativeLayout layoutSelection = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_button_game_selection, null);
            layoutSelection.setOnTouchListener(this);
            initButtonSelection(layoutSelection, i);
            mContainerSelection.addView(layoutSelection);
        }
    }

    private void initButtonSelection(View parent, int index) {
        ModelCard model = Utils.getModelCardFromId(mIdForPeriodAr[index]);
        parent.setTag(model.getTurkish());
        TextView textViewLabel = (TextView) parent.findViewById(R.id.textViewSelection);
        textViewLabel.setText(model.getEnglish());
        textViewLabel.setTypeface(mTtypeface1);
    }

    private void initViews() {
        mContainerSelection = (LinearLayout) findViewById(R.id.containerSelections);
        RelativeLayout containerPhoto = (RelativeLayout) findViewById(R.id.includeForGuess);
        ImageView imageViewPhoto = (ImageView) containerPhoto.findViewById(R.id.imageViewPhotoContent);
        mTextViewLabel = (TextView) containerPhoto.findViewById(R.id.textViewPhotoLabel);
        mImageViewPhoto = (ImageView) containerPhoto.findViewById(R.id.imageViewAnswerResult);

        mTextViewLabel.setText(mModelCard.getTurkish());
        mTextViewLabel.setTypeface(mTtypeface1);

        Picasso.with(getBaseContext())
                .load(mModelCard.getImagePath())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(imageViewPhoto);

        mImageViewPhoto.setAlpha(0f);
    }

    public void initParams() {
        mIdForPeriodAr = new int[0];
        mPeriodIndex = 0;
        try {
            mIdForPeriodAr = getIntent().getExtras().getIntArray(AppConstants.REASON_KEY_CARD_PERIOD_ID_ARRAY);
            //mPeriodIndex = getIntent().getExtras().getInt(AppConstants.REASON_KEY_ID);
        }catch (Exception e) {
            e.printStackTrace();
        }

        mModelCard = SingletonJSON.getInstance().getData()[mIdForPeriodAr[mPeriodIndex]];
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
                mImageViewPhoto.setAlpha(1f);
                AnimateUtils.startButtonAnimation(v, 80);

                // doğru cevap ise
                if (isAnswetCorrect) {
                    mImageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_correct));
                }
                // yanlış cevap ise
                else {
                    mImageViewPhoto.setImageDrawable(getResources().getDrawable(R.drawable.ic_image_incorrect));
                }

                // butona tıklama efekti
                AnimateUtils.startButtonAnimation(v, 80);

                // Cevap doğru / yanlış yazısının yükselmesi için slider'da kullanılan aynı efekti kullanıyorum
                AnimateUtils.startSliderAnimation(mImageViewPhoto, ActivityMain.AnimationTypes.ARISE)
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
                                mImageViewPhoto.setAnimation(fadeAnimation);
                                mImageViewPhoto.startAnimation(fadeAnimation);

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

        if (mPeriodIndex == mIdForPeriodAr.length - 1) {
            Logy.l("mModelCard next: " + -1);
            goToGameLearningActivity();
            return;
        }


        mPeriodIndex++;
        Logy.l("mModelCard next: " + mModelCard.getEnglish());
        mModelCard = SingletonJSON.getInstance().getData()[mIdForPeriodAr[mPeriodIndex]];

        refreshViewsWithNewParams();
    }

    private void refreshViewsWithNewParams() {
        mTextViewLabel.setText(mModelCard.getTurkish());

        initSelections();
    }

    public int[] getRandomIDs() {
        int[] randomIDs = new int[AppConstants.SELECTION_COUNT];
        int rightAnswerIndex = new Random().nextInt(2) + 0;
        int rightAnswerID = mModelCard.getId();
        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            if (i == rightAnswerIndex) {
                randomIDs[i] = rightAnswerID;
            }
            else {
                randomIDs[i] = new Random().nextInt(SingletonJSON.getInstance().getData().length) + 0;
            }
        }

        return randomIDs;
    }

    private void goToGameLearningActivity() {
        Intent in = new Intent(ActivityGameGuessWords.this, ActivityGameLearnWords.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
    }
}
