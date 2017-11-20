package mt.slider.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import mt.slider.R;
import mt.slider.interfaces.OnSliderIndexChangeListener;
import mt.slider.model.SliderItem;
import mt.slider.utils.AnimateUtils;
import mt.slider.utils.Logy;

/**
 * Created by Mete on 14.10.2016.
 */
public class SliderHelper implements View.OnTouchListener, View.OnClickListener {

    private ImageView mImgCenter1, mImgCenter2, mImgRight1, mImgRight2, mImgLeft1, mImgLeft2;
    private ImageButton mImageButtonSliderRight, mImageButtonSliderLeft;
    private RelativeLayout mSliderContainer;
    private Context mContext;
    private ArrayList<SliderItem> mSlideItems;
    private boolean isSlideButtonAnimationActive = true;

    private boolean isMImgCenter2Visible, isSlideAnimationStillWorking = false;
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

    public SliderHelper(Context context, ArrayList<SliderItem> slideItems, View slider) {
        this.mSliderContainer = slider.findViewById(R.id.sliderContainer);
        this.mContext = context;
        this.mSlideItems = slideItems;

        initViews();
        calculateAnimationSizes();

        if (mCategoryPosition > 0) {
            mImgCenter2.setImageDrawable(context.getResources().getDrawable(mSlideItems.get(mCategoryPosition).getImageResourceID()));
        }
        mImgCenter2.setVisibility(View.VISIBLE);
        isMImgCenter2Visible = true;

        mImageButtonSliderLeft.setImageResource(android.R.drawable.ic_media_rew);
        mImageButtonSliderRight.setImageResource(android.R.drawable.ic_media_ff);
    }

    public SliderHelper(Context context, ArrayList<SliderItem> slideItems, boolean isSlideButtonAnimationActive, View slider) {
        this.mSliderContainer = slider.findViewById(R.id.sliderContainer);
        this.mContext = context;
        this.mSlideItems = slideItems;
        this.isSlideButtonAnimationActive = isSlideButtonAnimationActive;

        initViews();
        calculateAnimationSizes();

        if (mCategoryPosition > 0) {
            mImgCenter2.setImageDrawable(context.getResources().getDrawable(mSlideItems.get(mCategoryPosition).getImageResourceID()));
        }
        mImgCenter2.setVisibility(View.VISIBLE);
        isMImgCenter2Visible = true;

        mImageButtonSliderLeft.setImageResource(android.R.drawable.ic_media_rew);
        mImageButtonSliderRight.setImageResource(android.R.drawable.ic_media_ff);
    }

    public void setSlideButtonResources(int leftButtonDrawableId, int rightButtonDrawableId) {
        mImageButtonSliderLeft.setImageResource(leftButtonDrawableId);
        mImageButtonSliderRight.setImageResource(rightButtonDrawableId);
    }

    public ImageButton getmImageButtonSliderRight() {
        return mImageButtonSliderRight;
    }

    public ImageButton getmImageButtonSliderLeft() {
        return mImageButtonSliderLeft;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        mImageButtonSliderRight = mSliderContainer.findViewById(R.id.imageButtonRight);
        mImageButtonSliderLeft = mSliderContainer.findViewById(R.id.imageButtonLeft);
        mImgCenter1 = mSliderContainer.findViewById(R.id.imageViewCenter);
        mImgCenter2 = mSliderContainer.findViewById(R.id.imageViewCenter2);
        mImgRight1 = mSliderContainer.findViewById(R.id.imageViewRight1);
        mImgLeft2 = mSliderContainer.findViewById(R.id.imageViewLeft1);
        mImgRight2 = mSliderContainer.findViewById(R.id.imageViewRight2);
        mImgLeft1 = mSliderContainer.findViewById(R.id.imageViewLeft2);

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
        initImageSizes();

        if (isSlideButtonAnimationActive) {
            AnimateUtils.startSliderSideButonAnimation(mImageButtonSliderLeft, ButtonTypes.ANIMATON_BUTTON_LEFT);
            AnimateUtils.startSliderSideButonAnimation(mImageButtonSliderRight, ButtonTypes.ANIMATON_BUTTON_RIGHT);
        }
        // for first start
        mImgCenter1.setImageDrawable(mContext.getResources().getDrawable(mSlideItems.get(0).getImageResourceID()));
    }

    private void initImageSizes() {
        View[] mSmall = new View[]{
                mImgRight1, mImgRight2, mImgLeft1, mImgLeft2
        };
        View[] mBig = new View[]{
                mImgCenter1, mImgCenter2
        };
        for (View imgBtn : mSmall) {
            imgBtn.getLayoutParams().width = mSlideItems.get(0).getSizeSmall();//(totalW / buttonCount);
        }
        for (View imgBtn : mBig) {
            imgBtn.getLayoutParams().width = mSlideItems.get(0).getSizeBig();//(totalW / buttonCount);
        }

    }

    private void initImageViews() {
        mImgLeft1.setImageDrawable(mContext.getResources().getDrawable(mSlideItems.get(1).getImageResourceID()));
        mImgCenter2.setImageDrawable(mContext.getResources().getDrawable(mSlideItems.get(0).getImageResourceID()));
        mImgRight2.setImageDrawable(mContext.getResources().getDrawable(mSlideItems.get(mSlideItems.size() - 1).getImageResourceID()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButtonRight) {
            slideLeft();
        } else if (v.getId() == R.id.imageButtonLeft) {
            slideRight();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
                    slideLeft();
                } else if (deltaX < -200) {
                    slideRight();
                }
                break;
        }
        return false;
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
                ((ImageView) view).setImageDrawable(mContext.getResources().getDrawable(
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
        AnimateUtils.mAnimationTranslateXPosition = mSlideItems.get(0).getSizeSmall();
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
        if (!isSlideAnimationStillWorking) {
            isSlideAnimationStillWorking = true;
            setVisibleSliderViews();

            mImgLeft1.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgLeft2, AnimationTypes.DISAPEAR, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 1))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, SliderHelper.AnimationTypes.CENTER_TO_LEFT, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition))
            ;
            AnimateUtils.startSliderAnimation(mImgRight2, SliderHelper.AnimationTypes.RIGHT_TO_CENTER, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight2, mCategoryPosition - 1))
            ;
            AnimateUtils.startSliderAnimation(mImgRight1, SliderHelper.AnimationTypes.ARISE, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 2))
            ;
            mCategoryPosition--;
            Logy.l("from: " + (mCategoryPosition + 1) + ", to: " + mCategoryPosition);
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);

            isSlideAnimationStillWorking = false;

            if (this.mListener != null) {
                this.mListener.OnSliderIndexChanged(mCategoryPosition);
            }
        }
    }

    private void slideRight() {
        if (!isSlideAnimationStillWorking) {
            isSlideAnimationStillWorking = true;
            setVisibleSliderViews();

            mImgRight2.setAlpha(0f);
            AnimateUtils.startSliderAnimation(mImgRight1, SliderHelper.AnimationTypes.DISAPEAR, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgRight1, mCategoryPosition - 1))
            ;
            AnimateUtils.startSliderAnimation(mImgCenter1, SliderHelper.AnimationTypes.CENTER_TO_RIGHT, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgCenter1, mCategoryPosition))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft1, SliderHelper.AnimationTypes.LEFT_TO_CENTER, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft1, mCategoryPosition + 1))
            ;
            AnimateUtils.startSliderAnimation(mImgLeft2, SliderHelper.AnimationTypes.ARISE, mSlideItems.get(0).getSizeRaito())
                    .setAnimationListener(setImageResourceBeforeAnimation(mImgLeft2, mCategoryPosition + 2))
            ;
            mCategoryPosition++;
            Logy.l("from: " + (mCategoryPosition - 1) + ", to: " + mCategoryPosition);
            mCategoryPosition = limitPositionInImageResourceLength(mCategoryPosition);

            isSlideAnimationStillWorking = false;

            if (this.mListener != null) {
                this.mListener.OnSliderIndexChanged(mCategoryPosition);
            }
        }
    }


}
