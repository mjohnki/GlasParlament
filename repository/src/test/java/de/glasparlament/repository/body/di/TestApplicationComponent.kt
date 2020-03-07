package de.glasparlament.repository.body.di

import dagger.Component
import de.glasparlament.repository.body.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    BodyRepositoryModule::class,
    TestApplicationModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}