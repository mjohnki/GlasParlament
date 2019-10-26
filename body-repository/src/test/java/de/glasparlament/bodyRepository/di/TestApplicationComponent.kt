package de.glasparlament.bodyRepository.di

import dagger.Component
import de.glasparlament.bodyRepository.ComponentTest
import de.glasparlament.data.di.DbModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    BodyRepositoryModule::class,
    TestApplicationModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}