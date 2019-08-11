package de.glasparlament.organization

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun setVisibility(state: Boolean): Int {
    return if (state) View.VISIBLE else View.GONE
}