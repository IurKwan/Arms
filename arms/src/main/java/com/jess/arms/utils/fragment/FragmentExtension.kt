package com.jess.arms.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.jess.arms.base.BaseFragment
import com.jess.arms.mvp.IPresenter
import com.jess.arms.utils.fragment.FragmentArgumentDelegate
import com.jess.arms.utils.fragment.FragmentNullableArgumentDelegate
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 自动注入fragment携带的参数
 * @author IurKwan
 * @date 12/24/20
 */

private const val TAG = "ViewBindingProperty"

fun <T : Any> argument(): ReadWriteProperty<Fragment, T> =
    FragmentArgumentDelegate()

fun <T : Any> argumentNullable(): ReadWriteProperty<Fragment, T?> =
    FragmentNullableArgumentDelegate()

fun <T> Bundle.put(key: String, value: T) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}



inline fun <F : Fragment, V : ViewBinding> bind(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (F) -> View = Fragment::requireView
) = FragmentViewBindingProperty { fragment: F ->
    viewBinder(viewProvider(fragment))
}

class FragmentViewBindingProperty<in F : Fragment, out V : ViewBinding>(
    private val viewBinder: (F) -> V
) : ReadOnlyProperty<F, V> {
    private var viewBinding: V? = null

    @SuppressLint("LogNotTimber")
    @MainThread
    override fun getValue(thisRef: F, property: KProperty<*>): V {
        // 已经绑定，直接返回
        viewBinding?.let { return it }

        // Use viewLifecycleOwner.lifecycle other than lifecycle
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w(
                TAG, "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " +
                        "The instance of viewBinding will be not cached."
            )
            // We can access to ViewBinding after Fragment.onDestroyView(), but don't save it to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    fun clear() {
        viewBinding = null
    }

    private inner class ClearOnDestroyLifecycleObserver : LifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post { clear() }
        }
    }
}


