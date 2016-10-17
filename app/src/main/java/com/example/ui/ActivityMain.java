package com.example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.interfaces.OnSliderIndexChangeListener;
import com.example.model.ModelSliderItem;
import com.example.R;
import com.example.helper.SliderHelper;
import com.example.utils.AnimateUtils;

import java.util.ArrayList;

public class ActivityMain extends Activity implements View.OnClickListener, OnSliderIndexChangeListener
{

    // My models created from this variables
    private final String[] CATEGORY_LABEL_TEXTS =  new String[]{
            "Animals", "Clothes", "Earth", "Foods", "FRUITS & VEGETABLES","Countries", "Body", "Goods" };
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
    private int[] CATEGORY_LABEL_TEXT_COLORS = {
            R.color.material_amber_500,
            R.color.material_red_500,
            R.color.material_green_500,
            R.color.material_blue_500,
            R.color.material_grey_500,
            R.color.material_cyan_500,
            R.color.material_indigo_500,
            R.color.material_lime_500
    };

    private TextView mTextViewLabel;
    Button mButtonPlay;
    SliderHelper mSliderHelper;
    ArrayList<ModelSliderItem> mModelSliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout sliderContainer = (RelativeLayout) findViewById(R.id.sliderContainer);
        mModelSliderItems = generateModels();
        mSliderHelper = new SliderHelper(sliderContainer, ActivityMain.this, mModelSliderItems);
        mSliderHelper.setCustomEventListener(this);

        mTextViewLabel = (TextView) findViewById(R.id.textViewCategoryLabel);
        mButtonPlay = (Button) findViewById(R.id.buttonPlay);
        mButtonPlay.setOnClickListener(this);
    }

    private ArrayList<ModelSliderItem> generateModels() {
        ArrayList<ModelSliderItem>  models = new ArrayList();
        for (int i = 0; i < CATEGORY_IMAGE_RESOURCE_IDS.length; i++) {
            models.add(new ModelSliderItem(
                    CATEGORY_LABEL_TEXTS[i],
                    CATEGORY_IMAGE_RESOURCE_IDS[i],
                    CATEGORY_LABEL_TEXT_COLORS[i]));
        }
        return models;
    }

    @Override
    public void onClick(View v) {
        // get selected slider index
        int index = mSliderHelper.getSliderPositionIndex();
        Toast.makeText(getBaseContext(), "selected index is: " + index, Toast.LENGTH_SHORT).show();

        switch (index) {
            case 0:
                // TODO somethings
                break;
            case 1:
                // TODO somethings
                break;
            default:
                // TODO other things
                break;
        }
    }

    @Override
    public void OnSliderIndexChanged(int newIndex) {
        Toast.makeText(getBaseContext(), "slider changed, new index is: " + mSliderHelper.getSliderPositionIndex(), Toast.LENGTH_SHORT).show();
        updateLable(newIndex);
    }

    private void updateLable(int index) {
//        int index = isIncresed ? mSliderHelper.getSliderPositionIndex() +1 :  mSliderHelper.getSliderPositionIndex() - 1;

//        if (index >= mModelSliderItems.size()) {
//            index = 0;
//        } else  if (index < 0) {
//            index = mModelSliderItems.size() -1;
//        }

        // Label'ı güncelle
        mTextViewLabel.setText(mModelSliderItems.get(index).getLabel());
        mTextViewLabel.setTextColor(getResources().getColor(mModelSliderItems.get(index).getColorID()));
        AnimateUtils.startLabelAnimation(mTextViewLabel, 800);
    }
}
































