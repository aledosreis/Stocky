package com.stocky.Model;

public class History {

    String product;
    int qtd;
    String date;
    String tipoTransacao;

    public History() {

    }

    public History(String product, int qtd, String date, String transacao) {
        this.product = product;
        this.qtd = qtd;
        this.date = date;
        this.tipoTransacao = transacao;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
