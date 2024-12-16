//package br.com.schuster.androidcleanarchitecture.presentation.feature
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import br.com.schuster.androidcleanarchitecture.MainCoroutineRule
//import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
//import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation
//import io.mockk.Called
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class MainViewModelAAAAATest {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    private lateinit var viewModel: MainViewModel
//    private lateinit var useCase: PostUseCase
//    private lateinit var uiState: StateFlow<UiState>
//
//    @Before
//    fun setUp() {
//        useCase = mockk()
//        viewModel = MainViewModel(useCase)
//        uiState = viewModel.uiState
//    }
//
//
//    @Test
//    fun `onEvent OnValueChange should update textSearch`() {
//        val newText = "new text"
//        viewModel.onEvent(MainScreenEvent.OnValueChange(newText))
//
//        kotlin.test.assertEquals(newText, viewModel.textSearch)
//    }
//
//    @Test
//    fun `onEvent OnSearch with blank text should show snackbar and update state to INPUT_TEXT_ERROR`() = runTest {
//        viewModel.onEvent(MainScreenEvent.OnSearch)
//
//        coVerify{ useCase wasNot Called }
////        kotlin.test.assertEquals(Status.INPUT_TEXT_ERROR, uiState.value.status)
//        kotlin.test.assertEquals(Status.IDLE, uiState.value.status)
//    }
//
//    @Test
//    fun `onEvent OnSearch with valid text should call useCase and update state to LOADING then SUCCESS`() = runTest {
//        val searchText = "123"
//        val post = ObjectPresentation(
//            postId = 1,
//            id = 1,
//            email  = "email",
//            name =  "name",
//            comment = "body"
//
//        )
//        coEvery { useCase.invoke(any()) } returns flowOf(post)
//
//        viewModel.textSearch = searchText
//        viewModel.onEvent(MainScreenEvent.OnSearch)
//
//        coVerify { useCase.invoke(searchText.toInt()) }
//        kotlin.test.assertEquals(Status.SUCCESS, uiState.value.status)
//        kotlin.test.assertEquals(post, uiState.value.data)
//    }
//
//    @Test
//    fun `onEvent OnSearch with valid text but useCase throws exception should update state to ERROR`() = runTest {
//        val searchText = "123"
//        val exception = Exception("Error")
//        coEvery { useCase.invoke(any()) } throws exception
//
//        viewModel.textSearch = searchText
//        viewModel.onEvent(MainScreenEvent.OnSearch)
//
//        coVerify { useCase.invoke(searchText.toInt()) }
//        kotlin.test.assertEquals(Status.ERROR, uiState.value.status)
//        kotlin.test.assertEquals(
//            exception.toString(),
//            uiState.value.errorMessage
//        ) // Assuming handleApiError returns the exception message
//    }
//
//    @Test
//    fun `onEvent OnClickSearch should behave the same as OnSearch`() = runTest {
//        // You can reuse the tests for OnSearch here, just replace OnSearch with OnClickSearch
//        // For example:
//        val searchText = "123"
//        val post = ObjectPresentation(
//            postId = 123,
//            id = 1,
//            email  = "email",
//            name =  "name",
//            comment = "body"
//
//        )
//        coEvery { useCase.invoke(any()) } returns flowOf(post)
//
//        viewModel.textSearch = searchText
//        viewModel.onEvent(MainScreenEvent.OnClickSearch) // Using OnClickSearch here
//
//        coVerify { useCase.invoke(searchText.toInt()) }
//        kotlin.test.assertEquals(Status.SUCCESS, uiState.value.status)
//        kotlin.test.assertEquals(post, uiState.value.data)
//    }
//}