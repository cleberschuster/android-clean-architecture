package br.com.schuster.androidcleanarchitecture.domain.usecase

import br.com.schuster.androidcleanarchitecture.domain.repository.PostRepository
import kotlinx.coroutines.flow.filter

/*
* Esta classe é responsável por realizar os casos de uso, chamando seu repository.
* Deve-se ter um UseCase para cada ação do seu aplicativo.
* Exemplo: Se seu aplicativo pode salvar dados, excluir dados e pegar dados,
* então, deverá ter 3 classes UseCase distintos.
*
*/

class PostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(id: Int) = repository.getPost(id).filter {
        it.id!! < 1000
    }
}