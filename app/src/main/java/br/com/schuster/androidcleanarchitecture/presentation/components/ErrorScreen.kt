package br.com.schuster.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.schuster.androidcleanarchitecture.R

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, uiStateError: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_error_72), contentDescription = ""
        )
        Text(text = "Ocorreu um erro $uiStateError", modifier = Modifier.padding(16.dp))
    }
}