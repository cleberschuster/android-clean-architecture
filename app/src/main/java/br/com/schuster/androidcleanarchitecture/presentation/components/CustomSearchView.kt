package br.com.schuster.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Number,
) {

    TextField(
        value = search,
        modifier = modifier
            .clip(CircleShape.copy(all = CornerSize(10.dp))),
        onValueChange = onValueChange,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
        placeholder = { Text(text = "Search") },
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
    )
}