package com.example.utils;

import java.util.ArrayList;

import mt.slider.model.ModelSliderItem;

import static com.example.constants.SliderConstant.CATEGORY_IMAGE_RESOURCE_IDS;
import static com.example.constants.SliderConstant.CATEGORY_LABEL_TEXTS;
import static com.example.constants.SliderConstant.CATEGORY_LABEL_TEXT_COLORS;

/**
 * Created by mete_ on 18.11.2016.
 */

public class Util {


    public static ArrayList<ModelSliderItem> generateModels() {
        ArrayList<ModelSliderItem>  models = new ArrayList();
        for (int i = 0; i < CATEGORY_IMAGE_RESOURCE_IDS.length; i++) {
            models.add(new ModelSliderItem(
                    CATEGORY_LABEL_TEXTS[i],
                    CATEGORY_IMAGE_RESOURCE_IDS[i],
                    CATEGORY_LABEL_TEXT_COLORS[i]));
        }
        return models;
    }
}
