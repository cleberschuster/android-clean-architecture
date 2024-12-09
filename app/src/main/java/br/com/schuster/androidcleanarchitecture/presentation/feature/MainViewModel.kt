package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.androidcleanarchitecture.R
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.utils.handleApiError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: PostUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(
        // UiState() ja inicializei os valores na propria classe
        UiState()
    )

    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    var textSearch by mutableStateOf("3")
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MainScreenEvent) {
        when (event) {

            is MainScreenEvent.OnValueChange -> {
                textSearch = event.searchText.trim()
            }
            is MainScreenEvent.OnSearch -> {
                searchPosts()
            }
            is MainScreenEvent.OnClickSearch -> {
                searchPosts()
            }
        }
    }

    private fun searchPosts() {
        viewModelScope.launch {
            if (textSearch.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar(resId = R.string.search_not_empty))

                _uiState.update { currentState ->
                    currentState.copy(
                        status = Status.INPUT_TEXT_ERROR,
                    )
                }
                return@launch
            }
            getNewPost(textSearch)
        }
    }

    private fun getNewPost(id: String) {
        _uiState.update { it.copy(status = Status.LOADING) }
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
                _uiState.update { currentState ->
                    currentState.copy(
                        status = Status.ERROR,
                        errorMessage = handleApiError(it).toString()
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
