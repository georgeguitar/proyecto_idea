'use strict'

const Idea = require('../models/idea')

function getIdea (req, res){
    let ideaId = req.params.ideaId

    Idea.findById(ideaId, (err, idea) => {
        if (err) return res.status(500).send({message: `Error al realizar la petición ${err}`})
        if (!idea) return res.status(404).send({message: `La idea no existe`})

        res.status(200).send({ idea })
    })
}

function getIdeas (req, res){
    Idea.find({}, (err, ideas) =>{
        if (err) return res.status(500).send({message: `Error al realizar la petición ${err}`})
        if (!ideas) return res.status(404).send({message: `No existen las ideas`})

        res.status(200).send({ ideas })
    })
}

function saveIdea (req, res){
    console.log('POST /api/idea')
    console.log(req.body)

    let idea = new Idea()
    idea.idea = req.body.idea
    idea.proposerName = req.body.proposerName
    idea.proposerEmail = req.body.proposerEmail
    idea.proposerId = req.body.proposerId
    // Se evaluara si se coloca este campo o no, ya que por defecto comienza en 0
    idea.votes = 0

    idea.save((err, ideaStored) => {
        if (err) res.status(500).send({ message: `Error al salvar en la base de datos: ${err}` })

        res.status(200).send({idea: ideaStored})
    })
}

function updateIdea (req, res){
    let ideaId = req.params.ideaId
    // Se recoge todo lo que pase del body, ya que no sabemos lo que se va a actualizar
    let update = req.body

    Product.findByIdAndUpdate(ideaId, update, (err, ideaUpdated) =>{
        if (err) res.status(500).send({message: `Error al actualizar la idea ${err}`})

        res.status(200).send({ idea: ideaUpdated})
    })
}

function deleteIdea (req, res){
    let ideaId = req.params.ideaId

    Idea.findById(ideaId, (err, idea) => {
        if (err) res.status(500).send({message: `Error al borrar la idea: ${err}`})
        
        idea.remove(err => {
            if (err) res.status(500).send({message: `Error al borrar La idea: ${err}`})
            res.status(200).send({message: `La idea ha sido eliminada`})
        })          
    })
}

module.exports = {
    getIdea,
    getIdeas,
    saveIdea,
    updateIdea,
    deleteIdea
}
