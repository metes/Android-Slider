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


    class BlankFragment : Fragment(), View.OnClickListener, OnSliderIndexChangeListener {

    private var mSlider: Slider? = null
    private var mTextViewLabel: TextView? = null
    private var mSliderItemList: ArrayList<SliderItem>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        initSlider(rootView)

        return rootView
    }

    private fun initSlider(rootView: View) {
        mSliderItemList = generateItems()
        mTextViewLabel = rootView.findViewById<View>(R.id.textViewCategoryLabel) as TextView
        val slider = rootView.findViewById<View>(R.id.slider)
        updateLabel(0)
        // Helper (add slider view items)
        mSlider = Slider(context!!, mSliderItemList!!, false, slider)


        mSlider!!.imageViewCenter1.setOnClickListener {
            Toast.makeText(context!!, "center image clicked", Toast.LENGTH_SHORT).show()
        }
        mSlider!!.setOnSliderIndexChangeListener(this)
        mSlider!!.setSlideButtonResources(R.drawable.ic_button_left, R.drawable.ic_button_right)
    }

    private fun generateItems(): ArrayList<SliderItem> {
        val items = ArrayList<SliderItem>()
        val colorResourceIds = intArrayOf(android.R.color.holo_blue_bright, android.R.color.holo_blue_dark, android.R.color.holo_green_light, android.R.color.holo_green_dark, android.R.color.holo_orange_light)
        val imageResourceIds = intArrayOf(R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3, R.drawable.ic_category_4, R.drawable.ic_category_5)

        for (i in 0..4) {
            items.add(SliderItem(
                    "Label $i",
                    imageResourceIds[i],
                    colorResourceIds[i],
                    280,
                    350
            ))
        }
        return items
    }

    private fun updateLabel(newIndex: Int) {
        mTextViewLabel!!.text = mSliderItemList!![newIndex].label
        mTextViewLabel!!.setTextColor(
                ContextCompat.getColor(context!!,mSliderItemList!![newIndex].colorID)
        )
    }

    override fun OnSliderIndexChanged(newIndex: Int) {
        Log.d(MainActivity::class.java.simpleName, "OnSliderIndexChanged newIndex: $newIndex")
        updateLabel(newIndex)
    }

    override fun onClick(v: View) {
        // get selected slider index
        val index = mSlider!!.sliderPositionIndex
        when (index) {
            0 -> {// TODO somethings
            }
            1 -> { // TODO somethings
            }
            else -> {  // TODO other things
            }
        }
    }
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
