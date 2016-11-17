package com.example.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.utils.Util;

import java.util.ArrayList;

import mt.slider.helper.SliderHelper;
import mt.slider.interfaces.OnSliderIndexChangeListener;
import mt.slider.model.ModelSliderItem;
import mt.slider.utils.AnimateUtils;

public class ActivityMain extends Activity implements View.OnClickListener, OnSliderIndexChangeListener
{
    TextView mTextViewLabel;
    SliderHelper mSliderHelper;
    ArrayList<ModelSliderItem> mModelSliderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModelSliderItems = Util.generateModels();

        initViews();
    }

    private void initViews() {
        RelativeLayout sliderContainer = (RelativeLayout) findViewById(R.id.sliderContainer);

        // Helper (add slider view items)
        mSliderHelper = new SliderHelper(sliderContainer, ActivityMain.this, mModelSliderItems);
        mSliderHelper.setSlideButtonResources(getResources().getDrawable(R.drawable.ic_button_left), getResources().getDrawable(R.drawable.ic_button_right));
        mSliderHelper.setCustomEventListener(this);

        // Label
        mTextViewLabel = (TextView) findViewById(R.id.textViewCategoryLabel);

        // Play Button
        (findViewById(R.id.buttonPlay)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // get selected slider index
        int index = mSliderHelper.getSliderPositionIndex();
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
        Toast.makeText(getBaseContext(), "slider changed, new index is: " + newIndex, Toast.LENGTH_SHORT).show();
        updateLable(newIndex);
    }

    private void updateLable(int index) {
        mTextViewLabel.setText(mModelSliderItems.get(index).getLabel());
        mTextViewLabel.setTextColor(getResources().getColor(mModelSliderItems.get(index).getColorID()));
        AnimateUtils.startLabelAnimation(mTextViewLabel, 800);
    }
}
































