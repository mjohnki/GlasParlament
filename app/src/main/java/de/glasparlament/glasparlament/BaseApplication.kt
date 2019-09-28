package de.glasparlament.glasparlament

import android.app.Application
import de.glasparlament.agendaItemRepository.di.agendaItemRepositoryModule
import de.glasparlament.agendaitem.di.agendaItemModule
import de.glasparlament.bodyRepository.di.bodyRepositoryModule
import de.glasparlament.data.di.dbModule
import de.glasparlament.glasparlament.di.applicationModule
import de.glasparlament.meeting.di.meetingModule
import de.glasparlament.meetingRepository.di.meetingRepositoryModule
import de.glasparlament.organization.di.organizationModule
import de.glasparlament.organizationRepository.di.organizationRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(
                    applicationModule,
                    agendaItemModule,
                    agendaItemRepositoryModule,
                    bodyRepositoryModule,
                    meetingModule,
                    meetingRepositoryModule,
                    organizationModule,
                    organizationRepositoryModule,
                    dbModule
            ))
        }
    }
}
