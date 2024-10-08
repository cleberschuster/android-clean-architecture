package br.com.schuster.androidcleanarchitecture.presentation.model

import com.google.gson.annotations.SerializedName

data class ObjectPresentation(
    val postId: Int? = null,
    val id: Int? = null,
    val email: String? = null,
    val name:String? = null,

    @SerializedName("body")
    val comment: String? = null
)
