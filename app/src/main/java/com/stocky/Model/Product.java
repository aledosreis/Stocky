package com.stocky.Model;

public class Product {

    private String id;
    private String descricao;
    private int qtdEstoque;
    private String dt_alteracao;

    public Product() {

    }

    public Product(String id, String descricao, int qtdEstoque, String dt_alteracao) {
        this.id = id;
        this.descricao = descricao;
        this.qtdEstoque = qtdEstoque;
        this.dt_alteracao = dt_alteracao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getDt_alteracao() {
        return dt_alteracao;
    }

    public void setDt_alteracao(String dt_alteracao) {
        this.dt_alteracao = dt_alteracao;
    }
}
