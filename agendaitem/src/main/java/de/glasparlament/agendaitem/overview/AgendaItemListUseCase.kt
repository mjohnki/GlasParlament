package de.glasparlament.agendaitem.overview

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AgendaItemListUseCase(private val repository: AgendaItemRepository) {

    suspend fun execute(url: String): Flow<StoreResponse<List<AgendaItem>>> =
            repository.getAgendaItems(url).map {
                if (it is StoreResponse.Data) {
                    StoreResponse.Data(
                            origin = it.origin,
                            value = it.value.sortedWith(compare())
                    )
                } else {
                    it
                }
            }

    private fun compare(): Comparator<AgendaItem> =
            Comparator { a1: AgendaItem, a2: AgendaItem ->
                prepareCompareValue(a1.number).compareTo(prepareCompareValue(a2.number))
            }

    private fun prepareCompareValue(name: String): String {
        var value = name
        if (value.indexOf(".") != -1) {
            value = value.substring(0, value.indexOf("."))
        }
        if (value.length == 2) {
            return value
        }
        return "0".plus(value)
    }
}
