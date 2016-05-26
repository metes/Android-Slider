package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;

public class ActivityGameGuessWords extends Activity {

    ModelCard mModelCard;
    LinearLayout mContainerSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_words);

        setModel();
        initViews();
        initSelections();
    }

    private void initSelections() {
        mContainerSelection.removeAllViews();/*
        for (int i = 0; i < AppConstants.PHOTO_AR_LENGTH; i++)
        {
            RelativeLayout layoutPhoto = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_button_game_selection, null);
            setSelection(layoutPhoto, mModelCards[i]);

            angle = (i % 2 == 0) ? angle + additionalAngle : angle - (additionalAngle * 2);
            layoutPhoto.setRotation(angle);
            layoutPhoto.setOnTouchListener(new MyTouchListener());
            mRelativeLayoutPhotoContainer.addView(layoutPhoto);
            mTopImageViews[i] = layoutPhoto;
        }*/
    }

    private void initViews() {
        mContainerSelection = (LinearLayout) findViewById(R.id.containerSelections);


    }

    public void setModel() {
        int cardArIndex = 0;
        try {
            cardArIndex = getIntent().getExtras().getInt(AppConstants.REASON_KEY_ID);
        }catch (Exception e) {
            e.printStackTrace();
        }
        int[] cardAr = getIntent().getExtras().getIntArray(AppConstants.REASON_KEY_ID_ARRAY);

        mModelCard = SingletonJSON.getInstance().getData()[cardAr[cardArIndex]];
    }
}
