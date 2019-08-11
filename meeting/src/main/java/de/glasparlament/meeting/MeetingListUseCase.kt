package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.data.Meeting
import de.glasparlament.data.MeetingList

class MeetingListUseCase(private val repository: de.glasparlament.meeting_repository.MeetingRepository) {

    suspend fun execute(url: String): Transfer<List<Meeting>> {
        val transfer = repository.getMeetingList(url)
        return when(transfer){
            is Transfer.Success -> Transfer.Success(map(transfer.data))
            is Transfer.Error -> transfer
        }
    }

    fun map(meetingList: MeetingList) : List<Meeting>{
        val result = meetingList.data

        result.sortBy { meeting -> meeting.name }

        return result
    }
}