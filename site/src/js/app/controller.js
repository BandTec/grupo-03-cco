// expreess recebe a requisão da biblioteca express
// bibliotecas/ frameworsks -> são códigos prontos
const express = require('express');

// variavel constante que recebe o retorno do arquivo newserial.js

const { ArduinoDataTemp } = require('./newserial')

// variavel constante que recebe o retorno do arquivo serialHumidity.js
const { ArduinoDataHumidity } = require('./serialHumidity')

// variavel constante que recebe o retorno do arquivo serialSwitch.js
const { ArduinoDataSwitch } = require('./serialSwitch')

// variavel constante que recebe o retorno do arquivo serialLuminosidity.js
const { ArduinoDataLuminosity } = require('./serialLuminosidity')

// variavel constante que recebe o retorno do arquivo database.js
// neste caso, inicia a conexão com o banco de dados.
const db = require('./database')

//instância de rota [url]
//está variavel será usada para chamar[usar] os metodos HTTP
//HTTP é o protocolo de comunicação utilizado ao acessar a internet.
// Toda vez que vc acessa um site vc utiliza destes metodos, quando vc busca informações utiliza o metodo GET
// quando envia informações para o site, utiliza o methodo post
// existe tbm o method update para atualizações e o methodo delete para exclusões.
const router = express.Router();

/*
    Todos os metodos do protocolo HTTP,
    necessitam de uma requisição [chamada] e de uma resposta
    Quando vc digita um url no navegador, vc está fazendo uma request,
    uma requisção para aquele endereço, quando vc visualiza já o conteudo da url,
    significa que o navegador te devolveu uma response, uma resposta.

    para saber mais:
    https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Overview
*/

// aqui solicitamos o metodo GET para o endereço '/'
// todo metodo precisará de um endereço e de uma função anonima [função sem nome que é executada sempre que houver a chamada]
//
router.get('/', (request, response, next) => {

    /// sum é a variavel de soma que recebe uma  lista reduzida
    // obs:  o reduce vai percorrer o vetor e tranforma-lo em uma unica informação
    let sum = ArduinoDataTemp.List.reduce((a, b) => a + b, 0);

    // average é média da variavel sum dividida pela pelo tamanho da variavel arduinodatatemp
    //ArduinoDataTemp recebe do arquivo newserial um objeto javascript, com lista e lista de hora
    let average = (sum / ArduinoDataTemp.List.length).toFixed(2);

    //sumhour é soma da lista de horas. O reduce vai percorrer est´´a lista e soma-lá, retornando o total dos valores.
    let sumHour = ArduinoDataTemp.ListHour.reduce((a, b) => a + b, 0);

    //averageHour é média da lista de horas. Recebe a soma das horas divida pela quantidade de horas que existe na lista
    let averageHour = (sumHour / ArduinoDataTemp.ListHour.length).toFixed(2);


    //A respota dessa requisição será um arquivo JSON [javascript object notation]
    // o json é basicamente um arquivo de objetos, que possuem chaves e valores, mais ou menos 
    // como no banco de dados, as entidades são os objetos,as chaves são os campos, e os valores são os dados daquela chave

    response.json({

        //este objeto js possui 6 chaves

        //a chave data receberá a lista da variavel arduinodatatemp[que é uma lista de dados e horas]
        data: ArduinoDataTemp.List,

        //a chave total vai receber o tamanho da lista que está armazenada no vetor arduino data temp
        total: ArduinoDataTemp.List.length,

        // a chave average vai receber o valor da variavel average
        //is nan verifica se o valor não é um número, se não for um número retorna true e se for retorna false 
        // então se average não for numero, a chave average receberá o valor 0 , 
        //agora se a variavel average for um numero a chave average receberá o valor da variavel da média
        average: isNaN(average) ? 0 : average,
        //a chave dataHour receberá o valor da lista de hora retornada do arquivo newserial.js
        dataHour: ArduinoDataTemp.ListHour,
        // a chave total hour receberá o tamanho desta lista de horas
        totalHour: ArduinoDataTemp.ListHour.length,
        //a chave averageHour seguirá a mesma lógica explicada no average, só que com o valor da média de horas
        averageHour: isNaN(averageHour) ? 0 : averageHour
    });

});

router.get('/humidity', (request, response, next) => {

    let sum = ArduinoDataHumidity.List.reduce((a, b) => a + b, 0);
    let average = (sum / ArduinoDataHumidity.List.length).toFixed(2);
    let sumHour = ArduinoDataHumidity.ListHour.reduce((a, b) => a + b, 0);
    let averageHour = (sumHour / ArduinoDataHumidity.ListHour.length).toFixed(2);

    response.json({
        data: ArduinoDataHumidity.List,
        total: ArduinoDataHumidity.List.length,
        average: isNaN(average) ? 0 : average,
        dataHour: ArduinoDataHumidity.ListHour,
        totalHour: ArduinoDataHumidity.ListHour.length,
        averageHour: isNaN(averageHour) ? 0 : averageHour
    });

});

router.get('/switch', (request, response, next) => {

    let sum = ArduinoDataSwitch.List.reduce((a, b) => a + b, 0);
    let average = (sum / ArduinoDataSwitch.List.length).toFixed(2);
    let sumHour = ArduinoDataSwitch.ListHour.reduce((a, b) => a + b, 0);
    let averageHour = (sumHour / ArduinoDataSwitch.ListHour.length).toFixed(2);

    response.json({
        data: ArduinoDataSwitch.List,
        total: ArduinoDataSwitch.List.length,
        average: isNaN(average) ? 0 : average,
        dataHour: ArduinoDataSwitch.ListHour,
        totalHour: ArduinoDataSwitch.ListHour.length,
        averageHour: isNaN(averageHour) ? 0 : averageHour
    });

});

router.get('/luminosity', (request, response, next) => {

    let sum = ArduinoDataLuminosity.List.reduce((a, b) => a + b, 0);
    let average = (sum / ArduinoDataLuminosity.List.length).toFixed(2);
    let sumHour = ArduinoDataLuminosity.ListHour.reduce((a, b) => a + b, 0);
    let averageHour = (sumHour / ArduinoDataLuminosity.ListHour.length).toFixed(2);

    response.json({
        data: ArduinoDataLuminosity.List,
        total: ArduinoDataLuminosity.List.length,
        average: isNaN(average) ? 0 : average,
        dataHour: ArduinoDataLuminosity.ListHour,
        totalHour: ArduinoDataLuminosity.ListHour.length,
        averageHour: isNaN(averageHour) ? 0 : averageHour
    });


    //aqui temos o método post, ou seja, de envio de dados para o servidor
    // este método será acionado no endereço '/sendData'
    // este é o método de acesso ao banco de dados
    //é aqui que será colocado os comandos sql para o banco
    router.post('/sendData', (request, response) => {

        //aqui o exemplo utilizado foi o de temperatura, mas basta colocar o do projeto
        temperature = ArduinoDataTemp.List[ArduinoDataTemp.List.length - 1];
        //luminosidade = ArduinoDataLuminosity.List[ArduinoDataLuminosity.List.length -1]

        //está varivael receberá o comando, podendo ser qualquer comando do banco
        ///no caso apresentado pela professora, ela fez apenas o de inserir ..
        //mas pode-se usar o select, update e delete
        var sql = "INSERT INTO medidas (tipo, valorLido) VALUES ('temperatura',?)";

        //aqui pegamos a variavel que fez a conexão com o banco, db e fazemos um query ['consulta', 'acesso']
        // está query receberá como parametros, o comando sql, e o valor, alpem disso receberá uma função anonima
        //com  paramentro de erro e resultado
        db.query(sql, temperature.toFixed(2), function (err, result) {
            //aqui verifica-se se o erro é true, ou seja, se houve erro;
            // se houver erro, trata ele com throw [meio que passa ele pra frente] e mostra no console
            if (err) throw err;
            console.log("Number of records inserted: " + result.affectedRows);
        });

        //se não der erro, então o status dá resposta é 200
        // toda resposta bem sucedida começa com 200
        // acima disso é uma resposta diferente, cada tipo de resposta possui um número dentro do protocolo HTTTP
        // que varia de 100 á 511 -> cada um com seu significado
        //para saber mais: acesse o site da mozilla https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Overview
        response.sendStatus(200);
    })

});
//aqui faz a exportação da rota, para que sej apossivel fazer o require em outros arquivos js
module.exports = router;