# Android_Slider
First version of Slider



This example includes the use of the first version of the slider library. Since the library is not yet accessible via grade, you need to add it manually.


Typically usage like this:


   RelativeLayout sliderContainer = (RelativeLayout) findViewById(R.id.sliderContainer);
   // Helper (add slider view items)
   mSliderHelper = new SliderHelper(sliderContainer, ActivityMain.this, mModelSliderItems);
   // Left & Right image button resouces
   mSliderHelper.setSlideButtonResources(getResources().getDrawable(R.drawable.ic_button_left),    getResources().getDrawable(R.drawable.ic_button_right));
   // Add SliderIndexChangeListener
   mSliderHelper.setCustomEventListener(this);
