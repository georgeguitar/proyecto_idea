#!/usr/bin/env node
const dbConnection = require('../../config/dbConnection');
var amqp = require('amqplib/callback_api');
var bcrypt=require('bcryptjs');
 var logger=require('../../config/log')

module.exports = app => {

  const connection = dbConnection();

  app.get("/",(req,res)=>{
    //res.send("microservicio usuario")
    res.render('user/registrar');
  });

  app.get('/user', (req, res) => {
    connection.query('SELECT * FROM users', (err, result) => {
      //res.json(result);
      res.render('user/user', {
        users: result
      });
    });
    logger.info("Se listaron los Usuario");
  });

  app.post('/registrar', (req, res) => {
    //var salt = bcrypt.genSaltSync(10);
    //var password = bcrypt.hashSync(req.body.password, salt);
    const { login, name, password} = req.body;
    connection.query('INSERT INTO users SET ? ',
      {
        login,
        name,
        password
      }
    , (err, result) => {
      amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
        conn.createChannel(function(err, ch) {
            var q = 'USUARIOS';
            //var msg = '{ "operation": "INSERTAR_USUARIO", "id":'+ req.params.id +'}'//"${result.insertId}" }`;
              var msg=`{ "Mensaje": "USUARIO_REGISTRADO", "idUsuario": "${result.insertId}" }`;
            ch.assertQueue(q, {durable: true});
            ch.sendToQueue(q, new Buffer(msg));
            console.log(" [x] Sent %s", msg);
        });
         setTimeout(function() { conn.close();
          //process.exit(0)
         }, 500);
         logger.info("Se Registro un nuevo Usuario con id: "+ result.insertId);
    });
      //res.redirect('/login');
      res.json({
        data:result,
        message:'usuario Registrado'})
    });

  });

  app.get("/login",(req,res)=>{
    //res.send("microservicio usuario")
    res.render('user/login');
  });

  app.post('/login', (req, res) => {
    const {id, login, password } = req.body;
    connection.query('SELECT * FROM users WHERE login = ?',[login], function (error, results, fields) {
      if (error) {
          res.json({
            status:false,
            message:'there are some error with query'
            })
      }else{
        if(results.length >0){

          //if(bcrypt.compareSync(req.param.password,results[0].password)){
            //console.log(req.param.password);
          if(password==results[0].password){
              connection.query('SELECT id FROM users WHERE login =?',[login], (err, result) => {
                var idU=JSON.parse(JSON.stringify(result))[0].id;

              //  console.log(JSON.parse(JSON.stringify(result))[0].id);
                amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
                  conn.createChannel(function(err, ch) {
                      var q = 'USUARIOS';

                      //var msg = '{ "operation": "INSERTAR_USUARIO", "id":'+ req.params.id +'}'//"${result.insertId}" }`;
                      var msg='{ "Mensaje": "USUARIO_LOGUEADO", "idUsuario":'+ idU+ '}';
                      ch.assertQueue(q, {durable: true});
                      ch.sendToQueue(q, new Buffer(msg));
                      console.log(" [x] Sent %s", msg);
                  });
                   setTimeout(function() { conn.close();
                    //process.exit(0)
                   }, 500);
                 });
                //res.redirect('/user');
               res.json({
                    status:true,
                    message:'usuario Logueado'
                });
                logger.info("Se logueo el  Usuario con id: "+ idU);
                });


            }else{
                res.json({
                  status:false,
                  message:"Email and password no se encontro"
                 });
            }
        }
        else{
          res.json({
              status:false,
            message:"Email no existe"
          });
        }
      }
    });
});



  app.get('/update/:id', (req, res) => {
    const { id } = req.params;
    connection.query('SELECT * FROM users WHERE id= ? ',[id]
    , (err, result) => {
      res.render('user/userEdit',{
        users:result[0]
      });
    });
  });
  app.post('/update/:id', (req, res) => {
    const { id } = req.params;
    const newUser = req.body;
    connection.query('UPDATE users set ? WHERE id=? ',[newUser,id]
    , (err, result) => {
      res.redirect('/user');
    });
    logger.info("Se actualizo el Usuario con id: "+ id);
  });


  app.get('/delete/:id', (req, res) => {
    const {id} = req.params;
  //  console.log(req.params);
    //res.send('work')
    connection.query('DELETE FROM users WHERE id= ? ', [id]
    , (err, result) => {

      amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
      conn.createChannel(function(err, ch) {
        var q = 'IDEAS';
        //var msg = '{idUser:'+req.params.id+'}';
        var msg='{ "accion": "BORRAR_IDEAS", "idUsuario":'+ req.params.id+ '}';        
        ch.assertQueue(q, {durable: false});
        ch.sendToQueue(q, new Buffer(msg));
        console.log(" [x] Sent %s", msg);      
       
      });
      setTimeout(function() { conn.close();
        //process.exit(0)
      },   500);
      });

      amqp.connect('amqp://wxlrebpd:cVgfbjWvPcTGZ_VlfkMLCcfn6TQhtxAL@llama.rmq.cloudamqp.com/wxlrebpd', function(err, conn) {
        conn.createChannel(function(err, ch) {
          var q = 'ESTADISTICA';
          //var msg = '{idUser:'+req.params.id+'}';
          var msg='{ "accion": "BORRAR_VOTOS", "idUsuario":'+ req.params.id+ '}'       
          ch.assertQueue(q, {durable: false});
          ch.sendToQueue(q, new Buffer(msg));
          console.log(" [x] Sent %s", msg);     
         
        });
        setTimeout(function() { conn.close();
          //process.exit(0)
        },   500);
        });

      res.redirect('/user');
      logger.info("Se elimino el Usuario con id: "+ id);
    });



  });




};
