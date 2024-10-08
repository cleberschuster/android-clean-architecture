package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schuster.androidcleanarchitecture.R
import br.com.schuster.androidcleanarchitecture.presentation.components.CustomSearchView
import br.com.schuster.androidcleanarchitecture.presentation.components.ErrorScreen
import br.com.schuster.androidcleanarchitecture.presentation.components.ShimmerScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        viewModel = viewModel,
        uiStateValue = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    viewModel: MainViewModel,
    uiStateValue: ApiState,
    snackbarHostState: SnackbarHostState,
    onEvent: ( MainScreenEvent ) -> Unit
) {

    // official docomentation:
    // https://developer.android.com/develop/ui/compose/side-effects?hl=pt-br#remembercoroutinescope
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val searchText = viewModel.textSearch
    val keyboardController = LocalSoftwareKeyboardController.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // official docomentation:
    // https://developer.android.com/develop/ui/compose/side-effects?hl=pt-br#launchedeffect
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.asString(context)
                    )
                }
            }
        }
    }

    // official docomentation:
    // https://developer.android.com/develop/ui/compose/side-effects?hl=pt-br#disposableeffect
    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val lifecycleObserver = LifecycleEventObserver { _, event ->

            when (event) {

                Lifecycle.Event.ON_START -> {
                    coroutineScope.launch {
                        onEvent(MainScreenEvent.OnSearch)
                    }
                }
                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar( {

                Row (
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    CustomSearchView(
                        modifier = Modifier.weight(1f),
                        search = searchText,
                        onValueChange = {
                            onEvent(MainScreenEvent.OnValueChange(it))
                        },
                    )

                    IconButton(onClick = {
                            coroutineScope.launch {
                                keyboardController?.hide()
                                onEvent(MainScreenEvent.OnSearch)
                            }
                    }) {
                        Icon(
                            modifier = Modifier.size(56.dp),
                            painter = painterResource(id = R.drawable.rounded_radio_button_checked_24),
                            contentDescription = null,
                        )
                    }
                }
            },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState )
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Post")

            when (uiStateValue.status) {
                Status.SUCCESS -> {

                    Column(
                        modifier = Modifier
                    ) {

                        Column {
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = "COMENTARIO: ${uiStateValue.data?.comment}",
                                style = TextStyle(fontSize = 16.sp)
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = "EMAIL: ${uiStateValue.data?.email}",
                                style = TextStyle(fontSize = 16.sp)
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = "NOME: ${uiStateValue.data?.name}",
                                style = TextStyle(fontSize = 16.sp)
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = "ID: ${uiStateValue.data?.id}",
                                style = TextStyle(fontSize = 16.sp)
                            )

                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = "POST ID: ${uiStateValue.data?.postId}",
                                style = TextStyle(fontSize = 16.sp)
                            )
                        }
                    }
                }

                Status.ERROR -> {
                    ErrorScreen(uiStateError = uiStateValue.message.toString() + stringResource(id = R.string.enter_valid_id))
                }

                Status.LOADING -> ShimmerScreen()
            }
        }
    }
}