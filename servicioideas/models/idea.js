'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema

const IdeaSchema = Schema({
    idea: String,
    proposerName: String,
    proposerEmail: String,
    proposerId: String,
    votes: {Type: Number, default: 0}
})

module.exports = mongoose.model('Idea', IdeaSchema)