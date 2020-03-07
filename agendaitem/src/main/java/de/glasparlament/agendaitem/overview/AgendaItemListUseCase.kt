package de.glasparlament.agendaitem.overview

import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.data.Transfer
import de.glasparlament.repository.agendaItem.AgendaItemRepository

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
