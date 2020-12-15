#conexão com o banco de dados e api de avisos do Slack
from Pythohms.services.mysqlPythohms import Mysql
from Pythohms.services.pythohms import CrawlerOpenHardwareMonitor
import time

#dessa classe eu pretendo tirar toda a estrutura de verificação dos valores e deixar só a execução dos scripts
# class MainPythohms:
# limite_clock = 1000
# limite_temp = 50

mensagem_erro = "Houve um erro crítico inesperado, um Log será emitido.\n" #mensagem de erro padrão

#Inserir user, password, host, database
mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')

mysql.connect()
intervalo = 15
cont = 0

# class mainPythohms:
def capturaMsgPythohms(serv):
    # while True:
    # cont += 1
    
    values = CrawlerOpenHardwareMonitor().getInfo() #pega a variavel info definada em pythohms e atribui a values 

    mysql.insert(values, serv) #insere values no mysq
    # print(aviso)
    # time.sleep(intervalo)
    return values