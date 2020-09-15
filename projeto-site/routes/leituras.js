var express = require('express');
var router = express.Router();
var sequelize = require('../models').sequelize;
var Leitura = require('../models').Leitura;
var banco = require('./bancoPneu.js');

/* Recuperar as últimas N leituras */
router.get('/ultimas', function (req, res, next) {

	// quantas são as últimas leituras que quer? 8 está bom?
	const limite_linhas = 7;

	console.log(`Recuperando as últimas ${limite_linhas} leituras`);

	const instrucaoSql = `select top ${limite_linhas} 
						QtdCarros, 
						fkPonto, 
						momento,
						FORMAT(momento,'HH:mm:ss') as momento_grafico 
						from registro order by idRegistro desc`;

	sequelize.query(instrucaoSql, {
		model: Leitura,
		mapToModel: true
	})
		.then(resultado => {
			console.log(`Encontrados: ${resultado.length}`);
			res.json(resultado);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});
});

// estatísticas (max, min, média, mediana, quartis etc)
router.get('/estatisticas', function (req, res, next) {

	console.log(`Recuperando as estatísticas atuais`);

	const instrucaoSql = `select 
							min(qtdCarros) as min_carro, 
							avg(qtdCarros) as media_carro, 
							max(qtdCarros) as max_carro
						from registro;`;

	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
		.then(resultado => {
			res.json(resultado[0]);
		}).catch(erro => {
			console.error(erro);
			res.status(500).send(erro.message);
		});

});


router.get('/pegarRodoviasBD/:idCliente', function (req, res) {
	console.log('Consultando rodovias...');

	const idCliente = req.params.idCliente;

	console.log('idCliente: ' + idCliente);

	const query = `select * from rodovia where fkCliente = ${idCliente}`;

	banco.sql.connect(banco.config, function (err) {
		if (err) console.log(err);

		console.log('conectado com o banco!');

		var request = new banco.sql.Request();
		request.query(query, function (err, result) {
			console.log('Request sendo feito!');
			if (err) {
				console.log(err);
			}
			else {
				console.log('Enviando resultado para a página');
				res.status(201).send(result.recordset);
				banco.sql.close();
			}
		});
	});
});

router.get('/pegarTrechos/:idRodovia', function (req, res) {
	console.log('Consultando trechos...');

	const idRodovia = req.params.idRodovia;

	console.log('idRodovia: ' + idRodovia);

	const query = `select * from ponto where fkRodovia = ${idRodovia}`;
	//select * from ponto where fkRodovia = 1;

	banco.sql.connect(banco.config, function (err) {
		if (err) console.log(err);

		console.log('conectado com o banco!');

		var request = new banco.sql.Request();
		request.query(query, function (err, result) {
			console.log('Request sendo feito!');
			if (err) {
				console.log(err);
			}
			else {
				console.log('Enviando resultado para a página');
				res.status(201).send(result.recordset);
				banco.sql.close();
			}
		});
	});
});

router.post('/pegarDadosBD', function (req, res) {
	console.log('Consultando trechos...');

	const requisitados = req.body;

	console.log('Pontos requisitados: ' + requisitados);

	let condicionais = '';
	let linhas = 12;

	if(requisitados.length>0){
		linhas = 6*requisitados.length;
		condicionais = 'and (';
		for(let i=0; i<requisitados.length; i++){
			pontoFinal = i+1==requisitados.length? ') ' : ' or ';
			condicionais += `fkPonto = ${requisitados[i]} ${pontoFinal} `;
		}
	}

	const query = `select top ${linhas} * from registro, ponto where fkPonto = idPonto ${condicionais} order by momento desc`;

	banco.sql.connect(banco.config, function (err) {
		if (err) console.log(err);

		console.log('conectado com o banco!');

		var request = new banco.sql.Request();
		request.query(query, function (err, result) {
			console.log('Request sendo feito!');
			if (err) {
				console.log(err);
			}
			else {
				console.log('Enviando resultado para a página');
				res.status(201).send(result.recordset);
				banco.sql.close();
			}
		});
	});
});

module.exports = router;
