package de.glasparlament.repository.body.di

import dagger.Component
import de.glasparlament.repository.body.ComponentTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [
    BodyRepositoryModule::class,
    TestApplicationModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}