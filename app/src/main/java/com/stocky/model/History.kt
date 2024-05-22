package com.stocky.model

data class History(
    var product: String? = null,
    var qtd: Int = 0,
    var date: String? = null,
    var tipoTransacao: String? = null
)