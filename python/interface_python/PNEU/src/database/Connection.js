const Sequelize = require('sequelize');
const dbconfig = require('../config/database');

const Leitura = require('../models/valorleitura');
const Usuario = require('../models/usuario');
const Servidor = require('../models/servidor');
const Componente = require('../models/componente');
const Localizacao = require('../models/localizacao');

const connection = new Sequelize(dbconfig);

Leitura.init(connection);
Servidor.init(connection);
Usuario.init(connection);
Componente.init(connection);
Localizacao.init(connection);

module.exports = connection;