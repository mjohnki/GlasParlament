package de.glasparlament.agendaItemRepository

data class AgendaItem(
        var id: String,
        var number: String,
        var name: String,
        var meeting: String,
        var auxiliaryFile: List<File>)
