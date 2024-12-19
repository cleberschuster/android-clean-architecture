package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
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
    // Como testar flows do Kotlin no Android
    // https://developer.android.com/kotlin/flow/test?hl=pt-br

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

        @Test
    fun `onEvent OnSearch with valid text but useCase throws exception should update state to ERROR`() = runTest {
        val searchText = "123"
        val exception = Exception("Error")

        coEvery { useCase.invoke(any()) } throws exception

        viewModel.onEvent(MainScreenEvent.OnValueChange(searchText))
        viewModel.onEvent(MainScreenEvent.OnSearch)

            coroutineRule.dispatcher.scheduler.advanceUntilIdle()

            viewModel.uiState.test {

                assertEquals(Status.ERROR, awaitItem().status)
                assertEquals(exception.toString(), awaitItem().errorMessage)

            }

        coVerify { useCase.invoke(searchText.toInt()) }
//        assertEquals(Status.ERROR, uiState.value.status)
//        assertEquals(
//            exception.toString(),
//            uiState.value.errorMessage
//        ) // Assuming handleApiError returns the exception message
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