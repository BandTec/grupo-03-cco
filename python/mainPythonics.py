from Calculo_python.api_monitoramento_modificada.services.mysqlPythonics import Mysql
from Calculo_python.api_monitoramento_modificada.services.dataGenerator import getData
import time
from Calculo_python.api_monitoramento_modificada.services.dataGenerator import intervalo 
#import duckBotMonitor

mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql.connect()
intervalo = 5

def capturaMsgPythonics(serv):
    # while True:
    #duckBotMonitor.TelegramBotMonitor() # executa o bot monitor 
    values = getData() #pega a função defina em "data generator" e atribui a values
    mysql.insert(values, serv) #insere values no mysql
    # time.sleep(intervalo) #repete a cada 5 segundos

    return values

# capturaMsgPythonics()
