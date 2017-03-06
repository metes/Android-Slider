# Android_Slider
First version of Slider

An Example: https://www.youtube.com/watch?v=QrX4VHida0Q

This example includes the use of the first version of the slider library. Since the library is not yet accessible via grade, you need to add it manually.


Typically usage like this:


  
    SliderHelper mSliderHelper;
    TextView mTextViewLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewLabel = (TextView) findViewById(R.id.textViewCategoryLabel);
        mTextViewLabel.setText("Label: " + 0);

        // Helper (add slider view items)
        mSliderHelper = new SliderHelper(MainActivity.this, generateItems(), true);
        mSliderHelper.setOnSliderIndexChangeListener(this);
    }

    private ArrayList<ModelSliderItem> generateItems() {
        ArrayList<ModelSliderItem> items = new ArrayList<>();
        int[] colorResourceIds = {
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
                    colorResourceIds[i]
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
