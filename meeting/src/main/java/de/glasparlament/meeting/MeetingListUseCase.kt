package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.meeting_repository.Meeting
import de.glasparlament.meeting_repository.MeetingRepository

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