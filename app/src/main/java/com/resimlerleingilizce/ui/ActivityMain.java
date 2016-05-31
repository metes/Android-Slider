package com.resimlerleingilizce.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Logy;
import com.resimlerleingilizce.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private boolean isMImgCenter2Visible;
    private int mCategoryPosition;
    private Typeface mTtypeface;
    private ImageView mImgCenter1, mImgCenter2, mImgRight1, mImgRight2, mImgLeft1, mImgLeft2, mImgCategoryLabel;
    private Button mButtonPlay;
    RelativeLayout mSliderContainer;

    private int[] CATEGORY_IMAGE_RESOURCE_IDS = { R.drawable.ic_category_1,
            R.drawable.ic_category_2, R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5 };

    private int[] CATEGORY_TEXT_RESOURCE_IDS = { R.drawable.ic_category_hayvanlar,
            R.drawable.ic_category_meyvevesebze, R.drawable.ic_category_yiyecekler,
            R.drawable.ic_category_dunya, R.drawable.ic_category_giysiler };

    public enum AnimationTypes {
        CENTER_TO_RIGHT,
        CENTER_TO_LEFT,
        LEFT_TO_CENTER,
        RIGHT_TO_CENTER,
        DISAPEAR,
        ARISE
    }

    public enum ButtonTypes {
        ANIMATON_BUTTON_LEFT,
        ANIMATON_BUTTON_RIGHT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCategoryPosition = 0;

        initViews();
    }

    @Override
    public void onResume(){

        super.onResume();
        if (mCategoryPosition > 0) {
            mImgCenter2.setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[mCategoryPosition]));
        }
        mImgCenter2.setVisibility(View.VISIBLE);
        isMImgCenter2Visible = true;

        calculateAnimationSizes();

    }

    private void calculateAnimationSizes() {

        mSliderContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = mSliderContainer.getLayoutParams().width;

        AnimateUtils.mAnimationTranslateXPosition = width / 3.8f;
        AnimateUtils.mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER = 1.29230769f;

        Logy.l("mAnimationTranslateXPosition: " + AnimateUtils.mAnimationTranslateXPosition);
        Logy.l("mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER: " + AnimateUtils.mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER);
    }

    private void initViews() {
        ImageButton imageButtonSliderRight = (ImageButton)     findViewById(R.id.imageButtonRight);
        ImageButton imageButtonSliderLeft = (ImageButton) findViewById(R.id.imageButtonLeft);
        mSliderContainer = (RelativeLayout) findViewById(R.id.sliderContainer);
        mImgCenter1 = (ImageView) findViewById(R.id.imageViewCenter);
        mImgCenter2 = (ImageView) findViewById(R.id.imageViewCenter2);
        mImgRight1 = (ImageView) findViewById(R.id.imageViewRight1);
        mImgLeft2 = (ImageView) findViewById(R.id.imageViewLeft1);
        mImgRight2 = (ImageView) findViewById(R.id.imageViewRight2);
        mImgLeft1 = (ImageView) findViewById(R.id.imageViewLeft2);
        mImgCategoryLabel = (ImageView) findViewById(R.id.imageViewCategoryLabel);
        mButtonPlay = (Button) findViewById(R.id.buttonPlay);


        imageButtonSliderRight.setOnClickListener(this);
        imageButtonSliderLeft.setOnClickListener(this);

        mButtonPlay.setOnClickListener(this);
        mButtonPlay.setOnTouchListener(this);

        mTtypeface = Typeface.createFromAsset(getAssets(), "coopbl.ttf");
        mButtonPlay.setTypeface(mTtypeface);

        initImageViews();

        AnimateUtils.startSliderSideButonAnimation(imageButtonSliderLeft, ButtonTypes.ANIMATON_BUTTON_LEFT);
        AnimateUtils.startSliderSideButonAnimation(imageButtonSliderRight, ButtonTypes.ANIMATON_BUTTON_RIGHT);
    }

    private void initImageViews() {
        mImgLeft1.setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[CATEGORY_IMAGE_RESOURCE_IDS.length-1]));
        mImgCenter1.setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[0]));
        mImgRight2.setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[1]));
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (view.getId() == R.id.buttonPlay) {
                    AnimateUtils.startButtonAnimation(view, 100).setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            goToGameActivity();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                else {

                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                if (view.getId() == R.id.buttonPlay) {

                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.buttonPlay) {
            return;
        }

        // Hepsini tekrar görünür yap
        mImgLeft2.setAlpha(1f);
        mImgRight2.setAlpha(1f);
        mImgRight1.setAlpha(1f);
        mImgLeft1.setAlpha(1f);
        mImgCenter2.setAlpha(1f);

        if (v.getId() == R.id.imageButtonRight)
        {
            mImgRight2.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgRight1, AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 1 ))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, AnimationTypes.CENTER_TO_RIGHT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition + 0))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft1, AnimationTypes.LEFT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft1, mCategoryPosition + 1 ))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft2, AnimationTypes.ARISE)
                   .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 2 ))
            ;
            mCategoryPosition++;
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);
        }
        else if (v.getId() == R.id.imageButtonLeft)
        {
            mImgLeft1.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgLeft2, AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 1 ))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, AnimationTypes.CENTER_TO_LEFT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition + 0 ))
            ;
            AnimateUtils.startSliderAnimation(mImgRight2, AnimationTypes.RIGHT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight2, mCategoryPosition - 1))
            ;
            AnimateUtils.startSliderAnimation(mImgRight1, AnimationTypes.ARISE)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 2))
            ;
            mCategoryPosition--;
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);
        }

        // Label'ı güncelle
        mImgCategoryLabel.setImageResource(CATEGORY_TEXT_RESOURCE_IDS[mCategoryPosition]);
        AnimateUtils.startLabelAnimation(mImgCategoryLabel, 800);
    }

    private void goToGameActivity() {
        Intent in = new Intent(ActivityMain.this, ActivityGameLearnWords.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.putExtra(AppConstants.REASON_KEY_CATEGORY, mCategoryPosition);
        startActivity(in);
    }

    private Animation.AnimationListener setImageResourceBeforeAnimation(final View view, int position) {
        final int finalPosition = limitPositionInImageResourceLength(position);
        Logy.l("position: " + position);
        Logy.l("finalPosition: " + finalPosition);

        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isMImgCenter2Visible) {
                    mImgCenter2.setVisibility(View.GONE);
                }
                ((ImageView) view).setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[finalPosition]));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isMImgCenter2Visible) {
                    mImgCenter2.setVisibility(View.GONE);
                    isMImgCenter2Visible = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    private int limitPositionInImageResourceLength(int position) {
        if (position >= CATEGORY_IMAGE_RESOURCE_IDS.length) {
            return (CATEGORY_IMAGE_RESOURCE_IDS.length - position) *  -1; // 0'dı
        }
        else if (position < 0) {
            return (CATEGORY_IMAGE_RESOURCE_IDS.length + position) ;
        }
        return position;
    }

}
































