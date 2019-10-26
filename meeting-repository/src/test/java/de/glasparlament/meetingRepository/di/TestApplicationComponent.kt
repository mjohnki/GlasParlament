package de.glasparlament.meetingRepository.di

import dagger.Component
import de.glasparlament.data.di.DbModule
import de.glasparlament.meetingRepository.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MeetingRepositoryModule::class,
    TestApplicationModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}