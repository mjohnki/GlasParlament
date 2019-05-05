package de.glasparlament.glasparlament

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import de.glasparlament.glasparlament.di.ApplicationModule
import de.glasparlament.glasparlament.di.DaggerApplicationComponent
import javax.inject.Inject


class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        val component = DaggerApplicationComponent.builder().applicationModule(
                ApplicationModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }
}