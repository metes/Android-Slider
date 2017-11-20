# Android Slider

# Example
A project Example: https://www.youtube.com/watch?v=Upi7xmJ-sWA

# Gradle 
Adding from gradle:

    allprojects {
        repositories { 
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
    dependencies {
        compile 'com.github.metes:android-slider:1.0.2'
    }
    

# Usage
Typically usage like this:

      private void initSlider(View rootView) {
        View slider = rootView.findViewById(R.id.slider);
        mSliderItemList = generateItems(280,400);
        // Helper (add slider view items)
        mSliderHelper = new SliderHelper(getContext(), mSliderItemList, false, slider);
        mSliderHelper.setOnSliderIndexChangeListener(this);

        updateLabel(0);
    }

    private ArrayList<SliderItem> generateItems(int smallIconSize, int bigIconSize) {
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
                    colorResourceIds[i],
                    smallIconSize,
                    bigIconSize
            ));
        }
        return items;
    }

    @Override
    public void OnSliderIndexChanged(int newIndex) {
        Log.d(MainActivity.class.getSimpleName(), "OnSliderIndexChanged newIndex: " + newIndex);
        updateLabel(newIndex);
    }

   

On XML add this line:

     <include
        android:id="@+id/slider"
        layout="@layout/item_slider"
        android:layout_width="400dp"
        android:layout_height="wrap_content"
        />
