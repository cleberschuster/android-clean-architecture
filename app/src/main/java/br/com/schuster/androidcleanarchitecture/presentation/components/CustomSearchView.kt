package br.com.schuster.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {


        TextField(value = search,
            modifier = modifier

            .clip(CircleShape)
            .background(Color(0XFF101921)),
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                Color(0XFF101921),
                Color(0XFF888D91),
                Color(0XFF888D91),
                Color(0XFF888D91),
                Color.White,
                focusedIndicatorColor = Color.Transparent, cursorColor = Color(0XFF070E14)
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            trailingIcon = { Icon(imageVector = Icons.Default.Clear, contentDescription = "") },
            placeholder = { Text(text = "Search") }
        )


}