import mysql.connector

class Mysql:
    def __init__(self, user, password, host, database):
        self.user = user
        self.password = password
        self.host = host
        self.database = database
        self.mysql = None
        self.cursor = None


    #Estabelecendo uma conexão 
    def connect(self):
        try:
            self.mysql = mysql.connector.connect(
            user=self.user, password=self.password, host=self.host, database=self.database,auth_plugin = 'mysql_native_password')
            #Criando cursor para manipulação do banco.
            print(self.mysql)
            self.cursor = self.mysql.cursor()
        except Exception as err:
            print(err)
            raise
    
    def select(self, serv):
        cursor = self.cursor
        cursor.execute("SELECT idcomponente,fktipocomponente FROM servidor,componente WHERE idservidor = fkservidor AND idservidor = " + str(serv))
        return cursor.fetchall()

    def selectServ(self, serv):
        cursor = self.cursor
        cursor.execute("SELECT nomeServidor FROM servidor WHERE idservidor = " + str(serv))
        return cursor.fetchall()

    def insert(self, data, serv):
        resultado = self.select(serv)
        vetorId = []

        for x in resultado:
            if x[1] == 1 or x[1] == 4 or x[1] == 5 or x[1] == 6 or x[1] == 7:
                vetorId.append(x)
                
        for x in vetorId:
            if x[1] == 1:
                values = [data[6],x[0],data[0]]
            elif x[1] == 4:
                values = [data[6],x[0],data[3]]
            elif x[1] == 5:
                values = [data[6],x[0],data[2]]
            elif x[1] == 6:
                values = [data[6],x[0],data[4]]
            elif x[1] == 7:
                values = [data[6],x[0],data[5]]

            query = (
                "INSERT INTO `valorLeitura`(dataTempo, fkComponente, valor)"
                "VALUES (%s,%s,%s)"                
            )
            try:
                print('Inserindo Valores')
                self.cursor.execute(query,values)
                self.mysql.commit()
                
            except Exception as err:
                print(err)
                self.mysql.rollback()
                self.close()

    # Fechando conexão
    def close(self):
        self.mysql.close()