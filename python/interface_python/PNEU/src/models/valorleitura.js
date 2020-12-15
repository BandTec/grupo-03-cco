const sequelize = require('sequelize');
const {Model, DataTypes} = require('sequelize');

class valorleitura extends Model{
    static init(sequelize){
        super.init({
            idValorLeitura: {
                field: 'idValorLeitura',
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            valor: {
                field: 'valor',
                type: DataTypes.INTEGER,
                allowNull: false
            },
            dataTempo: {
                field: 'dataTempo',
                type: DataTypes.STRING,
                allowNull: false
            },
            fkComponente: {
                field: 'fkComponente',
                type: DataTypes.INTEGER,
                allowNull: false
            }
        },{
            tableName:'valorleitura',
            sequelize
        })
    }
    
}

module.exports = valorleitura;