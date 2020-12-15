'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Rodovia = sequelize.define('Rodovia',{
		id: {
			field: 'idRodovia',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},		
		nome: {
			field: 'nomeRodovia',
			type: DataTypes.STRING,
			allowNull: false
		},
		fkCliente: {
			field: 'fkCliente',
			type: DataTypes.STRING,
			allowNull: false,
			references: {
				model: 'Cliente',
				key: 'id'
			  }
		}
	}, 
	{
		tableName: 'Rodovia', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Rodovia;
};
