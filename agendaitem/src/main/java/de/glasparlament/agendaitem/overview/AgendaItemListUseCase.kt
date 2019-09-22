package de.glasparlament.agendaitem.overview

import de.glasparlament.agendaitem_repository.AgendaItem
import de.glasparlament.agendaitem_repository.AgendaItemRepository
import de.glasparlament.data.AgendaItemRemote
import de.glasparlament.data.Transfer
import de.glasparlament.meeting_repository.MeetingRepository

class AgendaItemListUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Transfer<List<AgendaItem>> {
        return when(val result = repository.getAgendaItems(url)){
            is Transfer.Success -> Transfer.Success(result.data.sortedWith(compare()))
            is Transfer.Error -> result
        }
    }

    private fun compare(): Comparator<AgendaItem> {
        return Comparator { a1: AgendaItem, a2: AgendaItem ->
            prepareCompareValue(a1.number).compareTo(prepareCompareValue(a2.number))
        }
    }

    private fun prepareCompareValue(name: String) : String {
        var value = name
        if(value.indexOf(".")  != -1){
            value = value.substring(0,value.indexOf("."))
        }
        if(value.length == 2){
            return value
        }
        return "0".plus(value)
    }
}