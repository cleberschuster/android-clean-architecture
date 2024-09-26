package br.com.schuster.androidcleanarchitecture.data.api

import br.com.schuster.androidcleanarchitecture.data.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Path

/*
* Esta classe é responsável por conter seus métodos
* de chamada da API (métodos GET, POST, PATCH, etc);
*/

interface PostApiService {

    @GET("/comments/{id}")
    suspend fun getPost(@Path("id") id: Int): PostResponse
}