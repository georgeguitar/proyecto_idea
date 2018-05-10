'use strict'

const Idea = require('../models/idea')
var amqp = require('amqplib/callback_api')
var logger = require('../log')

function getIdea (req, res){
    let ideaId = req.params.ideaId

    Idea.findById(ideaId, (err, idea) => {
        if (err) return res.status(500).send({message: `Error al realizar la petición ${err}`})
        if (!idea) return res.status(404).send({message: `La idea no existe`})

        logger.info("Se ha listado una idea " + req.params.ideaId)
        res.status(200).send({ idea })
    })
}

function getIdeas (req, res){
    Idea.find({}, (err, ideas) =>{
        if (err) return res.status(500).send({message: `Error al realizar la petición ${err}`})
        if (!ideas) return res.status(404).send({message: `No existen las ideas`})

        logger.info("Se ha listado las ideas")
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

    Idea.findByIdAndUpdate(ideaId, update, (err, ideaUpdated) =>{
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

function deleteIdeas (req, res){
    // Falta hacer eso
    // { "accion": "BORRAR_VOTOS", "idIdea": "x1, x2, x3" }
}

function votarIdea (req, res){
    // Falta hacer eso
    // { "accion": "VOTAR_IDEA", "idUsuario": "XX", "idIdea": "xx"}
    let ideaId = req.params.ideaId
    let usuarioId = req.params.usuarioId

    amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
        conn.createChannel(function(err, ch) {
            var q = 'ESTADISTICA';
            var msg = `{ "accion": "VOTAR_IDEA", "idUsuario": "${usuarioId}", "idIdea": "${ideaId}" }`;

            ch.assertQueue(q, {durable: false});
            // Note: on Node 6 Buffer.from(msg) should be used
            ch.sendToQueue(q, new Buffer(msg));
            console.log(" [x] Sent %s", msg);
        });
        setTimeout(function() { conn.close(); }, 500);
    });

    res.status(200).send({message: `Se ha mandado para que se vote por la idea ${usuarioId} ${ideaId}`})
}

function quitarIdea (req, res){
    // Falta hacer eso
    // { "accion": "QUITAR_IDEA", "IdUsuario": "XX", "idIdea": "xx"}
    let ideaId = req.params.ideaId
    let usuarioId = req.params.usuarioId

    amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
        conn.createChannel(function(err, ch) {
            var q = 'ESTADISTICA';
            var msg = `{ "accion": "QUITAR_IDEA", "idUsuario": "${usuarioId}", "idIdea": "${ideaId}" }`;

            ch.assertQueue(q, {durable: false});
            // Note: on Node 6 Buffer.from(msg) should be used
            ch.sendToQueue(q, new Buffer(msg));
            console.log(" [x] Sent %s", msg);
        });
        setTimeout(function() { conn.close(); }, 500);
    });

    res.status(200).send({message: `Se ha mandado para que se quite el voto por la idea ${usuarioId} ${ideaId}`})
}

module.exports = {
    getIdea,
    getIdeas,
    saveIdea,
    updateIdea,
    deleteIdea,
    deleteIdeas,
    votarIdea,
    quitarIdea
}
