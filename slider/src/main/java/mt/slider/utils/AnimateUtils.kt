package mt.slider.utils

import android.view.View
import android.view.animation.*
import mt.slider.Slider


/**
 * Created by Mete on 23.03.2016.
 */
object AnimateUtils {

    private val animationDuration = 200
    private var animationTranslateXPosition: Float = 0.toFloat()

    fun startSliderAnimation(view: View, animType: Slider.AnimationTypes, scaleSizeMultipler: Float): AnimationSet {
        view.requestLayout()
        val scaleAnimation: Animation
        var fadeOut: Animation? = null
        var translateAnimation: Animation? = null
        val animationSet = AnimationSet(false)
        when (animType) {
            Slider.AnimationTypes.CENTER_TO_RIGHT -> {
                Logy.l("sliderAnimation CENTER_TO_RIGHT ")
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f / scaleSizeMultipler)
                // Translate
                translateAnimation = getTranslateXAnimation(animationTranslateXPosition)
            }
            Slider.AnimationTypes.CENTER_TO_LEFT -> {
                Logy.l("sliderAnimation CENTER_TO_LEFT ")
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f / scaleSizeMultipler)
                // Translate
                translateAnimation = getTranslateXAnimation(-1.0f * animationTranslateXPosition)
            }
            Slider.AnimationTypes.LEFT_TO_CENTER -> {
                Logy.l("sliderAnimation LEFT_TO_CENTER ")
                // Scale
                scaleAnimation = getScaleAnimation(1f, scaleSizeMultipler)
                // Translate
                translateAnimation = getTranslateXAnimation(animationTranslateXPosition)
            }
            Slider.AnimationTypes.RIGHT_TO_CENTER -> {
                Logy.l("sliderAnimation RIGHT_TO_CENTER ")
                // Scale
                scaleAnimation = getScaleAnimation(1f, scaleSizeMultipler)
                // Translate
                translateAnimation = getTranslateXAnimation(-1.0f * animationTranslateXPosition)
            }
            Slider.AnimationTypes.DISAPEAR -> {
                Logy.l("sliderAnimation DISAPEAR")
                // Scale
                scaleAnimation = getScaleAnimation(1f, 0f)
                // Fade out
                fadeOut = AlphaAnimation(1f, 0f)
                fadeOut.interpolator = AccelerateInterpolator() // and this
                fadeOut.startOffset = Animation.RELATIVE_TO_SELF.toLong()
                fadeOut.duration = Math.round(animationDuration * 0.4f).toLong()
            }
            Slider.AnimationTypes.ARISE -> {
                Logy.l("sliderAnimation ARISE")
                // Scale
                scaleAnimation = getScaleAnimation(0f, 1f)
            }
        }
        animationSet.addAnimation(scaleAnimation)
        if (translateAnimation != null) {
            animationSet.addAnimation(translateAnimation)
        }
        if (fadeOut != null) {
            animationSet.addAnimation(fadeOut)
        }
        animationSet.fillAfter = true
        view.animation = animationSet
        view.startAnimation(animationSet)

        return animationSet
    }

    fun startSliderSideButonAnimation(view: View, buttonTypes: Slider.ButtonTypes) {
        val animations: Array<Animation>
        if (buttonTypes === Slider.ButtonTypes.ANIMATON_BUTTON_LEFT) {
            animations = arrayOf(getTranslateXAnimation(-50.5f, 500), getTranslateXAnimation(50.5f, 500))
        } else {
            animations = arrayOf(getTranslateXAnimation(50.5f, 500), getTranslateXAnimation(-50.5f, 500))
        }

        val animationSet = addAnimations(animations)
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                view.startAnimation(animationSet)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        view.startAnimation(animationSet)
    }

    fun setmAnimationTranslateXPosition(animationTranslateXPosition: Float) {
        AnimateUtils.animationTranslateXPosition = animationTranslateXPosition
    }

    fun setAnimationDuration(animationTranslateXPosition: Float) {
        AnimateUtils.animationTranslateXPosition = animationTranslateXPosition
    }

    private fun getScaleAnimation(from: Float, to: Float): Animation {
        val scale = ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = animationDuration.toLong()     // animation duration in milliseconds
        scale.fillAfter = true    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        return scale
    }

    private fun getTranslateXAnimation(toXDelta: Float): Animation {
        val translateAnimation = TranslateAnimation(0.0f, toXDelta, 0.0f, 0.0f)
        translateAnimation.duration = animationDuration.toLong()

        return translateAnimation
    }

    private fun getScaleAnimation(from: Float, to: Float, animationDuration: Int): Animation {
        val scale = ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scale.duration = animationDuration.toLong()     // animation duration in milliseconds
        scale.fillAfter = true    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        return scale
    }

    private fun getTranslateXAnimation(toXDelta: Float, animationDuration: Int): Animation {
        val translateAnimation = TranslateAnimation(0.0f, toXDelta, 0.0f, 0.0f)
        translateAnimation.duration = animationDuration.toLong()

        return translateAnimation
    }

    private fun addAnimations(animations: Array<Animation>): AnimationSet {
        val animationSet = AnimationSet(false)
        var totalAnimationDuration: Long = 0

        for (anim in animations) {
            Logy.l("totalAnimationDuration: $totalAnimationDuration")
            anim.startOffset = totalAnimationDuration
            totalAnimationDuration += anim.duration
            animationSet.addAnimation(anim)
        }

        return animationSet
    }


}





















