const express = require('express');
const productController = require('../controllers/ProductsController');
const stockController = require('../controllers/StockController');
const historyController = require('../controllers/HistoryController');
const versionCheck = require('./version');

const routes = express.Router();

routes.get("/products", productController.index);
routes.post("/products", productController.create);
routes.post("/products/delete", productController.delete);
routes.get("/stock", stockController.index);
routes.post("/stock/add", stockController.adiciona);
routes.post("/stock/remove", stockController.retira);
routes.get("/history", historyController.index);
routes.get("/update", versionCheck.update);

module.exports = routes;