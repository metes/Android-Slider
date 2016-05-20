package com.resimlerleingilizce.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Logy;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int mCategoryPosition;
    ImageView mImgCenter, mImgRight1, mImgRight2, mImgLeft1, mImgLeft2;
    int[] IMAGE_RESOURCE_IDS = { R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3, R.drawable.ic_category_4, R.drawable.ic_category_5 };

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
        mCategoryPosition = 0;

        initViews();
    }

    private void initViews() {
        mImgCenter = (ImageView) findViewById(R.id.imageViewCenter);
        mImgRight1 = (ImageView) findViewById(R.id.imageViewRight1);
        mImgLeft2 = (ImageView) findViewById(R.id.imageViewLeft1);
        mImgRight2 = (ImageView) findViewById(R.id.imageViewRight2);
        mImgLeft1 = (ImageView) findViewById(R.id.imageViewLeft2);

        //ANIMATION_SCALE_SIZE_MULTIPLER
//        mImgCenter.getLayoutParams().width = Math.round(mImgCenter.getLayoutParams().width * AnimateUtils.ANIMATION_SCALE_SIZE_MULTIPLER);
//        mImgCenter.getLayoutParams().height = Math.round(mImgCenter.getLayoutParams().height * AnimateUtils.ANIMATION_SCALE_SIZE_MULTIPLER);

        findViewById(R.id.buttonRight).setOnClickListener(this);
        findViewById(R.id.buttonLeft).setOnClickListener(this);

        initImageViews();
    }

    private void initImageViews() {
        mImgLeft1.setImageDrawable(getResources().getDrawable(IMAGE_RESOURCE_IDS[IMAGE_RESOURCE_IDS.length-1]));
        mImgCenter.setImageDrawable(getResources().getDrawable(IMAGE_RESOURCE_IDS[0]));
        mImgRight2.setImageDrawable(getResources().getDrawable(IMAGE_RESOURCE_IDS[1]));
    }

    @Override
    public void onClick(View v) {
        // 2'ler center'ın üzerinde, 1'ler altında kalıyor
        mImgLeft2.setAlpha(1f);
        mImgRight1.setAlpha(1f);
        if (v.getId() == R.id.buttonRight)
        {
            mImgRight1.setAlpha(0f);
            AnimateUtils.setAnimation(mImgRight2, AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight2, mCategoryPosition - 1 ));

            AnimateUtils.setAnimation(mImgCenter, AnimationTypes.CENTER_TO_RIGHT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter, mCategoryPosition + 0))
            ;

            AnimateUtils.setAnimation(mImgLeft1, AnimationTypes.LEFT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft1, mCategoryPosition + 1 ))
            ;

            AnimateUtils.setAnimation(mImgLeft2, AnimationTypes.ARISE)
                   .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 2 ))
            ;
            mCategoryPosition++;
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);
        }
        else if (v.getId() == R.id.buttonLeft)
        {
            mImgLeft2.setAlpha(0f);
            AnimateUtils.setAnimation(mImgLeft1, AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft1, mCategoryPosition + 1 ))
            ;

            AnimateUtils.setAnimation(mImgCenter, AnimationTypes.CENTER_TO_LEFT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter, mCategoryPosition + 0 ))
            ;

            AnimateUtils.setAnimation(mImgRight2, AnimationTypes.RIGHT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight2, mCategoryPosition - 1))
            ;

            AnimateUtils.setAnimation(mImgRight1, AnimationTypes.ARISE)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 2))
            ;
            mCategoryPosition--;
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);
        }
    }


    public Animation.AnimationListener setImageResourceBeforeAnimation(final View view, int position) {
        Logy.l("position: " + position);
        final int finalPosition = limitPositionInImageResourceLength(position);
        Logy.l("finalPosition: " + finalPosition);
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ((ImageView) view).setImageDrawable(getResources().getDrawable(IMAGE_RESOURCE_IDS[finalPosition]));
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    public int limitPositionInImageResourceLength(int position) {
        if (position >= IMAGE_RESOURCE_IDS.length) {
            return (IMAGE_RESOURCE_IDS.length - position) *  -1; // 0'dı
        }
        else if (position < 0) {
            return (IMAGE_RESOURCE_IDS.length + position) ;
        }
        return position;
    }

}
































