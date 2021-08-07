package com.jess.arms.utils

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.properties.ReadWriteProperty

/**
 * @author IurKwan
 * @date 1/4/21
 */

/**
 * findViewById
 */
fun <T : View> Activity.find(@IdRes id: Int): T {
    return findViewById(id)
}

/**
 * setOnClick
 */
fun Int.onClick(activity: Activity, click: () -> Unit) {
    activity.find<View>(this).apply {
        setOnClickListener {
            click()
        }
    }
}

/**
 * setOnClick
 */
fun View.onClick(click: () -> Unit) {
    setOnClickListener {
        click()
    }
}