package com.resimlerleingilizce.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.resimlerleingilizce.ui.ActivityMain.*;

/**
 * Created by Mete on 23.03.2016.
 */
public class AnimateUtils {
    private final static int ANIMATION_DURATION = 200;

    private final static float ANIMATION_TRANSLATE_X_POSITION_SIDE = 370.0f;
    private final static float ANIMATION_TRANSLATE_X_POSITION_CENTER = 370.0f;
    private final static float ANIMATION_SCALE_SIZE_MULTIPLER =  1.29230769f;

    public static AnimationSet startSliderAnimation(final View view, AnimationTypes animeType) {
        view.requestLayout();
        ScaleAnimation scaleAnimation;
        TranslateAnimation translateAnimation;
        AnimationSet set;

        switch (animeType) {
            case CENTER_TO_RIGHT:
                Logy.l("startSliderAnimation CENTER_TO_RIGHT");
                // Scale
                scaleAnimation = new ScaleAnimation(1f, 1f / ANIMATION_SCALE_SIZE_MULTIPLER, 1f, 1f / ANIMATION_SCALE_SIZE_MULTIPLER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Translate
                translateAnimation = new TranslateAnimation(0.0f, ANIMATION_TRANSLATE_X_POSITION_CENTER, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                translateAnimation.setDuration(ANIMATION_DURATION);  // translateAnimation duration
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.addAnimation(translateAnimation);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            case CENTER_TO_LEFT:
                Logy.l("startSliderAnimation CENTER_TO_LEFT");
                // Scale
                scaleAnimation = new ScaleAnimation(1f, 1f / ANIMATION_SCALE_SIZE_MULTIPLER , 1f, 1f / ANIMATION_SCALE_SIZE_MULTIPLER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Translate
                translateAnimation = new TranslateAnimation(0.0f, -1.0f * ANIMATION_TRANSLATE_X_POSITION_CENTER, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                translateAnimation.setDuration(ANIMATION_DURATION);  // translateAnimation duration
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.addAnimation(translateAnimation);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            case LEFT_TO_CENTER:
                Logy.l("startSliderAnimation LEFT_TO_CENTER");
                // Scale
                scaleAnimation =  new ScaleAnimation(1f, 1f * ANIMATION_SCALE_SIZE_MULTIPLER, 1f, 1f * ANIMATION_SCALE_SIZE_MULTIPLER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Translate
                translateAnimation = new TranslateAnimation(0.0f, ANIMATION_TRANSLATE_X_POSITION_SIDE, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                translateAnimation.setDuration(ANIMATION_DURATION);  // translateAnimation duration
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.addAnimation(translateAnimation);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            case RIGHT_TO_CENTER:
                Logy.l("startSliderAnimation RIGHT_TO_CENTER");
                // Scale
                scaleAnimation =  new ScaleAnimation(1f, 1f * ANIMATION_SCALE_SIZE_MULTIPLER, 1f, 1f * ANIMATION_SCALE_SIZE_MULTIPLER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                //scaleAnimation =  new ScaleAnimation(1f, 0.92f * ANIMATION_SCALE_SIZE_MULTIPLER, 1f, 0.92f * ANIMATION_SCALE_SIZE_MULTIPLER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Translate
                translateAnimation = new TranslateAnimation(0.0f, -1.0f * ANIMATION_TRANSLATE_X_POSITION_SIDE, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                translateAnimation.setDuration(ANIMATION_DURATION);  // translateAnimation duration
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.addAnimation(translateAnimation);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            case DISAPEAR:
                Logy.l("startSliderAnimation DISAPEAR");
                // Scale
                scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(ANIMATION_DURATION);     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Fade out
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
                fadeOut.setStartOffset( Animation.RELATIVE_TO_SELF);
                fadeOut.setDuration(Math.round(ANIMATION_DURATION * 0.4f));
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.addAnimation(fadeOut);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            case ARISE:
                Logy.l("startSliderAnimation ARISE");
                // Scale
                scaleAnimation =  new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(Math.round(ANIMATION_DURATION / 1f));     // translateAnimation duration in milliseconds
                scaleAnimation.setFillAfter(true);    // If fillAfter is true, the transformation that this translateAnimation performed will persist when it is finished.
                // Animation set
                set = new AnimationSet(false); //change to false
                set.addAnimation(scaleAnimation);
                set.setFillAfter(true);

                view.setAnimation(set);
                view.startAnimation(set);

                return set;

            default:
                return null;
        }
    }

    public static AnimationSet startLabelAnimation(View view, int ANIMATION_DURATION) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(ANIMATION_DURATION );
/*
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(ANIMATION_DURATION / 2);
        fadeOut.setDuration(ANIMATION_DURATION / 2);
*/
        AnimationSet set = new AnimationSet(false); //change to false
        set.addAnimation(fadeIn);
        //set.addAnimation(fadeOut);
        view.setAnimation(set);
        view.startAnimation(set);

        return set;
    }

    public static Animation startButtonAnimation(final View view, int ANIMATION_DURATION) {
        // ImageView imageView = (ImageView) findViewById(R.id.ImageView01);
        // first 0f, 1f mean scaling from X-axis to X-axis, meaning scaling from 0-100%
        // first 0f, 1f mean scaling from Y-axis to Y-axis, meaning scaling from 0-100%
        // The two 0.5f mean animation will start from 50% of X-axis & 50% of Y-axis, i.e. from center

        ScaleAnimation scale = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(ANIMATION_DURATION);     // animation duration in milliseconds
        scale.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        //sequencial animation
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scale);
        view.setAnimation(set);
        view.startAnimation(set);

        return set;
    }

    public static Animation countDownNumberAnimation(View view, int ANIMATION_DURATION) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(ANIMATION_DURATION );

        ScaleAnimation scale = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(ANIMATION_DURATION);     // animation duration in milliseconds
        scale.setFillAfter(true);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.

        AnimationSet set = new AnimationSet(false); //change to false
        set.addAnimation(fadeIn);
        set.addAnimation(scale);
        view.setAnimation(set);
        view.startAnimation(set);

        return set;
    }
}





















