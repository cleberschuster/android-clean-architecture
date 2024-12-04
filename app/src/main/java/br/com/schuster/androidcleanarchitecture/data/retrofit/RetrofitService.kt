package br.com.schuster.androidcleanarchitecture.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

object RetrofitService {

    private fun criarHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

//    private fun aplicarToken(chain: Interceptor.Chain): Request {
//        return if (UsuarioLogado.isLogado()) {
//            val token = UsuarioLogado.token
//            chain.request()
//                .newBuilder()
//                .addHeader("Authorization", "${token.tipo} ${token.token}")
//                .build()
//        } else {
//            chain.request()
//        }
//    }

    val service: Retrofit = Retrofit.Builder()
        .client(criarHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create(): T = service.create(T::class.java)

}