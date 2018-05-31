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
            prepareSlider();
            updateLabel(0);

            return rootView;
        }

        private void prepareSlider() {
            mSliderItemList = generateItems();;
            mSliderHelper = new SliderHelper(getContext(), mSliderItemList, false, mSlider);
            mSliderHelper.setSlideButtonResources(R.drawable.ic_button_left, R.drawable.ic_button_right);
            mSliderHelper.setOnSliderIndexChangeListener(this);        
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
   

On XML

    <include
       android:id="@+id/slider"
       layout="@layout/item_slider"
       android:layout_width="300dp"
       android:layout_height="wrap_content" />
       
       
      
#License  
     
    Copyright 2018 Serkan Mete Soydaş

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
