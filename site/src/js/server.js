/* Utilizar Express para a criação de um servidor node
Para isso precisamos criar uma variavel para recebe-lo.
O express é um pacote[conjunto de códigos]  responsavel por interpretar as rotas [urls] +-
*/
const express = require('express');

//body-parser é uma 'biblioteca/framework' capaz de converter o corpo
// das requisições para outros formatos ex:json
const bodyParser = require('body-parser');

/*
Com a utilização do express, para criar o servidor basta chama-lo como uma função,
e como vamos reutiliza-lo, precisamos guarda-lo em uma variavel.
*/
const app = express();


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use((request, response, next) => {
    response.header("Access-Control-Allow-Origin", "*");
    response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use('/api', require('./app/controller'));
/* 
Agora precisamos colocar nosso servidor em ação, para isso precisaremos de um método chamado listen(),
Esse método recebe como parametro a porta onde o servidor irá trabalhar,

Para testar utili-ze no terminal o comando 
    node src/js/server.js 
No navegador coloque na barra de endereço
    localhost:3000
A resposta pode ser um "canot get/" ou o que estiver dentro da chamada para o método GET (mais a frente)
*/
const server = app.listen(3000);
//mostrando no console[cmd, git bash ,terminal] onde o servidor está rodando e como encerrar
console.log("Servidor rodando na porta : %s", server.address().port);
console.log("Para encerrar o servidor: ctrl + C");