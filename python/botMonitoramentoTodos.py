from mainPythonics import capturaMsgPythonics
from mainPythohms import capturaMsgPythohms
from Calculo_python.api_monitoramento_modificada.services.mysqlPythonics import Mysql
from Calculo_python.api_monitoramento_modificada.duckBotMonitor import TelegramBotMonitor
from datetime import datetime, timedelta, timezone
import pandas as pd
import time
serv = input('Digite o ID do servidor que deseja monitorar: ')
mysql = Mysql('PNEU2SEMESTRE','pneu123', 'localhost', 'projeto')
mysql.connect()
nomServidor = mysql.selectServ(serv)

# limites dos componentes
limiteTempCpu = 70
limiteClockCpu = 7000
limitePorcentCpu = 75
limitePorcentMemoria = 80
limiteGbMemoria = 300
limitePorcentDisco = 70
limiteGbDisco = 80

dateInicio = pd.Timestamp.now()
update_id = None
idsChat = set()
while True:
    print("=============================Iniciando=============================")
    
    msg = ""
    print(capturaMsgPythonics(serv))
    print(capturaMsgPythohms(serv))

    valorPythonics = capturaMsgPythonics(serv)
    valorPythohms = capturaMsgPythohms(serv)

    temperaturaCpu = valorPythohms[0]
    clockCpu = valorPythohms[1]
    porcentCpu = valorPythonics[0]
    porcentMemoria = valorPythonics[1]
    gbMemoria = valorPythonics[2]
    porcentDisco = valorPythonics[3]
    gbDisco = valorPythonics[4]


    dateRodando = pd.Timestamp.now()
    

    if temperaturaCpu >= limiteTempCpu or clockCpu >= limiteClockCpu or porcentCpu >= limitePorcentCpu or porcentMemoria >= limitePorcentMemoria or gbMemoria >= limiteGbMemoria or porcentDisco >= limitePorcentDisco or gbDisco >= limiteGbDisco:
        msg = "Listagem de Erros do servidor: " + str(nomServidor[0][0])+ "\n\n"
        if temperaturaCpu >= limiteTempCpu:
            msg += "Temperatura de CPU excedeu seu limite!\n"
        if clockCpu >= limiteClockCpu:
            msg += "Clock de CPU excedeu seu limite!\n"
        if porcentCpu >= limitePorcentCpu:
            msg += "Porcentagem de CPU excedeu seu limite!\n"
        if porcentMemoria >= limitePorcentMemoria:
            msg += "Porcentagem de Memória excedeu seu limite!\n"
        if gbMemoria >= limiteGbMemoria:
            msg += "GB de Memória excedeu seu limite!\n"
        if porcentDisco >= limitePorcentDisco:
            msg += "Porcentagem de Disco excedeu seu limite!\n"
        if gbDisco >= limiteGbDisco:
            msg += "GB de Disco excedeu seu limite!\n"

    diferenca = dateRodando - dateInicio

    diferenca = str(diferenca).split(".")
    tempoDividido = diferenca[0].split(":")

    

    # print('DIFERENÇA DE SEGUNDO PORAR ' + str(tempoDividido[1]))


    if (tempoDividido[1] == "30" or tempoDividido[1] == "00") and tempoDividido[2] <= "16":
        msg += '\n\nO servidor ' + str(nomServidor[0][0]) + ' está sendo monitorado a ' + str(diferenca[0].replace("days", "dia(s)"))

   
    
    

    
    print("=============================Telegram=============================")
    
    TelegramBotMonitor().Iniciar(msg,update_id)
    
    
    print("=============================Soninho=============================")
    time.sleep(10)
