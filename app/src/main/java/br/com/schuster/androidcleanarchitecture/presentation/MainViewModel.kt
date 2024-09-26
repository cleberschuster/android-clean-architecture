package br.com.schuster.androidcleanarchitecture.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schuster.androidcleanarchitecture.data.api.ApiState
import br.com.schuster.androidcleanarchitecture.data.api.Status
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.utils.toErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    init {
        getNewComment(5)
    }

    fun getNewComment(id: Int) {
        _uiState.update { it.copy(status = Status.LOADING) }

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)

            useCase.invoke(id)
                .onEach { result ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            status = Status.SUCCESS,
                            data = result,
                        )
                    }
                }
                //Trata erros de upstream
                .catch {
                    if (it.toErrorType().toString() == "404") {
                        _uiState.update { currentState ->
                            currentState.copy(
                                status = Status.ERROR,
                                message = " 404, faça a logica com o tipo do erro"
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
                }
                // .launchIn(this) Substitui o .collect() e possibilita tratar excessoes de downstream, que acontecem dentro do collect() ou do onEach()
                // e não são capturadas pelo .cath() que só captura excessoes de upstream vindas do repositorio.
                // Quando usamos o .onEach() e o .launchIn() essas possiveis excessoes de downstream já são capturadas e dai sim jogadas no .catch()
                // Se nao quiser usar o .launchIn(this) pode usar o .collect() vazio se tiver certeza que não ocorrerao excessoes de downstream.
                .launchIn(this)
        }
    }
}
