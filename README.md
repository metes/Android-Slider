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
        compile 'com.github.metes:android-slider:1.0.4'
    }
    

# Usage
Usage example:


    public class BlankFragment extends Fragment implements
        View.OnClickListener, OnSliderIndexChangeListener {
        
        
    private SliderHelper mSliderHelper;
    private TextView mTextViewLabel;
    private ArrayList<SliderItem> mSliderItemList;
    private View mSlider;    
    
    ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_blank, container, false);        
            mTextViewLabel = rootView.findViewById(R.id.textViewCategoryLabel);        
            mSlider = rootView.findViewById(R.id.slider);        
            initSlider();

            return rootView;
        }

        private void initSlider() {
            mSliderItemList = generateItems();;
            mSliderHelper = new SliderHelper(getContext(), mSliderItemList, false, mSlider);
            mSliderHelper.setSlideButtonResources(R.drawable.ic_button_left, R.drawable.ic_button_right);
            mSliderHelper.setOnSliderIndexChangeListener(this);        
            updateLabel(0);
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
                        colorResourceIds[i],
                        280,
                        350
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
        
        ...
   }
   

On XML add this line:

    <include
       android:id="@+id/slider"
       layout="@layout/item_slider"
       android:layout_width="300dp"
       android:layout_height="wrap_content" />

--------------------

The MIT License (MIT)

Copyright (c) <year> <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

--------------------
