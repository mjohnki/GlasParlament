package de.glasparlament.repository.agendaItem.di

import dagger.Component
import de.glasparlament.repository.agendaItem.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    de.johnki.demoandroid.repository.agendaItem.di.AgendaItemRepositoryModule::class,
    de.glasparlament.repository.agendaItem.di.TestApplicationModule::class
])
interface TestApplicationComponent {

    fun inject(test: de.glasparlament.repository.agendaItem.ComponentTest)
}