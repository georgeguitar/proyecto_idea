'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const ProductSchema = Schema({
    name: String,
    picture: String,
    proce: Number,
    category: { type: String, enum: ['computers', 'phones', 'accesories']},
    description: String
})

module.exports = mongoose.model('Product', ProductSchema)
