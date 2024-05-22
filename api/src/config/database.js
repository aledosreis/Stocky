const knex = require('knex');
require('dotenv/config');

module.exports = knex({
  client: 'pg',
  connection: {
    host : process.env.PG_HOST,
    user : process.env.PG_USER,
    password : process.env.PG_PASSWORD,
    database : process.env.PG_DATABASE,
  },
useNullAsDefault: true,
})