#!/usr/bin/env node

const mongoose = require('mongoose')
const app = require('./app')
const config = require('./config')

var amqp = require('amqplib/callback_api');
const productCtrl = require('./controllers/product')

const Idea = require('./models/idea')

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
        /*
        console.log("Entrando a eliminar");   
        Idea.remove({ proposerId: message.idUsuario }, function (err) {
          console.log(err);
          if (err) return console.log(err);
          // removed!

          console.log("Ideas eliminadas");
        });
        */
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