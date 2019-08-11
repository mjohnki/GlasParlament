package de.glasparlament.data

sealed class Transfer<out T: Any> {
    data class Success<out T : Any>(val data: T) : Transfer<T>()
    data class Error(val exception: String) : Transfer<Nothing>()
}