package com.iur.arms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.iur.arms.databinding.ActivityMainBinding
import com.jess.arms.utils.bind
import timber.log.Timber

/**
 * @author IurKwan
 * @date 2/3/21
 */
class MainFragment : Fragment(R.layout.activity_main) {

    private val b by bind(ActivityMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("")
    }


}