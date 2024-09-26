package br.com.schuster.androidcleanarchitecture.data.api

import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation

data class ApiState(
    val status: Status = Status.LOADING,
    val data: ObjectPresentation? = null,
    val message: String ?= null
)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

