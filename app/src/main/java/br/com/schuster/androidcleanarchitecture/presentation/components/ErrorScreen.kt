package br.com.schuster.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorScreen(uiStateError: String) {

    Column {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Ocorreu um erro $uiStateError",
            style = TextStyle(fontSize = 18.sp),
            color = Color.Red
        )

        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Tente novamente mais tarde",
            style = TextStyle(fontSize = 16.sp),
            color = Color.Blue,
        )
        Spacer(modifier = Modifier.padding(4.dp))
    }
}