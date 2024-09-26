package br.com.schuster.androidcleanarchitecture.data.datasource

import br.com.schuster.androidcleanarchitecture.domain.model.ObjectDomain

interface RemotePostDataSource {

    suspend fun getPost(id: Int): ObjectDomain
}