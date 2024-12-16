package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.schuster.androidcleanarchitecture.BaseUnitTest
import br.com.schuster.androidcleanarchitecture.CoroutineRule
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest : BaseUnitTest() {

    private val useCase: PostUseCase = mockk()
    private lateinit var viewModel: MainViewModel
    private lateinit var uiState: StateFlow<UiState>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Before
    fun setUp() {
        viewModel = MainViewModel(useCase)
        uiState = viewModel.uiState
    }

    @Test
    fun getUiState() {
        viewModel = MainViewModel(useCase)
        val uiState = uiState.value
        assertEquals(UiState(), uiState)
    }

    @Test
    fun `onEvent OnValueChange should update textSearch`() {
        val newText = "new text"
        viewModel.onEvent(MainScreenEvent.OnValueChange(newText))

        assertEquals(newText, viewModel.textSearch)
    }

    @Test
    fun `onEvent OnSearch should return Status SUCCESS`() = runTest {
        val searchText = "123"
        val post = ObjectPresentation(
            postId = 123,
            id = 1,
            email  = "email",
            name =  "name",
            comment = "body"

        )
        coEvery { useCase.invoke(any()) } returns flowOf(post)

        viewModel.onEvent(MainScreenEvent.OnValueChange(searchText))

        viewModel.onEvent(MainScreenEvent.OnSearch)

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        coVerify { useCase.invoke(searchText.toInt()) }

        assertEquals(Status.SUCCESS, uiState.value.status)
        assertEquals(post, uiState.value.data)
    }


    @Test
    fun `onEvent OnClickSearch should return Status SUCCESS`() = runTest {
        val searchText = "123"
        val post = ObjectPresentation(
            postId = 123,
            id = 1,
            email  = "email",
            name =  "name",
            comment = "body"

        )
        coEvery { useCase.invoke(any()) } returns flowOf(post)

        viewModel.onEvent(MainScreenEvent.OnValueChange(searchText))

        viewModel.onEvent(MainScreenEvent.OnClickSearch)

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        coVerify { useCase.invoke(searchText.toInt()) }

        assertEquals(Status.SUCCESS, uiState.value.status)
        assertEquals(post, uiState.value.data)
    }

//    @Test
//    fun getTextSearch() {
//    }
//
//    @Test
//    fun getUiEvent() {
//    }
//
//    @Test
//    fun onEvent() {
//    }
}