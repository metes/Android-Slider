package mt.slider

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import mt.slider.interfaces.OnSliderIndexChangeListener
import mt.slider.model.SliderItem
import mt.slider.utils.AnimateUtils
import mt.slider.utils.AnimateUtils.startSliderAnimation
import mt.slider.utils.Logy
import java.util.*

/**
 * Created by Mete on 14.10.2016.
 */
class Slider : View.OnTouchListener, View.OnClickListener {

    var sliderPositionIndex = 0
    lateinit var imageViewCenter1: ImageView
    lateinit var imageButtonSliderRight: ImageButton
    lateinit var imageButtonSliderLeft: ImageButton

    private lateinit var indexChangeListener: OnSliderIndexChangeListener
    private lateinit var imageViewCenter2: ImageView
    private lateinit var imageViewRight1: ImageView
    private lateinit var imageViewRight2: ImageView
    private lateinit var imageViewLeft1: ImageView
    private lateinit var imageViewLeft2: ImageView

    private var sliderContainer: RelativeLayout
    private var slideItemList: ArrayList<SliderItem>
    private var isImageCenter2Visible: Boolean = false
    private var isSlideAnimationStillWorking = false
    private var mTouchPositionX: Int = 0
    private var mContext: Context
    private var isSlideButtonAnimationActive = true

    enum class AnimationTypes {
        CENTER_TO_RIGHT,
        CENTER_TO_LEFT,
        LEFT_TO_CENTER,
        RIGHT_TO_CENTER,
        DISAPEAR,
        ARISE
    }

    enum class ButtonTypes {
        ANIMATON_BUTTON_LEFT,
        ANIMATON_BUTTON_RIGHT
    }

    //setting the listener
    fun setOnSliderIndexChangeListener(eventListener: OnSliderIndexChangeListener) {
        this.indexChangeListener = eventListener
    }

    constructor(context: Context, slideItems: ArrayList<SliderItem>, slider: View) {
        this.sliderContainer = slider.findViewById(R.id.sliderContainer)
        this.mContext = context
        this.slideItemList = slideItems

        initViews()
        calculateAnimationSizes()

        if (sliderPositionIndex > 0) {
            imageViewCenter2.setImageDrawable(
                    ContextCompat.getDrawable(context, slideItemList[sliderPositionIndex].imageResourceID))
        }
        imageViewCenter2.visibility = View.VISIBLE
        isImageCenter2Visible = true

        imageButtonSliderLeft.setImageResource(android.R.drawable.ic_media_rew)
        imageButtonSliderRight.setImageResource(android.R.drawable.ic_media_ff)
    }

    constructor(context: Context, slideItems: ArrayList<SliderItem>, isSlideButtonAnimationActive: Boolean, slider: View) {
        this.sliderContainer = slider.findViewById(R.id.sliderContainer)
        this.mContext = context
        this.slideItemList = slideItems
        this.isSlideButtonAnimationActive = isSlideButtonAnimationActive

        initViews()
        calculateAnimationSizes()

        if (sliderPositionIndex > 0) {
            imageViewCenter2.setImageDrawable(
                    ContextCompat.getDrawable(context, slideItemList[sliderPositionIndex].imageResourceID)
            )
        }
        imageViewCenter2.visibility = View.VISIBLE
        isImageCenter2Visible = true

        imageButtonSliderLeft.setImageResource(android.R.drawable.ic_media_rew)
        imageButtonSliderRight.setImageResource(android.R.drawable.ic_media_ff)
    }

    fun setSlideButtonResources(leftButtonDrawableId: Int, rightButtonDrawableId: Int) {
        imageButtonSliderLeft.setImageResource(leftButtonDrawableId)
        imageButtonSliderRight.setImageResource(rightButtonDrawableId)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initViews() {
        imageButtonSliderRight = sliderContainer.findViewById(R.id.imageButtonRight)
        imageButtonSliderLeft = sliderContainer.findViewById(R.id.imageButtonLeft)
        imageViewCenter1 = sliderContainer.findViewById(R.id.imageViewCenter)
        imageViewCenter2 = sliderContainer.findViewById(R.id.imageViewCenter2)
        imageViewRight1 = sliderContainer.findViewById(R.id.imageViewRight1)
        imageViewLeft2 = sliderContainer.findViewById(R.id.imageViewLeft1)
        imageViewRight2 = sliderContainer.findViewById(R.id.imageViewRight2)
        imageViewLeft1 = sliderContainer.findViewById(R.id.imageViewLeft2)

        imageButtonSliderRight.setOnClickListener(this)
        imageButtonSliderLeft.setOnClickListener(this)
        sliderContainer.setOnTouchListener(this)
        imageViewCenter1.setOnTouchListener(this)
        imageViewCenter2.setOnTouchListener(this)
        imageViewRight1.setOnTouchListener(this)
        imageViewRight2.setOnTouchListener(this)
        imageViewLeft1.setOnTouchListener(this)
        imageViewLeft2.setOnTouchListener(this)
        imageButtonSliderRight.setOnTouchListener(this)
        imageButtonSliderLeft.setOnTouchListener(this)

        initImageViews()
        initImageSizes()

        if (isSlideButtonAnimationActive) {
            AnimateUtils.startSliderSideButonAnimation(imageButtonSliderLeft, ButtonTypes.ANIMATON_BUTTON_LEFT)
            AnimateUtils.startSliderSideButonAnimation(imageButtonSliderRight, ButtonTypes.ANIMATON_BUTTON_RIGHT)
        }
        // for first start
        imageViewCenter1.setImageDrawable(
                ContextCompat.getDrawable(mContext, slideItemList[0].imageResourceID)
        )
    }

    private fun initImageSizes() {
        val mSmall = arrayOf<View>(imageViewRight1, imageViewRight2, imageViewLeft1, imageViewLeft2)
        val mBig = arrayOf<View>(imageViewCenter1, imageViewCenter2)
        for (imgBtn in mSmall) {
            imgBtn.layoutParams.width = slideItemList[0].sizeSmall//(totalW / buttonCount);
        }
        for (imgBtn in mBig) {
            imgBtn.layoutParams.width = slideItemList[0].sizeBig//(totalW / buttonCount);
        }

    }

    private fun initImageViews() {
        imageViewLeft1.setImageDrawable(
                ContextCompat.getDrawable(mContext, slideItemList[1].imageResourceID))
        imageViewCenter2.setImageDrawable(
                ContextCompat.getDrawable(mContext, slideItemList[0].imageResourceID))
        imageViewRight2.setImageDrawable(
                ContextCompat.getDrawable(mContext, slideItemList[slideItemList.size - 1].imageResourceID))
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imageButtonRight) {
            slideLeft()
        } else if (v.id == R.id.imageButtonLeft) {
            slideRight()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //TODO slide
                mTouchPositionX = x
                Logy.l("ontouch x: $x")
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                val deltaX = (mTouchPositionX - x).toFloat()
                Logy.l("ontouch deltaX: $deltaX")
                if (deltaX > 200) {
                    slideLeft()
                } else if (deltaX < -200) {
                    slideRight()
                }
            }
        }
        return false
    }

    private fun setImageResBeforeAnimation(view: View, position: Int): Animation.AnimationListener {
        val finalPosition = limitPositionInImageResourceLength(position)
        Logy.l("position: $position")
        Logy.l("finalPosition: $finalPosition")

        return object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (isImageCenter2Visible) {
                    imageViewCenter2.visibility = View.GONE
                    isImageCenter2Visible = false
                }
                (view as ImageView).setImageDrawable(
                        ContextCompat.getDrawable(mContext, slideItemList[finalPosition].imageResourceID))
            }

            override fun onAnimationEnd(animation: Animation) {
                if (isImageCenter2Visible) {
                    imageViewCenter2.visibility = View.GONE
                    isImageCenter2Visible = false
                }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        }
    }

    private fun calculateAnimationSizes() {
        AnimateUtils.setmAnimationTranslateXPosition(slideItemList[0].sizeSmall.toFloat())
    }

    private fun limitPositionInImageResourceLength(position: Int): Int {
        if (position >= slideItemList.size) {
            return (slideItemList.size - position) * -1 // 0'dı
        } else if (position < 0) {
            return slideItemList.size + position
        }
        return position
    }

    private fun setVisibleSliderViews() {
        // Hepsini tekrar görünür yap
        imageViewLeft1.alpha = 1f
        imageViewRight2.alpha = 1f
    }

    /**
     *  Left button action
     */
    fun slideLeft() {
        if (!isSlideAnimationStillWorking) {
            isSlideAnimationStillWorking = true
            setVisibleSliderViews()

            imageViewLeft1.alpha = 0f
            startSliderAnimation(imageViewLeft2, AnimationTypes.DISAPEAR, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewLeft2, sliderPositionIndex + 1))
            startSliderAnimation(imageViewCenter1, AnimationTypes.CENTER_TO_LEFT, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewCenter1, sliderPositionIndex))
            startSliderAnimation(imageViewRight2, AnimationTypes.RIGHT_TO_CENTER, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewRight2, sliderPositionIndex - 1))
            startSliderAnimation(imageViewRight1, AnimationTypes.ARISE, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewRight1, sliderPositionIndex - 2))
            sliderPositionIndex--
            Logy.l("from: " + (sliderPositionIndex + 1) + ", to: " + sliderPositionIndex)
            sliderPositionIndex = limitPositionInImageResourceLength(sliderPositionIndex)

            isSlideAnimationStillWorking = false
            this.indexChangeListener.OnSliderIndexChanged(sliderPositionIndex)
        }

    }

    /**
     *  Right button action
     */
    fun slideRight() {
        if (!isSlideAnimationStillWorking) {
            isSlideAnimationStillWorking = true
            setVisibleSliderViews()

            imageViewRight2.alpha = 0f
            startSliderAnimation(imageViewRight1, AnimationTypes.DISAPEAR, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewRight1, sliderPositionIndex - 1))
            startSliderAnimation(imageViewCenter1, AnimationTypes.CENTER_TO_RIGHT, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewCenter1, sliderPositionIndex))
            startSliderAnimation(imageViewLeft1, AnimationTypes.LEFT_TO_CENTER, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewLeft1, sliderPositionIndex + 1))
            startSliderAnimation(imageViewLeft2, AnimationTypes.ARISE, slideItemList[0].sizeRatio)
                    .setAnimationListener(setImageResBeforeAnimation(imageViewLeft2, sliderPositionIndex + 2))
            sliderPositionIndex++
            Logy.l("from: " + (sliderPositionIndex - 1) + ", to: " + sliderPositionIndex)
            sliderPositionIndex = limitPositionInImageResourceLength(sliderPositionIndex)

            isSlideAnimationStillWorking = false
            this.indexChangeListener.OnSliderIndexChanged(sliderPositionIndex)
        }
    }


}
