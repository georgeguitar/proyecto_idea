'use strict'

const mongoose = require('mongoose')
const app = require('./app')
const config = require('./config')

const process = require('child_process')

// Se realiza el folk del nuevo proceso
const child = process.fork('receive.js')

// Se hace la conexion y se define la base de datos a utilizar
mongoose.connect(config.db, (err, res) => {
    if (err) {
        return console.log(`Error al conectar a la base de datos: ${err}`)
    }
    console.log('Conexion a la base de datos establecida...')

    app.listen(config.port, () => {
        console.log(`API REST corriendo en http://localhost:${config.port}`)
    })
})
