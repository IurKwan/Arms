package com.iur.arms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.jess.arms.utils.ArmDataBus
import me.yokeyword.fragmentation.SupportFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArmDataBus.with<String>("aaa").setStickyData("123456")

        ArmDataBus.with<String>("aaa").observerSticky(this,false) {
            Log.d("213", "")
        }

    }
}