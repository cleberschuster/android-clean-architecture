package br.com.schuster.product.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealCategory(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
): Parcelable
