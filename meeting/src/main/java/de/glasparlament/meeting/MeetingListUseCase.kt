package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.MeetingRepository

class MeetingListUseCase(private val repository: MeetingRepository) {

    suspend fun execute(url: String): Transfer<List<Meeting>> {
        return when(val transfer = repository.getMeetingList(url)){
            is Transfer.Success -> Transfer.Success(map(transfer.data))
            is Transfer.Error -> transfer
        }
    }

    private fun map(meetings: List<Meeting>) : List<Meeting> =
        meetings.sortedBy { meeting -> meeting.name }
}
