package br.com.schuster.androidcleanarchitecture.domain.mapper

import br.com.schuster.androidcleanarchitecture.domain.model.ObjectDomain
import br.com.schuster.androidcleanarchitecture.presentation.model.ObjectPresentation
import br.com.schuster.androidcleanarchitecture.utils.Mapper

class ObjectToPresentationMapper: Mapper<ObjectDomain, ObjectPresentation> {

    override fun map(source: ObjectDomain): ObjectPresentation {
        return ObjectPresentation(
            postId = source.postId,
            id = source.id,
            email = source.email,
            name = source.name,
            comment = source.comment
        )
    }
}