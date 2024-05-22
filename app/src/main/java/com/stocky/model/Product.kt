package com.stocky.model

data class Product(
    var id: String? = null,
    var descricao: String? = null,
    var qtdEstoque: Int = 0,
    var dt_alteracao: String? = null
)