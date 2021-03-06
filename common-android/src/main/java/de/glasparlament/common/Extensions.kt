package de.glasparlament.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T : Any?> LifecycleOwner.observe(data: LiveData<T?>, block: (T) -> Unit) {
    data.observe(this, Observer {  it?.let(block) })
}
