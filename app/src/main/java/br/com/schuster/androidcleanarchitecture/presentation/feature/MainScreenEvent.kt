package br.com.schuster.androidcleanarchitecture.presentation.feature

sealed interface MainScreenEvent {
    data class OnValueChange(val searchText: String) : MainScreenEvent
    data object OnSearch : MainScreenEvent
    data object OnClickSearch : MainScreenEvent
}