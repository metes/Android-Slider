package com.example.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import java.util.ArrayList;

import mt.slider.helper.SliderHelper;
import mt.slider.interfaces.OnSliderIndexChangeListener;
import mt.slider.model.ModelSliderItem;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, OnSliderIndexChangeListener {

    private SliderHelper mSliderHelper;
    private TextView mTextViewLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewLabel = (TextView) findViewById(R.id.textViewCategoryLabel);
        mTextViewLabel.setText("Label: " + 0);

        // Helper (add slider view items)
        mSliderHelper = new SliderHelper(MainActivity.this, generateItems(), false);
        mSliderHelper.setCustomEventListener(this);
    }

    private ArrayList<ModelSliderItem> generateItems() {
        ArrayList<ModelSliderItem> items = new ArrayList<>();
        int[] colorIds = {
                android.R.color.holo_blue_bright,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_light,
        };
        int[] imageResourceIds = {
                android.R.drawable.ic_dialog_alert,
                android.R.drawable.ic_menu_agenda,
                android.R.drawable.ic_menu_gallery,
                android.R.drawable.ic_menu_my_calendar,
                android.R.drawable.ic_menu_compass
        };

        for (int i = 0; i < 5; i++) {
            items.add(new ModelSliderItem(
                    "Label " + i,
                    imageResourceIds[i],
                    colorIds[i]
            ));
        }
        return items;
    }

    @Override
    public void OnSliderIndexChanged(int newIndex) {
        Toast.makeText(getBaseContext(),
                "Slider changed, new index is: " + newIndex, Toast.LENGTH_SHORT)
                .show();
        mTextViewLabel.setText("Label: " + newIndex);
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
