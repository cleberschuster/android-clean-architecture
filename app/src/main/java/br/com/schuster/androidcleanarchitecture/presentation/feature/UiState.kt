package br.com.schuster.androidcleanarchitecture.presentation.feature

import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation

data class UiState(
    val status: Status = Status.LOADING,
    val data: ObjectPresentation? = null,
    val errorMessage: String ? = null,
)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    INPUT_ERROR
}

