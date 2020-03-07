package de.glasparlament.agendaitem.detail

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import de.glasparlament.repository.agendaItem.File

class AgendaFileViewBinder {

    data class Params(
            val file: File,
            val listener: View.OnClickListener,
            val views: Views
    )

    data class Views(
            val filename: AppCompatButton
    )

    operator fun invoke(params: Params) {
       with(params.views){
           filename.text = params.file.name
           filename.setOnClickListener(params.listener)
       }
    }
}
