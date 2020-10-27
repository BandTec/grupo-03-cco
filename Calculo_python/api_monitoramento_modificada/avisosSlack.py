import psutil, time, sys
import requests
from services.dataGenerator import getData,intervalo 

#variáveis de contagem
contador_log = 0 #seta o contador de Logs em 0
temporizador = 0#seta o temporizador em 0

#variáveis de teto de uso 
maximo_cpu = 90 #define a porcentagem máxima de CPU que pode ser utilizada
maximo_memoria_porcentagem = 90 #define o máximo de memória que o sistema pode utilizar em porcentagem
maximo_disco_porcentagem = 80 #define o máximo de disco que o sistema pode utilizar em porcentagem

#mensagens de erro padrão
mensagem_erro = "Houve um erro crítico inesperado, um Log será emitido.\n" #mensagem de erro padrão
mensagem_erro_memoria_padrao = "O uso de memória atingiu o limite de " #mensagem de erro padrão de memória
mensagem_erro_disco_padrao = "O uso de disco atingiu o limite de "  #mensagem de erro padrão de disco

def alertarSlack():
    #tornando as variaveis globais para poderem ser modificadas dentro da função
    global contador_log 
    global temporizador

    #incrementadores
    contador_log += 1 
    temporizador = (contador_log * intervalo) * 5 #cálculo para saber há quantos segundos o sistema está rodando

    mensagem_tempo = 'O sistema está rodando há ' + str(temporizador) + 'segs..' #variável que guarda a mensagem que mostra quanto tempo o sistema ficou no ar

    url = 'https://hooks.slack.com/services/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' # canal do Slack

    if (getData()[0] >= maximo_cpu) or (getData()[3] >= maximo_memoria_porcentagem) or (getData()[5] >= maximo_disco_porcentagem):
        aviso = ''
        aviso += '------------------------------------------------------------------------------'
        aviso += "\n" + mensagem_erro + "Foram realizadas " + str(contador_log) + " inserções no banco de dados. \n"
        
        if getData()[0] >= maximo_cpu :
            mensagem_erro_cpu = "\n O uso de CPU atingiu o limite de " + str(maximo_cpu) + "% de uso. "
            aviso += mensagem_erro_cpu
        if getData()[3] >= maximo_memoria_porcentagem :
            mensagem_erro_memoria_percent = "\n" + mensagem_erro_memoria_padrao + str(maximo_memoria_porcentagem) + "% de uso. "
            aviso += mensagem_erro_memoria_percent
        if getData()[5] >= maximo_disco_porcentagem :
            mensagem_erro_disco_percent = "\n" +mensagem_erro_disco_padrao + str(maximo_disco_porcentagem) + "% de uso. "
            aviso += mensagem_erro_disco_percent

        aviso = aviso[0:-2] + '.'
        pload = {'text': aviso}
        requests.post(url, json = pload)
        return aviso
    else:
        return "Servidor OK!"

    if __name__ == '__main__':
        while True:
            valores = dadosHardware()
            aviso = alertarSlack(valores)
            print(aviso)
