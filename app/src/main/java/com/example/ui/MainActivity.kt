package com.example.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = BlankFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
    }


}
