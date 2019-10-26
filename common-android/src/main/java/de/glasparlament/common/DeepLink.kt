package de.glasparlament.common

import android.content.res.Resources
import android.net.Uri

object DeepLink {

    fun agendaOverview(resources: Resources, title: String, meetingId: String): Uri =
            Uri.parse(
                    resources.getString(
                            R.string.deeplink_agenda_format,
                            title,
                            Uri.encode(meetingId))
            )

    fun search(resources: Resources): Uri =
            Uri.parse(
                    resources.getString(
                            R.string.deeplink_search
                    )
            )

    fun meetingList(resources: Resources, title: String, meetingListId: String): Uri =
            Uri.parse(
                    resources.getString(
                            R.string.deeplink_meeting_list_format,
                            title,
                            Uri.encode(meetingListId)
                    )
            )

}