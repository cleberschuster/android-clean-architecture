package br.com.schuster.androidcleanarchitecture.presentation.feature

import android.content.Context
import androidx.annotation.StringRes

sealed class UiEvent {
//    data class ShowSnackbars(val message: String? = null) : UiEvent()

    class ShowSnackbar(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiEvent()

    fun asString(context: Context?): String {
        return when (this) {
            is ShowSnackbar -> context?.getString(resId, args).orEmpty()
//            is ShowSnackbars -> message.toString()
        }
    }
}

