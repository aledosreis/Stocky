const { raw } = require('../config/database');
const db = require('../config/database');

module.exports = {
  async index(request, response) {
    const result = await db('estoque')
                  .join('produtos', 'estoque.id_produto', '=', 'produtos.id_produto')
                  .select()
                  .orderBy('estoque.id_produto');
    return response.status(200).json(result);
  },

  async adiciona(request, response) {
    const {id_produto, qtd_estoque} = request.body;
    if (qtd_estoque > 0) {
      await db('estoque').where('id_produto', '=', id_produto).increment('qtd_estoque', qtd_estoque);
      await db('historico').insert({id_produto, qtd_movimentada: qtd_estoque, tipo_transacao: 1});
      return response.status(200).json();
    } else {
      return response.status(400).json();
    }
  },

  async retira(request, response) {
    const {id_produto, qtd_estoque} = request.body;
    if (qtd_estoque > 0) {
      const atualiza  = await db('estoque')
                              .where('id_produto', '=', id_produto)
                              .andWhere('qtd_estoque', '>=', qtd_estoque)
                              .decrement('qtd_estoque', qtd_estoque);
      if (atualiza  == 1) {
        await db('historico').insert({id_produto, qtd_movimentada: qtd_estoque, tipo_transacao: 2}); 
        return response.status(200).json();
      } else {
        return response.status(400).json();
      }
    } else {
      return response.status(400).json();
    }
  }
};