const db = require('../config/database');

module.exports = {
  async index(request, response) {
    const result = await db('produtos').select().orderBy('id_produto');
    return response.status(200).json(result);
  },

  async create(request, response) {
    const {descricao} = request.body;
    const insertedProductId = await db('produtos').insert({descricao}).returning('id_produto');
    const newProduto = insertedProductId[0];
    await db('estoque').insert({id_produto: newProduto, qtd_estoque: 0});
    return response.status(200).send();
  },

  async delete(request, response) {
    const {id_produto} = request.body;
    await db('historico').delete().where('id_produto', '=', id_produto);
    await db('estoque').delete().where('id_produto', '=', id_produto);
    await db('produtos').delete().where('id_produto', '=', id_produto);
    return response.status(200).send();
  }
};