package com.jess.arms.utils.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jess.arms.utils.put
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author IurKwan
 * @date 12/24/20
 */
class FragmentArgumentDelegate<T : Any> : ReadWriteProperty<Fragment, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        //key 为属性名
        val key = property.name
        return thisRef.arguments
                ?.get(key) as? T
                ?: throw IllegalStateException("Property ${property.name} could not be read")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        val args = thisRef.arguments
                ?: Bundle().also(thisRef::setArguments)
        val key = property.name
        args.put(key, value)
    }

}