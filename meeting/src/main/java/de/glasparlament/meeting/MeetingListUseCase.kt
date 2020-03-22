package de.glasparlament.meeting

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.MeetingRepository
import kotlinx.coroutines.flow.Flow

class MeetingListUseCase(private val repository: MeetingRepository) {

    suspend fun execute(url: String): Flow<StoreResponse<List<Meeting>>> =
        repository.getMeetingList(url)

}
