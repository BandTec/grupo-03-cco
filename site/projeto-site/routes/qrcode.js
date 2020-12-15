const express = require('express');
const router = express.Router();
var Codes = require('../models').Codes;
const sequelize = require("../models").sequelize;

router.get('/returnCode',function(req, res, next) {
    const instrucaoSql = `select * from codes`;
    sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
    .then(resultado => {
      if(resultado.length == 0){
        console.log("Banco vazio");
        return res.json([]);
      }else{
        console.log("Retornando código: " + JSON.stringify(resultado[0]));
        return res.json(resultado);
      }
      }).catch(erro => {
        console.error(erro);
        return	res.status(500).send(erro.message);
      });
});
router.post('/storeCode',function(req, res, next) {
  const code = req.body.cod;
  const instrucaoSql = `DELETE FROM codes WHERE id >= 1; TRUNCATE TABLE codes; insert into codes values ('${code}');`;
  sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.DELETE })
  .then(resultado => {
      console.log(resultado);
    }).catch(erro => {
      console.error(erro);
      return	res.status(500).send(erro.message);
    });
});

module.exports = router;