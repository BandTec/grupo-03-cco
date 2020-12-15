var express = require('express');
var routes = express.Router();

/* GET home page. */
routes.get('/', function(req,res){
    res.sendfile(__dirname + "/public/html/index.html")
})

module.exports = routes;