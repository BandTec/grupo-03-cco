#conexão com el banco de dados

from services.mysql import Mysql
from services.dataGenerator import getData
import time
from services.dataGenerator import intervalo 


#Inserir user, password, host, database
mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql.connect()

while True:
    values = getData() #pega a função defina em "data generator" e atribui a values
    mysql.insert(values) #insere values no mysql
    time.sleep(intervalo) #repete a cada 2 segundos
