package br.com.schuster.androidcleanarchitecture.di

import br.com.schuster.androidcleanarchitecture.data.api.PostApiService
import br.com.schuster.androidcleanarchitecture.data.datasource.RemotePostDataSource
import br.com.schuster.androidcleanarchitecture.data.datasource.RemotePostDataSourceImpl
import br.com.schuster.androidcleanarchitecture.data.repository.PostRepositoryImpl
import br.com.schuster.androidcleanarchitecture.data.retrofit.HttpClient
import br.com.schuster.androidcleanarchitecture.data.retrofit.RetrofitClient
import br.com.schuster.androidcleanarchitecture.domain.repository.PostRepository
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.presentation.feature.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
* Neste arquivo você deve declarar todas as suas dependências injetadas.
* Lembre-se de manter cada classe em sua camada, como feito abaixo.
* Obs.: Se o arquivo ficar muito grande, é melhor criar um arquivo para cada camada.
*/

val domainModules = module {
    factory { PostUseCase(repository = get()) }
}

val presentationModules = module {
    viewModel { MainViewModel(useCase = get()) }
}

val dataModules = module {
    factory<RemotePostDataSource> { RemotePostDataSourceImpl(api = get()) }
    factory<PostRepository> { PostRepositoryImpl(remoteDataSource = get()) }
}

val networkModules = module {
    single { RetrofitClient(application = androidContext()).newInstance() }
    single { HttpClient(get()) }
    factory { get<HttpClient>().create(PostApiService::class.java) }
}

val anotherModules = module {}
