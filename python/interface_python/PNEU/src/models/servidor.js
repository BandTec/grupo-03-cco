const sequelize = require('sequelize');
const {Model, DataTypes} = require('sequelize');

class servidor extends Model{
    static init(sequelize){
        super.init({
            idServidor: {
                field: 'idServidor',
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            StatusOnline: {
                field: 'StatusOnline',
                type: DataTypes.STRING,
                allowNull: false
            },
            Nome: {
                field: 'nomeServidor',
                type: DataTypes.STRING,
                allowNull: false
            },
            fkLocalizacao: {
                field: 'fkLocalizacao',
                type: DataTypes.INTEGER,
                allowNull: false
            },
        },{
            tableName:'servidor',
            sequelize
        })
    }
}

module.exports = servidor;