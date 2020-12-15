const dataSet = require('../models/valorleitura');
const dataSetUser = require('../models/usuario');
const dataSetServer = require('../models/servidor');
const dataSetComponente = require('../models/componente');
const dataSetLocalizacao = require('../models/localizacao');

module.exports = {
  async leiturasBdCPU(req, res) {
    var idServidor = req.body.idServidor

    const componenteCpuUso = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 1
      }
    })

    const componenteCpuClock = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 2
      }
    })

    const componenteCpuTemp = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 3
      }
    })

    var cpuUso = ""
    var cpuClock = ""
    var cpuTemp = ""

    if(JSON.stringify(componenteCpuUso[0]) != undefined){
        var fkCpuUso = JSON.stringify(componenteCpuUso[0].dataValues.idComponente);
        cpuUso = await dataSet.findAll({
          where: {
            fkComponente: fkCpuUso
          },
          attributes: ['valor', 'dataTempo'],
          limit: 1,
          order: [
            [
              "idValorLeitura", "DESC"
            ]
          ]
        })
    }else{
      cpuUso = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    if(JSON.stringify(componenteCpuClock[0]) != undefined){
      var fkCpuClock = JSON.stringify(componenteCpuClock[0].dataValues.idComponente);
      cpuClock = await dataSet.findAll({
        where: {
          fkComponente: fkCpuClock
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      cpuClock = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    if(JSON.stringify(componenteCpuTemp[0]) != undefined){
      var fkCpuTemp = JSON.stringify(componenteCpuTemp[0].dataValues.idComponente);
      cpuTemp = await dataSet.findAll({
        where: {
          fkComponente: fkCpuTemp
        },
        attributes: ['valor', 'dataTempo'],
  
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      cpuTemp = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    var resposta = [cpuUso, cpuClock, cpuTemp]
    if (cpuUso.length == 0 && cpuClock.length == 0 && cpuTemp.length == 0) {
      console.log("Banco vazio");
      return res.json([]);
    } else {
      console.log("Retornando valores da CPU: " + JSON.stringify(resposta));
      return res.json(resposta);
    }
  },

  async leiturasBdMem(req, res) {
    var idServidor = req.body.idServidor
    
    const componenteMemPerc = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 4
      }
    })

    const componenteMemUso = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 5
      }
    })

    var memGb = ""
    var memPercent = ""

    if(JSON.stringify(componenteMemPerc[0]) != undefined){
      var fkMemPerc = JSON.stringify(componenteMemPerc[0].dataValues.idComponente);
      memPercent = await dataSet.findAll({
        where: {
          fkComponente: fkMemPerc
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      memPercent = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    if(JSON.stringify(componenteMemUso[0]) != undefined){
      var fkMemUso = JSON.stringify(componenteMemUso[0].dataValues.idComponente);
      memGb = await dataSet.findAll({
        where: {
          fkComponente: fkMemUso
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      memGb = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    var resposta = [memPercent,memGb]
    if (memPercent.length == 0 && memGb.length == 0) {
      console.log("Banco vazio");
      return res.json([]);
    } else {
      console.log("Retornando valores da Memória: " + JSON.stringify(resposta));
      return res.json(resposta);
    }
  },

  async leiturasBdDisco(req, res) {
    var idServidor = req.body.idServidor
    
    const componenteDisPerc = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 6
      }
    })

    const componenteDisUso = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 7
      }
    })

    var discoGb = ""
    var discoPercent = ""

    if(JSON.stringify(componenteDisPerc[0]) != undefined){
      var fkDisPerc = JSON.stringify(componenteDisPerc[0].dataValues.idComponente);
      discoGb = await dataSet.findAll({
        where: {
          fkComponente: fkDisPerc
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      discoGb = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    if(JSON.stringify(componenteDisUso[0]) != undefined){
      var fkDisUso = JSON.stringify(componenteDisUso[0].dataValues.idComponente);
      discoPercent = await dataSet.findAll({
        where: {
          fkComponente: fkDisUso
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      discoPercent = [{valor: "0.0",dataTempo: "0:00:00"}]
    }

    var resposta = [discoPercent, discoGb]
    if (discoGb.length == 0 && discoPercent.length == 0) {
      console.log("Banco vazio");
      return res.json([]);
    } else {
      console.log("Retornando valores do Disco: " + JSON.stringify(resposta));
      return res.json(resposta);
    }
  },

  async leituraBdUser(req, res) { 
    const login = req.body.login;
    const senha = req.body.senha;

    const selectUser = await dataSetUser.findAll({
      where: {
        Nick: [`${login}`],
        Senha: [`${senha}`]
      }
    })

    var resposta = [selectUser]
    if (selectUser.length == 1) {
      console.log("Retornando valores do login : " + JSON.stringify(resposta));
      res.json(resposta[0]);
    } else if (selectUser.length == 0) {
      res.status(403).send('Login e/ou senha inválido(s)');
    } else {
      res.status(403).send('Mais de um usuário com o mesmo login e senha!');
    }

  },

  async leituraGerente(req,res){
    const idServidor = req.body.idServidor;

    const componenteCpu = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 1
      }
    })

    const componenteMem = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 4
      }
    })

    const componenteDisc = await dataSetComponente.findAll({
      where:{
        fkServidor: idServidor,
        fkTipoComponente: 7
      }
    })

    var cpuUso = ""
    var memPercent = ""
    var discoPercent = ""
    
    if(JSON.stringify(componenteCpu[0])  != undefined){
      var cpu = JSON.stringify(componenteCpu[0].dataValues.idComponente);
      console.log("======================");
      cpuUso = await dataSet.findAll({
        where: {
          fkComponente: cpu
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }

    if(JSON.stringify(componenteMem[0]) != undefined){
      var mem = JSON.stringify(componenteMem[0].dataValues.idComponente);
      console.log("======================");
      memPercent = await dataSet.findAll({
      where: {
        fkComponente: mem
      },
      attributes: ['valor', 'dataTempo'],
      limit: 1,
      order: [
        [
          "idValorLeitura", "DESC"
        ]
      ]
    })
    }

    if(JSON.stringify(componenteDisc[0]) != undefined){
      var disc = JSON.stringify(componenteDisc[0].dataValues.idComponente);
      console.log("======================");
      discoPercent = await dataSet.findAll({
        where: {
          fkComponente: disc
        },
        attributes: ['valor', 'dataTempo'],
        limit: 1,
        order: [
          [
            "idValorLeitura", "DESC"
          ]
        ]
      })
    }else{
      discoPercent = []
    }
    
    console.log("======================");

    var resposta = [cpuUso, memPercent, discoPercent]
    
    if (cpuUso.length == 0 && memPercent.length == 0 && discoPercent.length == 0) {
      console.log("Banco vazio");
      return res.json([]);
    } else {
      console.log("Retornando valores da CPU: " + JSON.stringify(resposta));
      return res.json(resposta);
    }
  },

  async leituraServidor(req,res){
    const qntServidor = req.body.qntServidor;
    const estado = req.body.estado;
    console.log("Estado: " + estado);
    console.log("Limite de servidores: " + qntServidor);

    var servidores = "";

    if(qntServidor == 0 && estado == "Todos"){
      servidores = await dataSetServer.findAll()
    }else{
      if(estado == "Todos"){
        servidores = await dataSetServer.findAll({
          limit: qntServidor,
        })
      }else{
        var localizacao = await dataSetLocalizacao.findAll({
          where: {
            estado
          }
        })
        if(localizacao.length == 0){
          console.log("Nenhuma localização encontrada");
        }else{
          var fkLocalizacao =  JSON.stringify(localizacao[0].dataValues.idLocalizacao)
          if(qntServidor == 0){
            servidores = await dataSetServer.findAll({
              where: {
                fkLocalizacao
              }
            })
          }else{
            servidores = await dataSetServer.findAll({
              limit: qntServidor,
              where: {
                fkLocalizacao
              }
            })
          }
        }
      }
      
    }
   
    if(servidores.length == 0){
      console.log("Banco vazio");
      return res.json([])
    } else {
      console.log("Retornando lista de servidores: " + JSON.stringify(servidores));
      return res.json(servidores);
    }
  }

};
