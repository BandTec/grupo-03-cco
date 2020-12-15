import psutil, time, sys
import requests
import json
import time
import os
# from services.dataGenerator import getData
# from services.dataGenerator import intervalo 
# from services.dataGenerator import getData,intervalo 

idChats = set()
class TelegramBotMonitor:
    def __init__(self):
        token = '1409242209:AAH9XvWYNb6IvbReO5ffRuJkprcbL2uUwJ0'
        self.url_base = f'https://api.telegram.org/bot{token}/'

    def Iniciar(self, resposta,update_id):
        global idChats
        print("=============================Dados=============================")
        dados = TelegramBotMonitor().atualizarDados(update_id)
        if dados:
            for dado in dados:
                idChat = dado["message"]["from"]["id"]
                print("idChat: " + str(idChat))
                idChats.add(idChat)
            print(idChats)
        
        if idChats:
            for idChat in idChats:
                print("Igual: " + str(idChat))
                self.responder(resposta, idChat)

    # Obter mensagens
    def obter_novas_mensagens(self, update_id):
        print('update_id innicio da func 1: ' + str(update_id))
        link_requisicao = f'{self.url_base}getUpdates?timeout=10'
        if update_id:
            print('update_id innicio da func 2: ' + str(update_id))
            link_requisicao = f'{link_requisicao}&offset={update_id + 1}'
            print('update_id innicio da func 3: ' + str(update_id))
        resultado = requests.get(link_requisicao)
        print('update_id innicio da func 4: ' + str(update_id))
        return json.loads(resultado.content)

    # Responder
    def responder(self, resposta, chat_id):
        link_requisicao = f'{self.url_base}sendMessage?chat_id={chat_id}&text={resposta}'
        requests.get(link_requisicao)

    def atualizarDados(self,update_id):
        atualizacao = self.obter_novas_mensagens(update_id)
        print(atualizacao)
        return atualizacao["result"]

botMonitor = TelegramBotMonitor()


























# def criar_resposta(self, resposta):
#         global contador_log
#         global temporizador
#         global cont
#         #incrementadores
#         contador_log += 1 
#         temporizador = (contador_log * intervalo) * 5 #cálculo para saber há quantos segundos o sistema está rodando
#         mensagem_tempo = 'O sistema está rodando há ' + str(temporizador) + 'segs..' #variável que guarda a mensagem que mostra quanto tempo o sistema ficou no ar
#         while True:
#             cont += 1
#             if (getData()[0] >= maximo_cpu) or (getData()[3] >= maximo_memoria_porcentagem) or (getData()[5] >= maximo_disco_porcentagem):
#                 aviso = ''
#                 aviso += '------------------------------------------------------------------------------'
                
#                 if getData()[0] >= maximo_cpu :
#                     mensagem_erro_cpu = "\n O uso de CPU atingiu o limite de " + str(maximo_cpu) + "% de uso. "
#                     aviso += mensagem_erro_cpu
#                 if getData()[3] >= maximo_memoria_porcentagem :
#                     mensagem_erro_memoria_percent = "\n" + mensagem_erro_memoria_padrao + str(maximo_memoria_porcentagem) + "% de uso. "
#                     aviso += mensagem_erro_memoria_percent
#                 if getData()[5] >= maximo_disco_porcentagem :
#                     mensagem_erro_disco_percent = "\n" +mensagem_erro_disco_padrao + str(maximo_disco_porcentagem) + "% de uso. "
#                     aviso += mensagem_erro_disco_percent

#                 mensagem = "\n" + str(aviso) + "\n" + str(mensagem_tempo) + "! " + str(cont) + "º mensagem"
#                 return mensagem
#             else:
#                 if cont % 10 == 0:
#                     return "Servidor OK!" + "\n" + "O sistema está rodando à " + str(cont) + " segundos!"