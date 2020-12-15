'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/
const cliente = require('./cliente');
module.exports = (sequelize, DataTypes) => {
    let Usuario = sequelize.define('Usuario',{
		id: {
			field: 'idLogin',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'nomeUsuario',
			type: DataTypes.STRING,
			allowNull: false
		},
		login: {
			field: 'nickUsuario',
			type: DataTypes.STRING,
			allowNull: false
		},
		senha: {
			field: 'senha',
			type: DataTypes.STRING,
			allowNull: false
		},
		cargo: {
			field: 'CargoUsuario',
			type: DataTypes.STRING,
			allowNull: false
		},
		fkCliente:{
			field:'fkCliente',
			type: DataTypes.INTEGER,
			allowNull: false,
			references:{
				model: cliente,
				key:'id'
			}

		}
	}, 
	{
		tableName: 'Usuario', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Usuario;
};
