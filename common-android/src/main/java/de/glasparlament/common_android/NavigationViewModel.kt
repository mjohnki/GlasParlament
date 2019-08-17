package de.glasparlament.common_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

abstract class NavigationViewModel : ViewModel() {
    val navigationCommand = MutableLiveData<NavigationCommand>()

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }
}