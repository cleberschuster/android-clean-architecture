package br.com.schuster.androidcleanarchitecture.presentation.feature

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    data class ShowToast(val message: String) : UiEvent
}