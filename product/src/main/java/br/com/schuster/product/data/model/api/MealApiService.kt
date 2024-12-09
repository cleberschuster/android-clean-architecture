package br.com.schuster.product.data.model.api

import br.com.schuster.product.data.model.MealCategoriesResponse
import retrofit2.http.GET

interface MealApiService{
    @GET("categories.php")
    suspend fun getCategories(): MealCategoriesResponse
}