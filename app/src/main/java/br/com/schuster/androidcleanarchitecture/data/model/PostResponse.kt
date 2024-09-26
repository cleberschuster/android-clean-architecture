package br.com.schuster.androidcleanarchitecture.data.model

import com.google.gson.annotations.SerializedName

/*
* Este objeto é o que será retornado por sua API.
*
* IMPORTANTE: Mesmo que o campo da API seja exatamente o mesmo nome
* que você quer para sua variável, sempre declare-a com o @SerializedName, pois se em algum momento
* o nome do campo na API mudar, não impactará seu código inteiro, somente essa classe.
*
* Caso você tivese um dataSource local, que pegasse os campos do ROOM, por exemplo, você teria
* OUTRO objeto de Response. Lembre-se: Evitar de reutilizar objetos, pois em caso de mudanças,
* o impacto em seu aplicativo será maior do que o desejado.
*/

data class PostResponse(
    val postId: Int? = null,
    val id: Int? = null,
    val email: String? = null,
    val name:String? = null,

    @SerializedName("body")
    val comment: String? = null
)