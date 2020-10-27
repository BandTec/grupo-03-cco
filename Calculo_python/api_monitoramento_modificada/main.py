#conexão com o banco de dados e api de avisos do Slack
from services.mysql import Mysql
from services.dataGenerator import getData
import time
from services.dataGenerator import intervalo 
import avisosSlack

#Inserir user, password, host, database
mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql.connect()

while True:
    values = getData() #pega a função defina em "data generator" e atribui a values
    mysql.insert(values) #insere values no mysql
    Slack = avisosSlack.alertarSlack() 
    print(Slack) # printa avisos no cmd
    time.sleep(intervalo) #repete a cada 2 segundos
    