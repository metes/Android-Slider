package com.example.ui;

import android.net.Uri;
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

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlankFragment fragment = new BlankFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
