'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Cliente = sequelize.define('Cliente',{
		id: {
			field: 'idCliente',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nomeCliente: {
			field: 'nomeCliente',
			type: DataTypes.STRING,
			allowNull: false
		},
		cnpj: {
			field: 'CNPJ',
			type: DataTypes.STRING,
			allowNull: false
		}
	}, 
	{
		tableName: 'Cliente', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Cliente;
};
