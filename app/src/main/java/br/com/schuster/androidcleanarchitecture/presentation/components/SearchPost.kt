package br.com.schuster.androidcleanarchitecture.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schuster.androidcleanarchitecture.R

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Number,
    currentSearchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchDispatched: () -> Unit,
    onCleanTextPressed: () -> Unit,
) {
    Box(
        modifier = modifier.height(56.dp).padding(end = 16.dp)
    ) {
        TextField(
            modifier = Modifier.clip(CircleShape.copy(all = CornerSize(10.dp))).fillMaxWidth(),
            value = currentSearchText,
            colors = TextFieldDefaults.colors(
                Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray
            ),
            onValueChange = {
                onSearchTextChanged(it)
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_list_main_search),
                    color = Color.Gray
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = Color.Gray
            ),
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                SearchIcon()
            },
            trailingIcon = {
                when (currentSearchText.isEmpty()) {
                    false -> CloseButton(action = onCleanTextPressed)
                    true -> {}
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search, keyboardType = keyboardType),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchDispatched()
            }),
        )
    }
}

@Composable
fun DefaultIcon(
    modifier: Modifier = Modifier,
    searchIcon: ImageVector = Icons.Default.Search,
    iconColor: Color = Color.White,
    contentDescription: String = "",
    onIconClickAction: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = onIconClickAction
    ) {
        Icon(
            imageVector = searchIcon,
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}

@Composable
fun SearchIcon() {
    DefaultIcon(
        searchIcon = Icons.Filled.Search,
        contentDescription = stringResource(id = R.string.icon_search),
        iconColor = Color.Gray
    )
}

@Composable
fun CloseButton(action: () -> Unit = {}) {
    DefaultIcon(
        searchIcon = Icons.Default.Close,
        contentDescription = stringResource(id = R.string.icon_close),
        onIconClickAction = action,
        iconColor = Color.Gray
    )
}

@Composable
@Preview
fun SearchTopBarEmptyPreview() {
    SearchTopBar(
        currentSearchText = "",
        onSearchTextChanged = {},
        onSearchDispatched = {},
        onCleanTextPressed = {},
    )
}

@Composable
@Preview
fun SearchTopBarPreview() {
    SearchTopBar(
        currentSearchText = "Texto de busca",
        onSearchTextChanged = {},
        onSearchDispatched = {},
        onCleanTextPressed = {},
    )
}
