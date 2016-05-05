package com.resimlerleingilizce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.utils.AnimateUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView mImgCenter, mImgRight1, mImgRight2, mImgLeft1, mImgLeft2;

    public enum AnimationTypes {
        CENTER_TO_RIGHT,
        CENTER_TO_LEFT,
        LEFT_TO_CENTER,
        RIGHT_TO_CENTER,
        DISAPEAR,
        ARISE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mImgCenter = (ImageView) findViewById(R.id.imageViewCenter);
        mImgRight1 = (ImageView) findViewById(R.id.imageViewRight1);
        mImgLeft2 = (ImageView) findViewById(R.id.imageViewLeft1);
        mImgRight2 = (ImageView) findViewById(R.id.imageViewRight2);
        mImgLeft1 = (ImageView) findViewById(R.id.imageViewLeft2);

        findViewById(R.id.buttonRight).setOnClickListener(this);
        findViewById(R.id.buttonLeft).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mImgLeft2.setAlpha(1f);
        mImgRight1.setAlpha(1f);
        if (v.getId() == R.id.buttonRight)
        {
            mImgRight1.setAlpha(0f);
            AnimateUtils.setAnimation(mImgLeft1, AnimationTypes.LEFT_TO_CENTER);
            AnimateUtils.setAnimation(mImgCenter, AnimationTypes.CENTER_TO_RIGHT);
            AnimateUtils.setAnimation(mImgRight2, AnimationTypes.DISAPEAR).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            AnimateUtils.setAnimation(mImgLeft2, AnimationTypes.ARISE);
        }
        else if (v.getId() == R.id.buttonLeft)
        {
            mImgLeft2.setAlpha(0f);
            AnimateUtils.setAnimation(mImgLeft1, AnimationTypes.DISAPEAR).setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });;
            AnimateUtils.setAnimation(mImgCenter, AnimationTypes.CENTER_TO_LEFT);
            AnimateUtils.setAnimation(mImgRight2, AnimationTypes.RIGHT_TO_CENTER);
            AnimateUtils.setAnimation(mImgRight1, AnimationTypes.ARISE);
        }
    }




}
































