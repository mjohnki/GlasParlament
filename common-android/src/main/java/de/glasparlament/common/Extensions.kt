package de.glasparlament.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController


fun <T : Any?> LifecycleOwner.observe(data: LiveData<T?>, block: (T) -> Unit) {
    data.observe(this, Observer {  it?.let(block) })
}

fun LifecycleOwner.observeNavigation(data: LiveData<Event<NavigationCommand>>, navController: NavController) {
    data.observe(this, Observer { command ->
        command.getContentIfNotHandled()?.let {
            when (it) {
                is NavigationCommand.To -> navController.navigate(it.directions)
            }
        } })
}
