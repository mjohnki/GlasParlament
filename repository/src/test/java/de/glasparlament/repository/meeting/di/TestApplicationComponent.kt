package de.glasparlament.repository.meeting.di

import dagger.Component
import de.glasparlament.repository.meeting.ComponentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MeetingRepositoryModule::class,
    TestApplicationModule::class])
interface TestApplicationComponent{

    fun inject(test: ComponentTest)
}