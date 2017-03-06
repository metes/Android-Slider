package mt.slider.helper;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import mt.slider.R;
import mt.slider.interfaces.OnSliderIndexChangeListener;
import mt.slider.model.ModelSliderItem;
import mt.slider.utils.AnimateUtils;
import mt.slider.utils.Logy;

/**
 * Created by Mete on 14.10.2016.
 */
public class SliderHelper implements View.OnTouchListener, View.OnClickListener {

    private ImageView mImgCenter1, mImgCenter2, mImgRight1, mImgRight2, mImgLeft1, mImgLeft2;
    private ImageButton mImageButtonSliderRight, mImageButtonSliderLeft;
    private RelativeLayout mSliderContainer;
    private Activity mActivity;
    private ArrayList<ModelSliderItem> mSlideItems;
    private boolean isSlideButtonAnimationActive = true;

    private boolean isMImgCenter2Visible, mIsSlideAnimationStillWorking = false;
    private int mCategoryPosition = 0, mTouchPositionX;

    private OnSliderIndexChangeListener mListener; //listener field

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

    //setting the listener
    public void setOnSliderIndexChangeListener(OnSliderIndexChangeListener eventListener) {
        this.mListener = eventListener;
    }

    public SliderHelper(Activity activity, ArrayList<ModelSliderItem> slideItems) {
        this.mSliderContainer = (RelativeLayout) activity.findViewById(R.id.sliderContainer);
        this.mActivity = activity;
        this.mSlideItems = slideItems;

        initViews();
        calculateAnimationSizes();

        if (mCategoryPosition > 0) {
            mImgCenter2.setImageDrawable(activity.getResources().getDrawable(mSlideItems.get(mCategoryPosition).getImageResourceID()));
        }
        mImgCenter2.setVisibility(View.VISIBLE);
        isMImgCenter2Visible = true;
    }

    public SliderHelper(Activity activity, ArrayList<ModelSliderItem> slideItems, boolean isSlideButtonAnimationActive) {
        this.mSliderContainer = (RelativeLayout) activity.findViewById(R.id.sliderContainer);
        this.mActivity = activity;
        this.mSlideItems = slideItems;
        this.isSlideButtonAnimationActive = isSlideButtonAnimationActive;

        initViews();
        calculateAnimationSizes();

        if (mCategoryPosition > 0) {
            mImgCenter2.setImageDrawable(activity.getResources().getDrawable(mSlideItems.get(mCategoryPosition).getImageResourceID()));
        }
        mImgCenter2.setVisibility(View.VISIBLE);
        isMImgCenter2Visible = true;
    }

    public void setSlideButtonResources(int leftButtonDrawableId, int rightButtonDrawableId) {
        mImageButtonSliderLeft.setImageResource(leftButtonDrawableId);
        mImageButtonSliderRight.setImageResource(rightButtonDrawableId);
    }

    private void initViews() {
        mImageButtonSliderRight = (ImageButton) mActivity.findViewById(R.id.imageButtonRight);
        mImageButtonSliderLeft = (ImageButton) mActivity.findViewById(R.id.imageButtonLeft);
        mImgCenter1 = (ImageView) mActivity.findViewById(R.id.imageViewCenter);
        mImgCenter2 = (ImageView) mActivity.findViewById(R.id.imageViewCenter2);
        mImgRight1 = (ImageView) mActivity.findViewById(R.id.imageViewRight1);
        mImgLeft2 = (ImageView) mActivity.findViewById(R.id.imageViewLeft1);
        mImgRight2 = (ImageView) mActivity.findViewById(R.id.imageViewRight2);
        mImgLeft1 = (ImageView) mActivity.findViewById(R.id.imageViewLeft2);

        mImageButtonSliderRight.setOnClickListener(this);
        mImageButtonSliderLeft.setOnClickListener(this);
        mSliderContainer.setOnTouchListener(this);
        mImgCenter1.setOnTouchListener(this);
        mImgCenter2.setOnTouchListener(this);
        mImgRight1.setOnTouchListener(this);
        mImgRight2.setOnTouchListener(this);
        mImgLeft1.setOnTouchListener(this);
        mImgLeft2.setOnTouchListener(this);
        mImageButtonSliderRight.setOnTouchListener(this);
        mImageButtonSliderLeft.setOnTouchListener(this);

        initImageViews();
        initSound();

        if (isSlideButtonAnimationActive) {
            AnimateUtils.startSliderSideButonAnimation(mImageButtonSliderLeft, ButtonTypes.ANIMATON_BUTTON_LEFT);
            AnimateUtils.startSliderSideButonAnimation(mImageButtonSliderRight, ButtonTypes.ANIMATON_BUTTON_RIGHT);
        }

        // for first start
        mImgCenter1.setImageDrawable(mActivity.getResources().getDrawable(mSlideItems.get(0).getImageResourceID()));
    }

    private void initImageViews() {
        mImgLeft1.setImageDrawable(mActivity.getResources().getDrawable(mSlideItems.get(1).getImageResourceID()));
        mImgCenter2.setImageDrawable(mActivity.getResources().getDrawable(mSlideItems.get(0).getImageResourceID()));
        mImgRight2.setImageDrawable(mActivity.getResources().getDrawable(mSlideItems.get(mSlideItems.size() - 1).getImageResourceID()));
    }

    @Override
    public void onClick(View v) {
//        Utils.playSound(mSoundClick, mSoundPool);

        if (v.getId() == R.id.imageButtonRight) {
            slideLeft();
        } else if (v.getId() == R.id.imageButtonLeft) {
            slideRight();
        }
    }

    @Override
    public boolean onTouch(final View view, MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //TODO slide
                mTouchPositionX = x;
                Logy.l("ontouch x: " + x);

                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                float deltaX = mTouchPositionX - x;
                Logy.l("ontouch deltaX: " + deltaX);
                if (deltaX > 200) {
                    // TODO kaydırma işlemi
                    slideLeft();
                } else if (deltaX < -200) {
                    // TODO kaydırma işlemi
                    slideRight();
                }

                break;
        }
        return false;
    }

    private void initSound() {
//        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        mSoundClick = mSoundPool.load(mActivity, R.raw.sound_click, 1);
//        mSoundCorrect = mSoundPool.load(mActivity, R.raw.sound_correct_answer, 1);
//        mSoundWrong = mSoundPool.load(mActivity, R.raw.sound_wrong_answer, 1);
//        mSoundSlide = mSoundPool.load(mActivity, R.raw.sound_photo_slide, 1);
    }

    public int getSliderPositionIndex() {
        return mCategoryPosition;
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
                    isMImgCenter2Visible = false;
                }
                ((ImageView) view).setImageDrawable(mActivity.getResources().getDrawable(
                        mSlideItems.get(finalPosition).getImageResourceID()
                ));
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

    private void calculateAnimationSizes() {

        mSliderContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = mSliderContainer.getLayoutParams().width;

        AnimateUtils.mAnimationTranslateXPosition = width / 3.8f;
        AnimateUtils.mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER = 1.29230769f;

        Logy.l("mAnimationTranslateXPosition: " + AnimateUtils.mAnimationTranslateXPosition);
        Logy.l("mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER: " + AnimateUtils.mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER);
    }

    private int limitPositionInImageResourceLength(int position) {
        if (position >= mSlideItems.size()) {
            return (mSlideItems.size() - position) * -1; // 0'dı
        } else if (position < 0) {
            return (mSlideItems.size() + position);
        }
        return position;
    }

    private void setVisibleSliderViews() {
        // Hepsini tekrar görünür yap
        mImgLeft1.setAlpha(1f);
        mImgRight2.setAlpha(1f);
    }



    private void slideLeft() {
        if (!mIsSlideAnimationStillWorking) {
            mIsSlideAnimationStillWorking = true;
            setVisibleSliderViews();

            mImgLeft1.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgLeft2, AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 1))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, SliderHelper.AnimationTypes.CENTER_TO_LEFT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition + 0))
            ;
            AnimateUtils.startSliderAnimation(mImgRight2, SliderHelper.AnimationTypes.RIGHT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight2, mCategoryPosition - 1))
            ;
            AnimateUtils.startSliderAnimation(mImgRight1, SliderHelper.AnimationTypes.ARISE)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 2))
            ;
            mCategoryPosition--;
            Logy.l((mCategoryPosition + 1) + " mCategoryPosition'den,  " + mCategoryPosition + "'e");
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);

            mIsSlideAnimationStillWorking = false;

            if(this.mListener!=null){
                this.mListener.OnSliderIndexChanged(mCategoryPosition);
            }
        }

    }

    private void slideRight() {
        if (!mIsSlideAnimationStillWorking) {
            mIsSlideAnimationStillWorking = true;
            setVisibleSliderViews();

            mImgRight2.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgRight1, SliderHelper.AnimationTypes.DISAPEAR)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 1))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, SliderHelper.AnimationTypes.CENTER_TO_RIGHT)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition + 0))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft1, SliderHelper.AnimationTypes.LEFT_TO_CENTER)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft1, mCategoryPosition + 1))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft2, SliderHelper.AnimationTypes.ARISE)
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 2))
            ;
            mCategoryPosition++;
            Logy.l((mCategoryPosition - 1) + " mCategoryPosition'den,  " + mCategoryPosition + "'e");
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);


            mIsSlideAnimationStillWorking = false;

            if(this.mListener!=null){
                this.mListener.OnSliderIndexChanged(mCategoryPosition);
            }
        }
    }


}
