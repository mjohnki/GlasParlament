package de.glasparlament.meeting

import de.glasparlament.data.*

object TestData {
    val meeting = MeetingRemote(
            id = "id",
            name = "39. Sitzung des Plenums",
            agendaItem = listOf(),
            organization = listOf(),
            body = "http://test.test"
    )
    val meetings = listOf(meeting)
    val meetingList = MeetingList(meetings)
}