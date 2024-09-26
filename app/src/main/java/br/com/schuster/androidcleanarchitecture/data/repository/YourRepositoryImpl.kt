package br.com.schuster.androidcleanarchitecture.data.repository

import br.com.schuster.androidcleanarchitecture.data.datasource.RemotePostDataSource
import br.com.schuster.androidcleanarchitecture.domain.mapper.ObjectToPresentationMapper
import br.com.schuster.androidcleanarchitecture.domain.repository.PostRepository
import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/*
* Esta camada é responsável por chavear entre as fontes de dados.
* Por exemplo, se você tivesse armazenamento local (usando ROOM, por exemplo),
* você teria um localDataSource, e este repository seria responsável pela
* lógica de saber se pega os dados localmente ou da API.
*
* Após a chamada, é realizado a transformação do objeto da camada de DOMAIN
* para um objeto da camada de PRESENTATION por meio do mapper.
*
*/

class YourRepositoryImpl(
    private val remoteDataSource: RemotePostDataSource
) : PostRepository {

    private val mapper: ObjectToPresentationMapper = ObjectToPresentationMapper()

    override suspend fun getPost(id: Int): Flow<ObjectPresentation> = flow {

        val response = withContext(Dispatchers.IO) {
            mapper.map(remoteDataSource.getPost(id))
        }
        emit(response)
    }
}