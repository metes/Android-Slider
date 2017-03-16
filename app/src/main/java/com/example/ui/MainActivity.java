package com.example.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.R;

import java.util.ArrayList;

import mt.slider.helper.SliderHelper;
import mt.slider.interfaces.OnSliderIndexChangeListener;
import mt.slider.model.SliderItem;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, OnSliderIndexChangeListener {

    private SliderHelper mSliderHelper;
    private TextView mTextViewLabel;
    private ArrayList<SliderItem> mSliderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSliderItemList = generateItems();

        mTextViewLabel = (TextView) findViewById(R.id.textViewCategoryLabel);
        updateLabel(0);

        // Helper (add slider view items)
        mSliderHelper = new SliderHelper(MainActivity.this, mSliderItemList, true);
        mSliderHelper.setOnSliderIndexChangeListener(this);
        mSliderHelper.setSlideButtonResources(R.drawable.ic_button_left, R.drawable.ic_button_right);
    }

    private ArrayList<SliderItem> generateItems() {
        ArrayList<SliderItem> items = new ArrayList<>();
        int[] colorResourceIds = {
                android.R.color.holo_blue_bright,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
        };
        int[] imageResourceIds = {
                R.drawable.ic_category_1,
                R.drawable.ic_category_2,
                R.drawable.ic_category_3,
                R.drawable.ic_category_4,
                R.drawable.ic_category_5,
        };

        for (int i = 0; i < 5; i++) {
            items.add(new SliderItem(
                    "Label " + i,
                    imageResourceIds[i],
                    colorResourceIds[i]
            ));
        }
        return items;
    }

    private void updateLabel(int newIndex) {
        mTextViewLabel.setText(mSliderItemList.get(newIndex).getLabel());
        mTextViewLabel.setTextColor(getResources().getColor(mSliderItemList.get(newIndex).getColorID()));
    }

    @Override
    public void OnSliderIndexChanged(int newIndex) {
        Log.d(MainActivity.class.getSimpleName(), "OnSliderIndexChanged newIndex: " + newIndex);
        updateLabel(newIndex);
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
}
