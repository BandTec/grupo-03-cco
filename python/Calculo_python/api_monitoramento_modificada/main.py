from services.mysqlPythonics import Mysql
from services.dataGenerator import getData
import time
from services.dataGenerator import intervalo 
#import duckBotMonitor

mysql1 = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql1.connect()
intervalo = 5

def capturaMsgPythonics():
    # while True:
    #duckBotMonitor.TelegramBotMonitor() # executa o bot monitor 
    values = getData() #pega a função defina em "data generator" e atribui a values
    # mysql1.insert(values, ) #insere values no mysql
    # time.sleep(intervalo) #repete a cada 5 segundos

    return values

# capturaMsgPythonics()
