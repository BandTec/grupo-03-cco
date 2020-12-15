const sequelize = require('sequelize');
const {Model, DataTypes} = require('sequelize');

class localizacao extends Model{
    static init(sequelize){
        super.init({
            idLocalizacao: {
                field: 'idLocalizacao',
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            estado: {
                field: 'estado',
                type: DataTypes.STRING,
                allowNull: false
            },
            cidade: {
                field: 'cidade',
                type: DataTypes.STRING,
                allowNull: false
            },
            bairro: {
                field: 'bairro',
                type: DataTypes.STRING,
                allowNull: false
            },
            rua: {
                field: 'rua',
                type: DataTypes.STRING,
                allowNull: false
            },
            numero: {
                field: 'numero',
                type: DataTypes.STRING,
                allowNull: false
            },
        },{
            tableName:'localizacao',
            sequelize
        })
    }
}

module.exports = localizacao;