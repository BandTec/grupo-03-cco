const express = require('express');
const leiturasController = require('./controllers/LeituraController');

const routes = express.Router();

routes.get('/', function(req,res){
    res.sendfile(__dirname + "/public/html/index.html")
})

routes.get('/return', leiturasController.leituraBD); 
//routes.get('/pythohms/return', PythohmsController.marise); 


module.exports = routes;
