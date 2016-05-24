package com.resimlerleingilizce.ui;

import android.content.ClipData;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resimlerleingilizce.R;
import com.resimlerleingilizce.utils.Logy;

public class ActivityGame extends AppCompatActivity  {

    private Typeface mTtypeface1, mTtypeface2;
    private TextView mTextViewMeaning;
    private RelativeLayout mRelativeLayoutPhotoContainer;
    private int photoArLength = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initViews();
        fillPhotoContainer();
    }

    private void fillPhotoContainer() {
        mRelativeLayoutPhotoContainer.removeAllViews();
        float angle = 0, additionalAngle = 10;
        for (int i = 0; i < photoArLength; i++)
        {
            RelativeLayout layoutPhoto = (RelativeLayout) getLayoutInflater().inflate(R.layout.item_photo, null);
            TextView originalText = (TextView) layoutPhoto.findViewById(R.id.textViewPhotoLabel);
            originalText.setTypeface(mTtypeface2);
            angle = (i % 2 == 0) ? angle + additionalAngle : angle - (additionalAngle * 2);
            layoutPhoto.setRotation(angle);
            layoutPhoto.setOnTouchListener(new MyTouchListener());
            layoutPhoto.setOnDragListener(new MyDragListener());
            mRelativeLayoutPhotoContainer.addView(layoutPhoto);
        }
        mRelativeLayoutPhotoContainer.setOnDragListener(new MyDragListener());
    }

    private void initViews() {
        mTextViewMeaning = (TextView) findViewById(R.id.textViewMeaning);
        mRelativeLayoutPhotoContainer = (RelativeLayout) findViewById(R.id.relativeLayoutPhotoContainer);

        mTtypeface1 = Typeface.createFromAsset(getAssets(), "coopbl.ttf");
        mTtypeface2 = Typeface.createFromAsset(getAssets(), "homework_normal.ttf");
        mTextViewMeaning.setTypeface(mTtypeface1);
    }

    View mDropedView;

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("" + view.getId(), "" + view.getId());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                view.setAlpha(0f);
                mDropedView = view;
                return true;
            } else
            {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
//        Drawable enterShape = getResources().getDrawable(R.drawable.ic_button_play);
//        Drawable normalShape = getResources().getDrawable(R.drawable.ic_button_board);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    if (mDropedView.getId() == view.getId()) {
                        Logy.l("DragEvent.ACTION_DRAG_ENDED if");
                        if (mDropedView != null)
                            mDropedView.setVisibility(View.GONE);
                    }
                    else {
                        Logy.l("DragEvent.ACTION_DRAG_ENDED else");
                        mDropedView.setVisibility(View.GONE);
                        // Dropped, reassign View to ViewGroup
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                    }
                default:
                    break;
            }
            return true;
        }
    }

}
