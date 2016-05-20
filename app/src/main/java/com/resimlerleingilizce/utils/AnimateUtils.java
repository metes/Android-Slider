package com.resimlerleingilizce.utils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.resimlerleingilizce.ui.MainActivity.*;

/**
 * Created by Mete on 23.03.2016.
 */
public class AnimateUtils {
    private final static int ANIMATION_DURATION = 1000;
//    public static float ANIMATION_TRANSLATE_X_POSITION_CENTER = 500f;
//    public static float ANIMATION_TRANSLATE_X_POSITION_SIDE = 495f;
//    public final static float ANIMATION_SCALE_SIZE_MULTIPLER =  1.80f;

    private final static float ANIMATION_TRANSLATE_X_POSITION_SIDE = 80.0f;
    private final static float ANIMATION_TRANSLATE_X_POSITION_CENTER = 80.0f;
    private final static float ANIMATION_SCALE_SIZE_MULTIPLER =  1.4f;

    public static AnimationSet setAnimation(final View view, AnimationTypes animeType) {
        view.requestLayout();
        ScaleAnimation scaleAnimation;
        TranslateAnimation translateAnimation;
        AnimationSet set;

        switch (animeType) {
            case CENTER_TO_RIGHT:
                Logy.l("setAnimation CENTER_TO_RIGHT");
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
                Logy.l("setAnimation CENTER_TO_LEFT");
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
                Logy.l("setAnimation LEFT_TO_CENTER");
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
                Logy.l("setAnimation RIGHT_TO_CENTER");
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
                Logy.l("setAnimation DISAPEAR");
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
                Logy.l("setAnimation ARISE");
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
}





















