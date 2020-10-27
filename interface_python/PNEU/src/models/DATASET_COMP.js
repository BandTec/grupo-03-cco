const { Sequelize, DataTypes } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
    let DATASET_COMP = sequelize.define('DATASET_COMP',{
        id: {
            field: 'id',
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        cpu : {
            field: 'cpu',
            type : DataTypes.STRING,
            allowNull: false
        },
        ram_total: {
            field: 'ram_total',
            type: DataTypes.STRING,
            allowNull: false
        },
        ram : {
            field: 'ram',
            type: DataTypes.STRING,
            allowNull: false
        },
        ram_percent : {
            field: 'ram_percent',
            type: DataTypes.STRING,
            allowNull: false
        },
        disk: {
            field: 'disk',
            type: DataTypes.String,
            allowNull : false
        },
        disk_percent: {
            field: 'disk_percent',
            type: DataTypes.STRING,
            allowNull: false
        }
    },
        { 
            tableName: 'DATASET_COMP',
            freezeTableName: true,
            underscored : true,
            timestamps: false
        });

    return DATASET_COMP;
};