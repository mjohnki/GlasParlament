package de.glasparlament.common_android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

abstract class NavigationViewModel : ViewModel() {
    val navigationCommand = MutableLiveData<Event<NavigationCommand>>()

    fun navigate(directions: NavDirections) {
        navigationCommand.postValue(Event(NavigationCommand.To(directions)))
    }
}