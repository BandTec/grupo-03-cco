#conexão com o banco de dados e api de avisos do Slack
from services.mysqlPythohms import Mysql
from services.pythohms import CrawlerOpenHardwareMonitor
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

def capturaMsgPythohms(serv):
    while True:
        # cont += 1

        values = CrawlerOpenHardwareMonitor().getInfo() #pega a variavel info definada em pythohms e atribui a values 

        # mediaTemp = values[0]
        # mediaClock = values[1]
        # mediaData = values[2]

        

        # if (mediaTemp > limite_temp or mediaClock > limite_clock):
        #     aviso = ''
        #     aviso += '-------------------------------' + str(mediaData) + '-----------------------------------------'
        #     aviso += "\n" + mensagem_erro + "Foram realizadas " + str(cont*5) + " inserções no banco de dados. \n"
            
        #     if mediaTemp > limite_temp:
        #         mensagemErro = "\nA temperatura chegou no limite de " + str(limite_temp)
        #         aviso += mensagemErro
        #     if mediaClock > limite_clock:
        #         mensagemErro = "\nO clock chegou no limite de " + str(limite_clock)
        #         aviso += mensagemErro
        # else:
        #     aviso = 'Servidor OK ' + str(mediaData)


        mysql.insert(values, serv) #insere values no mysq
        # print(aviso)
        # time.sleep(intervalo)