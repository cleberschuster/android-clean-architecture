package br.com.schuster.androidcleanarchitecture.di

import br.com.schuster.androidcleanarchitecture.data.api.PostApiService
import br.com.schuster.androidcleanarchitecture.data.datasource.RemotePostDataSource
import br.com.schuster.androidcleanarchitecture.data.datasource.RemotePostDataSourceImpl
import br.com.schuster.androidcleanarchitecture.data.repository.PostRepositoryImpl
import br.com.schuster.androidcleanarchitecture.data.retrofit.RetrofitService
import br.com.schuster.androidcleanarchitecture.domain.repository.PostRepository
import br.com.schuster.androidcleanarchitecture.domain.usecase.PostUseCase
import br.com.schuster.androidcleanarchitecture.presentation.feature.MainViewModel
import kotlinx.coroutines.Dispatchers
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
    factory<PostRepository> { PostRepositoryImpl(remoteDataSource = get(), dispatcherIO = Dispatchers.IO) }
}

val networkModules = module {
    single { RetrofitService.create<PostApiService>() }
}

val anotherModules = module {}
