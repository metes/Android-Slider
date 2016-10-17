package com.example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.model.ModelSliderItem;
import com.example.R;
import com.example.helper.SliderHelper;

import java.util.ArrayList;

public class ActivityMain extends Activity //implements View.OnClickListener, View.OnTouchListener
{

    private final String[] CATEGORY_LABEL_TEXTS =  new String[]{
            "HAYVANLAR", "GİYSİLER", "DÜNYA", "YİYECEKLER", "MEYVE & SEBZE","ÜLKELER", "VÜCUT", "EŞYA" };

    private int[] CATEGORY_IMAGE_RESOURCE_IDS = {
            R.drawable.ic_category_1,
            R.drawable.ic_category_5,
            R.drawable.ic_category_4,
            R.drawable.ic_category_3,
            R.drawable.ic_category_2,
            R.drawable.ic_category_6,
            R.drawable.ic_category_7,
            R.drawable.ic_category_8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout sliderContainer = (RelativeLayout) findViewById(R.id.sliderContainer);
        SliderHelper sliderHelper = new SliderHelper(sliderContainer, ActivityMain.this, generateModels());


    }

    private ArrayList<ModelSliderItem> generateModels() {
        ArrayList<ModelSliderItem>  models = new ArrayList();
        for (int i = 0; i < CATEGORY_IMAGE_RESOURCE_IDS.length; i++) {
            models.add(new ModelSliderItem(
                    CATEGORY_LABEL_TEXTS[i],
                    CATEGORY_IMAGE_RESOURCE_IDS[i],
                    i % 2 == 0 ? R.color.category_1 : R.color.category_2 ));
        }
        return models;
    }















//    private void goToGameActivity() {
////        Intent in = new Intent(ActivityMain.this, ActivityGameLearnWords.class);
////        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        in.putExtra(AppConstants.REASON_KEY_CATEGORY, mCategoryPosition);
////        Logy.l("mCategoryPosition: " + mCategoryPosition);
////        startActivity(in);
//    }
//
//    private Animation.AnimationListener setImageResourceBeforeAnimation(final View view, int position) {
//        final int finalPosition = limitPositionInImageResourceLength(position);
//        Logy.l("position: " + position);
//        Logy.l("finalPosition: " + finalPosition);
//
//        return new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                if (isMImgCenter2Visible) {
//                    mImgCenter2.setVisibility(View.GONE);
//                    isMImgCenter2Visible = false;
//                }
//                ((ImageView) view).setImageDrawable(getResources().getDrawable(CATEGORY_IMAGE_RESOURCE_IDS[finalPosition]));
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                if (isMImgCenter2Visible) {
//                    mImgCenter2.setVisibility(View.GONE);
//                    isMImgCenter2Visible = false;
//                }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        };
//    }
//
//    private int limitPositionInImageResourceLength(int position) {
//        if (position >= CATEGORY_IMAGE_RESOURCE_IDS.length) {
//            return (CATEGORY_IMAGE_RESOURCE_IDS.length - position) *  - 1; // 0'dı
//        }
//        else if (position < 0) {
//            return (CATEGORY_IMAGE_RESOURCE_IDS.length + position) ;
//        }
//        return position;
//    }
//
//    private void initSound() {
//        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        mSoundClick = mSoundPool.load(this, R.raw.sound_click, 1);
//        mSoundCorrect = mSoundPool.load(this, R.raw.sound_correct_answer, 1);
//        mSoundWrong = mSoundPool.load(this, R.raw.sound_wrong_answer, 1);
//        mSoundSlide = mSoundPool.load(this, R.raw.sound_photo_slide, 1);
//    }



}
































