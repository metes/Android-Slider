package com.example.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.R
import mt.slider.Slider
import mt.slider.interfaces.OnSliderIndexChangeListener
import mt.slider.model.SliderItem
import java.util.*

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
