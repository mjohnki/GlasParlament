package de.glasparlament.glasparlament.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import de.glasparlament.repository.agendaItem.di.AgendaItemRepositoryModule
import de.glasparlament.agendaitem.di.AgendaItemModule
import de.glasparlament.repository.body.di.BodyRepositoryModule
import de.glasparlament.repository.DbModule
import de.glasparlament.glasparlament.BaseApplication
import de.glasparlament.meeting.di.MeetingModule
import de.glasparlament.repository.meeting.di.MeetingRepositoryModule
import de.glasparlament.search.di.SearchModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    MeetingModule::class,
    SearchModule::class,
    BodyRepositoryModule::class,
    MeetingRepositoryModule::class,
    AgendaItemRepositoryModule::class,
    DbModule::class,
    AgendaItemModule::class])
interface ApplicationComponent {

    fun inject(activity: BaseApplication)
}
