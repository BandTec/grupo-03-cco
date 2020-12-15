// const sequelize = require('sequelize');
// const {Model, DataTypes} = require('sequelize');

// class Code extends Model{
//     static init(sequelize){
//         super.init({
//             code: DataTypes.STRING(5)
//         },{
//             sequelize
//         })
//     }
// }

'use strict';

module.exports = (sequelize, DataTypes) => {
    let Codes = sequelize.define('Codes',{
		id: {
			field: 'id',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		code: {
			field: 'code',
			type: DataTypes.STRING(5),
			allowNull: false
		},
	}, 
	{
		tableName: 'codes', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Codes;
};


// module.exports = Code;