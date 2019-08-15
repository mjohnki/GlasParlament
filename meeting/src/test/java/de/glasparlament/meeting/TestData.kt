package de.glasparlament.meeting

import de.glasparlament.data.*

object TestData {
    val meeting = Meeting(
            id = "id",
            name = "39. Sitzung des Plenums",
            agendaItem = listOf(),
            organization = listOf(),
            body = "http://test.test"
    )
    val meetingList = MeetingList(listOf(meeting))
}