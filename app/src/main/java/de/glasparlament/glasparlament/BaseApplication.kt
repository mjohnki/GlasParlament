package de.glasparlament.glasparlament

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import de.glasparlament.glasparlament.di.ApplicationModule
import de.glasparlament.glasparlament.di.DaggerApplicationComponent
import javax.inject.Inject


class BaseApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder().applicationModule(
                ApplicationModule(this)).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> =
            androidInjector

}
