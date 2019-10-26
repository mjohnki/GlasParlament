package de.glasparlament.agendaItemRepository.di

import dagger.Component
import de.glasparlament.agendaItemRepository.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AgendaItemRepositoryModule::class,
    TestApplicationModule::class
])
interface TestApplicationComponent {

    fun inject(test: ComponentTest)
}