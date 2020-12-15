'use strict';

/* 
lista e explicação dos Datatypes:
https://codewithhugo.com/sequelize-data-types-a-practical-guide/
*/

module.exports = (sequelize, DataTypes) => {
    let Leitura = sequelize.define('Leitura',{	
		id: {
			field: 'idRegistro',
			type: DataTypes.INTEGER,
			primaryKey: true,
			autoIncrement: true
		},	
		qtdCarros: {
			field: 'qtdCarros',
			type: DataTypes.REAL,
			allowNull: false
		},
		fkPonto: {
			field: 'fkPonto',
			type: DataTypes.REAL,
			allowNull: false
		},
		momento: {
			field: 'horaRegistro',
			type: DataTypes.DATE, // NÃO existe DATETIME. O tipo DATE aqui já tem data e hora
			allowNull: false
		},
		momento_grafico: {
			field: 'horaRegistro',
			type: DataTypes.VIRTUAL, // campo 'falso' (não existe na tabela). Deverá ser preenchido 'manualmente' no select
			allowNull: true
		}
	}, 
	{
		tableName: 'registro', 
		freezeTableName: true, 
		underscored: true,
		timestamps: false,
	});

    return Leitura;
};
