const sequelize = require('sequelize');
const {Model, DataTypes} = require('sequelize');

class componente extends Model{
    static init(sequelize){
        super.init({
            idComponente: {
                field: 'idComponente',
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            fkTipoComponente: {
                field: 'fkTipoComponente',
                type: DataTypes.STRING,
                allowNull: false
            },
            fkServidor: {
                field: 'fkServidor',
                type: DataTypes.STRING,
                allowNull: false
            },
        },{
            tableName:'componente',
            sequelize
        })
    }
}

module.exports = componente;