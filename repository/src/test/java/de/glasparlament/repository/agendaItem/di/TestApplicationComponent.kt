package de.glasparlament.repository.agendaItem.di

import dagger.Component
import de.glasparlament.repository.agendaItem.ComponentTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [
    AgendaItemRepositoryModule::class,
    TestApplicationModule::class
])
interface TestApplicationComponent {

    fun inject(test: ComponentTest)
}