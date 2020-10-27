const dataSet = require('../models/DATASET_COMP');

module.exports = {
    async buenas(req,res){
        console.log('antes');
        const data_Set = await dataSet.findAll();    
        console.log(data_Set);
        return res.json(JSON.stringify(data_Set));
        
    }
};

