package de.glasparlament.glasparlament.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import de.glasparlament.glasparlament.BaseApplication
import javax.inject.Singleton

@Singleton
@Component(modules =  [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    OrganizationModule::class,
    MeetingModule::class,
    AgendaItemModule::class])
interface ApplicationComponent {

    fun inject(activity: BaseApplication)
}
