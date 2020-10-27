const express = require("express");
const router = express.Router();
const sequelize = require("../models").sequelize;
const Usuario = require("../models").Usuario;
const Cliente = require("../models").Cliente;

let sessoes = [];

/* Recuperar usuário por login e senha */
router.post("/autenticar", function(req, res, next) {
  console.log("Recuperando usuário por login e senha");

  var login = req.body.login; // depois de .body, use o nome (name) do campo em seu formulário de login
  var senha = req.body.senha; // depois de .body, use o nome (name) do campo em seu formulário de login

  let instrucaoSql = `select * from Usuario where nickUsuario='${login}' and senha='${senha}'`;
  console.log(`Instrução -> ${instrucaoSql}`);

  sequelize
    .query(instrucaoSql, {
      model: Usuario
    })
    .then(resultado => {
      console.log(`Encontrados: ${resultado.length}`);
      if (resultado.length == 1) {
        sessoes.push(resultado[0].idLogin);
        console.log("sessoes: ", sessoes);
        res.json(resultado[0]);
      } else if (resultado.length == 0) {
        res.status(403).send("Login e/ou senha inválido(s)");
      } else {
        res.status(403).send("Mais de um usuário com o mesmo login e senha!");
      }
    })
    .catch(erro => {
      console.error(erro);
      res.status(500).send(erro.message);
    });
});

// de cadastro de usuario e cliente
router.post("/", function(req, res, next) {
  console.log(res.body);
  Cliente.create({
    nomeCliente: req.body.nomeCliente,
    cnpj: req.body.cnpj
  })
    .then(resultado => {
      console.log(`Cliente criado: ${resultado}`);

      console.log("Criando o usuário");

      Usuario.create({
        nome: req.body.nomeUsuario,
        cargo: req.body.cargoUsuario,
        login: req.body.nickUsuario,
        senha: req.body.senha,
        fkCliente: resultado.id
      })
        .then(result => {
          console.log(`Registro criado: ${result}`);
        })
        .catch(erro => {
          console.error(erro);
          res.status(500).send(erro.message);
        });
    })
    .catch(erro => {
      console.error(erro);
      res.status(500).send(erro.message);
    });
});

//buscar dados de um usuario
router.get("/buscar/:id/:fk", function(req, res, next) {
  let fk= parseInt(req.params.fk);
  const instrucaoSql = `select nomeCliente, CNPJ from CLiente where idCliente = ${fk}`;
	sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
		.then(resultado => {
		  return	res.json(resultado);
		}).catch(erro => {
			console.error(erro);
	  	return	res.status(500).send(erro.message);
		});
});

/* Verificação de usuário */
router.get("/sessao/:login", function(req, res, next) {
  let login = req.params.login;
  console.log(`Verificando se o usuário ${login} tem sessão`);

  let tem_sessao = false;
  for (let u = 0; u < sessoes.length; u++) {
    if (sessoes[u] == login) {
      tem_sessao = true;
      break;
    }
  }

  if (tem_sessao) {
    let mensagem = `Usuário ${login} possui sessão ativa!`;
    console.log(mensagem);
    res.send(mensagem);
  } else {
    res.sendStatus(403);
  }
});

/* Logoff de usuário */
router.get("/sair/:login", function(req, res, next) {
  let login = req.params.login;
  console.log(`Finalizando a sessão do usuário ${login}`);
  let nova_sessoes = [];
  for (let u = 0; u < sessoes.length; u++) {
    if (sessoes[u] != login) {
      nova_sessoes.push(sessoes[u]);
    }
  }
  sessoes = nova_sessoes;
  res.send(`Sessão do usuário ${login} finalizada com sucesso!`);
});

/* Recuperar todos os usuários */
router.get("/", function(req, res, next) {
  console.log("Recuperando todos os usuários");
  Usuario.findAndCountAll()
    .then(resultado => {
      console.log(`${resultado.count} registros`);

      res.json(resultado.rows);
    })
    .catch(erro => {
      console.error(erro);
      res.status(500).send(erro.message);
    });
});

module.exports = router;
