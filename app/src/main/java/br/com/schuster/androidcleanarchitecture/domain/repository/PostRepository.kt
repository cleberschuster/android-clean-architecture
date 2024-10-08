package br.com.schuster.androidcleanarchitecture.domain.repository

import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPost(id: Int): Flow<ObjectPresentation>
}