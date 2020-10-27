const { Sequelize, DataTypes } = require("sequelize");

module.exports = (sequelize, DataTypes) => {
    let TEMP_CLOCK = sequelize.define('TEMP_CLOCK',{
        id: {
            field: 'id',
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },

        temperatura: {
            field: 'temperatura',
            type: DataTypes.STRING,
            allowNull: false
        },

        clock: {
            field: 'clock',
            type: DataTypes.STRING,
            allowNull: false
        }
    },
    { 
        tableName: 'TEMP_CLOCK',
        freezeTableName: true,
        underscored : true,
        timestamps: false
    });  

    return TEMP_CLOCK;
};
