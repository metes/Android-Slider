package com.resimlerleingilizce.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.constants.AppConstants;
import com.resimlerleingilizce.model.ModelCard;
import com.resimlerleingilizce.singletons.SingletonJSON;
import com.resimlerleingilizce.utils.AnimateUtils;
import com.resimlerleingilizce.utils.Logy;
import com.resimlerleingilizce.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ActivityGameGuessWords extends Activity implements View.OnClickListener{

    private Typeface mTtypeface1, mTtypeface2;
    ModelCard mModelCard;
    LinearLayout mContainerSelection;
    int[] mRandomIDs, mIdForPeriodAr;
    TextView mTextViewLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_guess_words);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "coopbl.ttf");
        mTtypeface2 = Typeface.createFromAsset(getAssets(), "homework_normal.ttf");

        initParams();
        initViews();
        initSelections();
    }

    private void initSelections() {
        mContainerSelection.removeAllViews();
        mRandomIDs = getRandomIDs();
        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            RelativeLayout layoutSelection = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_button_game_selection, null);
            layoutSelection.setOnClickListener(this);
            initButtonSelection(layoutSelection, i);
            mContainerSelection.addView(layoutSelection);
        }
    }

    private void initButtonSelection(View parent, int index) {
        ModelCard model = Utils.getModelCardFromId(mIdForPeriodAr[index]);
        parent.setTag(model.getTurkish());
        TextView textViewLabel = (TextView) parent.findViewById(R.id.textViewSelection);
        textViewLabel.setText(model.getEnglish());
        textViewLabel.setTypeface(mTtypeface1);
    }

    private void initViews() {
        mContainerSelection = (LinearLayout) findViewById(R.id.containerSelections);
        RelativeLayout containerPhoto = (RelativeLayout) findViewById(R.id.includeForGuess);
        ImageView imageViewPhoto = (ImageView) containerPhoto.findViewById(R.id.imageViewPhotoContent);
        mTextViewLabel = (TextView) containerPhoto.findViewById(R.id.textViewPhotoLabel);

        mTextViewLabel.setText(mModelCard.getTurkish());
        mTextViewLabel.setTypeface(mTtypeface1);

        Picasso.with(getBaseContext())
                .load(mModelCard.getImagePath())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .into(imageViewPhoto);
    }

    public void initParams() {
        int cardArIndex = 0;
        mIdForPeriodAr = new int[0];
        try {
            mIdForPeriodAr = getIntent().getExtras().getIntArray(AppConstants.REASON_KEY_CARD_PERIOD_ID_ARRAY);
            cardArIndex = getIntent().getExtras().getInt(AppConstants.REASON_KEY_ID);
        }catch (Exception e) {
            e.printStackTrace();
        }

        mModelCard = SingletonJSON.getInstance().getData()[mIdForPeriodAr[cardArIndex]];
    }

    @Override
    public void onClick(View v) {
        AnimateUtils.startButtonAnimation(v, 100);
        if (v.getTag().toString().equals(mTextViewLabel.getText().toString())) {
            // TODO cevap doğru
            Logy.l("Cevap doğru " + v.getTag());
        }
        else {
            // TODO cevap yalnış
            Logy.l("Cevap yanlış " + v.getTag());
        }
        Logy.l("doğru Cevap buydu:" + mTextViewLabel.getText().toString());
    }

    public int[] getRandomIDs() {
        int[] randomIDs = new int[AppConstants.SELECTION_COUNT];
        int rightAnswerIndex = new Random().nextInt(2) + 0;
        int rightAnswerID = mModelCard.getId();
        for (int i = 0; i < AppConstants.SELECTION_COUNT; i++) {
            if (i == rightAnswerIndex) {
                randomIDs[i] = rightAnswerID;
            }
            else {
                randomIDs[i] = new Random().nextInt(SingletonJSON.getInstance().getData().length) + 0;
            }
        }

        return randomIDs;
    }
}
