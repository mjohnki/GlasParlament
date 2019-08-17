package de.glasparlament.agendaitem

import de.glasparlament.data.*
import java.util.ArrayList

object TestData {
    val agendaItem_17 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_17a = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17.a",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_17b = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "17.b",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_16 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "16",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_18 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "18",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_4 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "4",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_5 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "5",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_6 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "6",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val agendaItem_7 = AgendaItem(
            id = "id",
            name = "Nachhaltigkeit auf den Bau: Berlin baut mit Holz",
            number = "7",
            meeting = "http://test.test",
            auxiliaryFile = listOf()
    )
    val meeting_single_agendaitem = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_16_17 = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_16, agendaItem_17),
            organization = ArrayList(),
            body = ""
    )

    val meeting_agendaitem_17_16 = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17, agendaItem_16),
            organization = ArrayList(),
            body = ""
    )

    val meeting_agendaitem_17a_17b = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17a, agendaItem_17b),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_17a_17b_16_18 = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_17a, agendaItem_17b, agendaItem_16, agendaItem_18),
            organization = ArrayList(),
            body = ""
    )
    val meeting_agendaitem_7_4_6_5 = Meeting(
            id = "",
            name = "",
            agendaItem = listOf(agendaItem_7, agendaItem_4, agendaItem_6, agendaItem_5),
            organization = ArrayList(),
            body = ""
    )
}