package mt.slider.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import mt.slider.helper.SliderHelper;


/**
 * Created by Mete on 23.03.2016.
 */
public class AnimateUtils {

    private final static int ANIMATION_DURATION = 200;
    public static float mAnimationTranslateXPosition;
    public static float mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER;

    public static AnimationSet startSliderAnimation(final View view, SliderHelper.AnimationTypes animeType) {
        view.requestLayout();
        Animation scaleAnimation;
        Animation translateAnimation;
        AnimationSet animationSet;

        switch (animeType) {
            case CENTER_TO_RIGHT:
                Logy.l("startSliderAnimation CENTER_TO_RIGHT");
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f / mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER, ANIMATION_DURATION);
                // Translate
                translateAnimation = getTranslateXAnimation(mAnimationTranslateXPosition, ANIMATION_DURATION);
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            case CENTER_TO_LEFT:
                Logy.l("startSliderAnimation CENTER_TO_LEFT");
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f / mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER, ANIMATION_DURATION);
                // Translate
                translateAnimation = getTranslateXAnimation(-1.0f * mAnimationTranslateXPosition, ANIMATION_DURATION);
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            case LEFT_TO_CENTER:
                Logy.l("startSliderAnimation LEFT_TO_CENTER");
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f * mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER, ANIMATION_DURATION);
               // Translate
                translateAnimation = getTranslateXAnimation(mAnimationTranslateXPosition, ANIMATION_DURATION);
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            case RIGHT_TO_CENTER:
                Logy.l("startSliderAnimation RIGHT_TO_CENTER");
                // Scale
                scaleAnimation = getScaleAnimation(1f, 1f * mAnimationScaleSizeMultiplerANIMATION_SCALE_SIZE_MULTIPLER, ANIMATION_DURATION);
                // Translate
                translateAnimation = getTranslateXAnimation(-1.0f * mAnimationTranslateXPosition, ANIMATION_DURATION);
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            case DISAPEAR:
                Logy.l("startSliderAnimation DISAPEAR");
                // Scale
                scaleAnimation = getScaleAnimation(1f, 0f, ANIMATION_DURATION);
                // Fade out
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
                fadeOut.setStartOffset( Animation.RELATIVE_TO_SELF);
                fadeOut.setDuration(Math.round(ANIMATION_DURATION * 0.4f));
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(fadeOut);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            case ARISE:
                Logy.l("startSliderAnimation ARISE");
                // Scale
                scaleAnimation = getScaleAnimation(0f, 1f, ANIMATION_DURATION);
                // Animation animationSet
                animationSet = new AnimationSet(false); //change to false
                animationSet.addAnimation(scaleAnimation);
                animationSet.setFillAfter(true);

                view.setAnimation(animationSet);
                view.startAnimation(animationSet);

                return animationSet;

            default:
                return null;
        }
    }

    public static Animation startSliderSideButonAnimation(final View view, SliderHelper.ButtonTypes buttonTypes) {
        Animation[] animations;
        if (buttonTypes == SliderHelper.ButtonTypes.ANIMATON_BUTTON_LEFT) {
            animations = new Animation[]{
                    getTranslateXAnimation(-50.5f, 500),
                    getTranslateXAnimation(50.5f, 500)
            };
        }
        else {
            animations = new Animation[]{
                    getTranslateXAnimation(50.5f, 500),
                    getTranslateXAnimation(-50.5f, 500)
            };
        }

        final AnimationSet animationSet = addAnimations(animations);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animationSet);
        return animationSet;
    }

    private static Animation getScaleAnimation(float from, float to, int animationDuration) {
        Animation scale = new ScaleAnimation(from, to, from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(animationDuration);     // animation duration in milliseconds
        scale.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        return scale;
    }

    private static Animation getTranslateXAnimation(float toXDelta, int animationDuration) {
        Animation translateAnimation = new TranslateAnimation(0.0f, toXDelta, 0.0f, 0.0f);
        translateAnimation.setDuration(animationDuration);

        return translateAnimation;
    }

    private static AnimationSet addAnimations(Animation[] animations) {
        AnimationSet animationSet = new AnimationSet(false);
        long totalAnimationDuration = 0;

        for (int i = 0; i < animations.length; i++)
        {
            Logy.l("totalAnimationDuration: " + totalAnimationDuration);
            Animation a = animations[i];
            a.setStartOffset(totalAnimationDuration);
            totalAnimationDuration += a.getDuration();
            animationSet.addAnimation(a);
        }

        return animationSet;
    }


}





















