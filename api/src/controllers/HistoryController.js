const db = require('../config/database');

module.exports = {
  async index(request, response) {
    const result = await db('historico')
                        .where('dt_transacao', '>=', db.raw('(SELECT CURRENT_DATE -30)'))
                        .join('produtos', 'historico.id_produto', '=', 'produtos.id_produto')
                        .select()
                        .orderBy('id_transacao',"desc");
    return response.status(200).json(result);
  }
};
