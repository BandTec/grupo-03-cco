const express = require('express');
const leiturasController = require('../controllers/LeituraController');
const dataSet = require('../models/valorleitura');
const dataSetUser = require('../models/usuario'); 

const routes = express.Router();

routes.post('/returnCPU',leiturasController.leiturasBdCPU);
routes.post('/returnMem',leiturasController.leiturasBdMem);
routes.post('/returnDisco',leiturasController.leiturasBdDisco);

routes.post('/returnGerente',leiturasController.leituraGerente);
routes.post('/returnServidor',leiturasController.leituraServidor);
routes.post('/returnUsuario',leiturasController.leituraBdUser);

module.exports = routes; 