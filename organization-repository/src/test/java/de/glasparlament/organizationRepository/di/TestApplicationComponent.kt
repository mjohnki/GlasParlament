package de.glasparlament.organizationRepository.di

import dagger.Component
import de.glasparlament.data.di.DbModule
import de.glasparlament.organizationRepository.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    OrganizationRepositoryModule::class,
    TestApplicationModule::class,
    DbModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}