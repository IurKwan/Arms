package com.iur.arms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iur.arms.imageloader.glide.GlideConfigImpl
import com.jess.arms.utils.ArmsUtils

/**
 * @author IurKwan
 * @date 2/3/21
 */
class MainFragment : Fragment(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArmsUtils.obtainAppComponentFromContext(context)
            .imageLoader()
            .loadImage(context,GlideConfigImpl.builder()
                .build()
            )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}