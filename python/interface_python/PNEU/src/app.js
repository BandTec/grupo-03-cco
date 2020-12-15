const express = require('express');
const routes = require('./routes/leitura');
//const leituraRouter = require('./routes.js');

require('./database/Connection.js');

const appinho = express(); 
appinho.use(express.json());

appinho.use(express.static(__dirname+'/public/img'));
appinho.use(express.static(__dirname+'/public/css'));
appinho.use(express.static(__dirname+'/public/html'));
appinho.use(express.static(__dirname+'/public/js')); 
appinho.use('/leitura',routes);

appinho.use(routes);

appinho.listen(3000);


