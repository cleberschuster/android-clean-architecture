package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.utils.toErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: PostUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(
        // ApiState() ja inicializei os valores na propria classe
        ApiState()
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    var textSearch by mutableStateOf("1")
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getNewComment(textSearch)
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnValueChange -> {
                textSearch = event.searchText.trim()
            }
            is MainScreenEvent.OnClickSearch -> {
                getNewComment(textSearch)
            }
        }
    }

    private fun getNewComment(id: String) {
        _uiState.update { it.copy(status = Status.LOADING) }

        viewModelScope.launch {

            if (textSearch.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar(message = "a pesquisa não pode ser vazia"))
                return@launch
            }

            delay(1000)

            useCase.invoke(id.toInt())

                .onEach { result ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            status = Status.SUCCESS,
                            data = result,
                        )
                    }
                }
                .catch {
                    if (it.toErrorType().toString() == "404") {
                        _uiState.update { currentState ->
                            currentState.copy(
                                status = Status.ERROR,
                                message = "digite um id válido"
                            )
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                status = Status.ERROR,
                                message = it.toErrorType().toString()
                            )
                        }
                    }
                }.collect()
        }
    }
}
