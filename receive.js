#!/usr/bin/env node

const mongoose = require('mongoose')
const app = require('./app')
const config = require('./config_receive')
// const api = express.Router()

const express = require ('express')
var amqp = require('amqplib/callback_api');
const Idea = require('./models/idea')
const IdeaCtrl = require('./controllers/product')

// Se hace la conexion y se define la base de datos a utilizar
mongoose.connect(config.db, (err, res) => {
  if (err) {
      return console.log(`Error al conectar a la base de datos: ${err}`)
  }
  console.log('Conexion a la base de datos establecida...')

  app.listen(config.port, () => {
      console.log(`API REST corriendo en el recibidor http://localhost:${config.port}`)
  })
})

amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
  conn.createChannel(function(err, ch) {
    var q = 'IDEA';

    ch.assertQueue(q, {durable: false});
    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);
    ch.consume(q, function(msg) {
      var str = msg.content.toString();
      console.log(str);
      var message = JSON.parse(str);
      if (message.accion == 'USUARIO_ELIMINADO'){
        console.log(message.idUsuario);
        /*
        * Se esta utilizando mongoose, como manejar a mongoDB, por lo que se tiene que buscar con comandos de esa extension
        * http://mongoosejs.com/docs/guide.html
        * o si no utilizar una alternativa
        * Este script esta en C:\xampp\htdocs\tutoriales\api-rest
        * Ejecutar el script en la consola escribiendo 
        * node receive.js 
        * Si al ejecutar sale que se esta usando el puerto 3000 ejecutar
        * netstat -ano | findstr :3000
        * y con el comando 
        * tskill typeyourPIDhere
        * matar el proceso, es un problema que tiene node.js en windows
        * 
        * El otro script esta en C:\xampp\htdocs\tutoriales\rabbitmq\ejemplo01
        * en la otra consola send.js      
        */

 

       //Idea.deleteMany( { proposerId: 5 } );
       // console.log(Idea);
        
       Idea.deleteMany({ proposerId: message.idUsuario }, function (err) {
        //if (err) res.status(500).send({message: `Error al borrar las ideas: ${err}`})
        //res.status(200).send({message: `La ideas eliminadas`})
        console.log('Borrado');
      });



      }
      console.log(" [x] Received %s", msg.content.toString());
    }, {noAck: true});
  });
});

/*
amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
  conn.createChannel(function(err, ch) {

    var q = 'IDEA';

    ch.assertQueue(q, {durable: false});
    //ch.prefetch(1);
    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", q);
    ch.consume(q, function(msg) {
      var str = msg.content.toString()
      var message = JSON.parse(str)
      if (message.mensaje == 'USUARIO_ELIMINADO'){

        //Idea.remove
        
        console.log("Se ha borrado las ideas de ese usuario: ", message.idUsuario)
        // Se tiene que mandar el mensaje 
        // { "accion": "BORRAR_VOTOS", "idUsuario": "xx" }

        Idea.remove({ proposerId: message.idUsuario }, function (err) {
          if (err) return handleError(err);
          // removed!

          // Se va a mandar un mensaje para que se borre de las estadisticas esos votos
          amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, connx) {
            connx.createChannel(function(err, ch) {
                var q = 'ESTADISTICA';
                var msg = `{ "accion": "BORRAR_VOTOS", "idUsuario": "${message.idUsuario}" }`;

                ch.assertQueue(q, {durable: false});
                // Note: on Node 6 Buffer.from(msg) should be used
                ch.sendToQueue(q, new Buffer(msg));
                console.log(" [x] Sent %s", msg);
            });
            setTimeout(function() { connx.close(); }, 500);
          });
          
          console.log("Se habra eliminado de verdad???");
        });

      }
      console.log(" [x] Received %s", msg.content.toString());
    });
    setTimeout(function() { conn.close(); process.exit(0) }, 500);
  });
});
*/