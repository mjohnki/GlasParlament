package de.glasparlament.agendaitem

import de.glasparlament.data.*
import java.util.ArrayList

object TestData {
    val agendaItem_17 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_17a = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17.a",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_17b = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17.b",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_16 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "16",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_18 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "18",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_4 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "4",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_5 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "5",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_6 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "6",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_7 = AgendaItemRemote(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "7",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val meeting_single_agendaitem = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_16_17 = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_16, agendaItem_17),
            organization = ArrayList(),
            body = ""
    )

    val meeting_agendaitem_17_16 = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17, agendaItem_16),
            organization = ArrayList(),
            body = ""
    )

    val meeting_agendaitem_17a_17b = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17a, agendaItem_17b),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_17a_17b_16_18 = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17a, agendaItem_17b, agendaItem_16, agendaItem_18),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_7_4_6_5 = MeetingRemote(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_7, agendaItem_4, agendaItem_6, agendaItem_5),
            organization = ArrayList(),
            body = ""
    )
}