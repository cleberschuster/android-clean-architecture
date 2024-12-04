package br.com.schuster.androidcleanarchitecture.data.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Essa classe é responsável pela criação da instância do Retrofit.
* Retrofit é uma API que faz a transmissão de dados entre seu app e servidor por meio do JSON.
*
* Para que não seja necessário ficar criando várias instâncias de Retrofit a cada Serviço chamado,
* foi injetado no Koin usando single.
*
* Também foi implementado cache de 5MB que tem duração de 10 minutos, fazendo com que os Requests
* sejam retornados mais rapidamente pelo serviço.
*
*/

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
private const val URL_LOCAL_HOST = "http://10.0.2.2:8080"
//private const val BASE_URL = "https://your.base.url.here.com"
private const val CACHE_SIZE = 5 * 1024 * 1024L // 5 MB de cache

