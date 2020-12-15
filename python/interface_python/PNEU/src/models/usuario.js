const sequelize = require('sequelize');
const {Model, DataTypes} = require('sequelize');

class usuario extends Model{
    static init(sequelize){
        super.init({
            idLogin: {
                field: 'idUsuario',
                type: DataTypes.INTEGER,
                primaryKey: true,
                autoIncrement: true
            },
            nome: {
                field: 'nome',
                type: DataTypes.STRING,
                allowNull: false
            },
            Cargo: {
                field: 'Cargo',
                type: DataTypes.STRING,
                allowNull: false
            },
            Nick: {
                field: 'Nick',
                type: DataTypes.STRING,
                allowNull: false
            },
            Senha: {
                field: 'Senha',
                type: DataTypes.STRING,
                allowNull: false
            }
        },{
            tableName:'usuario',
            sequelize
        })
    }
}

module.exports = usuario;