const express = require('express');
const PythohmsController = require('./controllers/PythohmsController');
const Pythonics = ('./controllers/Pythonics.js');
const Pythohms = ('./controllers/PythohmsController.js');

const routes = express.Router();



routes.get('/', function(req,res){
    res.sendfile(__dirname + "/public/html/index.html")
})

//routes.get('pythonics/return', Pythonics.buenas); 
//routes.get('/pythohms/return', PythohmsController.marise); 


module.exports = routes;
