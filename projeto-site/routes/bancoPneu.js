var configuracoes = {
    banco: {
        server: "serverpneu.database.windows.net",
        user: "adminlocal",
        password: "grupo3ehD+",
        database: "bdProjPneu",
        options: {
            encrypt: true
        }
    }
}
 
var sql = require('mssql');

function conectar() {
  sql.close();
  return sql.connect(configuracoes.banco)
} 

module.exports = {
    conectar: conectar,
    sql: sql,
    config: configuracoes.banco
}
