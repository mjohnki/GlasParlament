package de.glasparlament.agendaitem_repository

data class AgendaItemSearchResult(
        var id: String,
        var number: String,
        var name: String,
        var meetingName: String)