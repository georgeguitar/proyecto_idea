'use strict'

const express = require ('express')
const productCtrl = require('../controllers/product')
const ideaCtrl = require('../controllers/idea')
const api = express.Router()

/*
api.get('/product', productCtrl.getProducts)
api.get('/product/:productId', productCtrl.getProduct)
api.post('/product', productCtrl.saveProduct)
api.put('/product/:productId', productCtrl.updateProduct)
api.delete('/product/:productId', productCtrl.deleteProduct)
*/

api.get('/idea', ideaCtrl.getIdeas)
api.get('/idea/:ideaId', ideaCtrl.getIdea)
api.post('/idea', ideaCtrl.saveIdea)
api.put('/idea/:ideaId', ideaCtrl.updateIdea)
api.delete('/idea/:ideaId', ideaCtrl.deleteIdea)
api.get('/votar/:ideaId/:usuarioId', ideaCtrl.votarIdea)
api.get('/quitar/:ideaId/:usuarioId', ideaCtrl.votarIdea)

module.exports = api