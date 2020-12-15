import psutil, time, sys
import requests
import json
import os
from services.dataGenerator import getData,intervalo 

contador_log = 0
temporizador = (contador_log * intervalo) * 5 #cálculo para saber há quantos segundos o sistema está rodando

#variáveis de teto de uso 
maximo_cpu = 50 #define a porcentagem máxima de CPU que pode ser utilizada
maximo_memoria_porcentagem = 50 #define o máximo de memória que o sistema pode utilizar em porcentagem
maximo_disco_porcentagem = 50 #define o máximo de disco que o sistema pode utilizar em porcentagem

#mensagens de erro padrão
mensagem_erro = "Houve um erro crítico inesperado, um Log será emitido.\n" #mensagem de erro padrão
mensagem_erro_memoria_padrao = "O uso de memória atingiu o limite de " #mensagem de erro padrão de memória
mensagem_erro_disco_padrao = "O uso de disco atingiu o limite de "  #mensagem de erro padrão de disco

class TelegramBot:
    def __init__(self):
        token = '1417386777:AAG4DfHX5A1uIPVxelLX8HvgKQ47wPtR17o'
        self.url_base = f'https://api.telegram.org/bot{token}/'

    def Iniciar(self):
        update_id = None
        while True:
            atualizacao = self.obter_novas_mensagens(update_id)
            dados = atualizacao["result"]
            if dados:
                for dado in dados:
                    update_id = dado['update_id']
                    mensagem = str(dado["message"]["text"])
                    chat_id = dado["message"]["from"]["id"]
                    eh_primeira_mensagem = int(dado["message"]["message_id"]) == 1
                    resposta = self.criar_resposta(mensagem, eh_primeira_mensagem)
                    self.responder(resposta, chat_id)            
                    self.monitorar(resposta, chat_id)

    # Obter mensagens
    def obter_novas_mensagens(self, update_id):
        link_requisicao = f'{self.url_base}getUpdates?timeout=100'
        if update_id:
            link_requisicao = f'{link_requisicao}&offset={update_id + 1}'
        resultado = requests.get(link_requisicao)
        return json.loads(resultado.content)

    # Criar uma resposta
    def criar_resposta(self, mensagem, eh_primeira_mensagem):
        global contador_log
        global temporizador
        print('mensagem escrita')
        
        # se a mensagem que eu mandei, for a primeira mensagem, ou for igual a "conversar" ou "monitorar", a condição será satisfeita
        if eh_primeira_mensagem == True or mensagem in ('duck', 'Duck'): 
          return f'''Olá, eu sou DuckBot. Quack!{os.linesep}Você deseja monitorar o sistema, ou conversar comigo?{os.linesep}(conversar/ monitorar))'''

        if mensagem == 'conversar':
          return f'''Olá, digite o código referente a natureza do erro:{os.linesep}1 - Erro de conexão{os.linesep}2 - Erro de inicialização{os.linesep}3 - Perca de pacotes{os.linesep}4 - PC não quer funcionar'''

        if mensagem == '1':
            return f'''É meu querido ta dando erro de conexão ai, tenta conectar o wifi na tomada.{os.linesep}(encerrar/voltar)
            '''
        elif mensagem == '2':
            return f'''Putz, tenta clicar em algum executável ai.{os.linesep}(encerrar/voltar)
            '''
        elif mensagem == '3':
            return f'''Espera um pouco{os.linesep}(encerrar/voltar)
            '''
        elif mensagem == '4':
            return f'''Tenta ligar o PC na tomada{os.linesep}(encerrar/voltar)
            '''

        elif mensagem == 'monitorar':
            while True:
                #incrementadores
                contador_log += 1 
                temporizador = (contador_log * intervalo) * 5 #cálculo para saber há quantos segundos o sistema está rodando
                mensagem_tempo = 'O sistema está rodando há ' + str(temporizador) + 'segs..' #variável que guarda a mensagem que mostra quanto tempo o sistema ficou no ar

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

                    mensagem = "\n" + str(aviso) + "\n" + str(mensagem_tempo) + "!"
                    return mensagem
                else:
                    return "Servidor OK!" + "\n" + str(mensagem_tempo) + "!"
            
        elif mensagem.lower() in ('encerrar', 'e'):
            return ''' Volte sempre! :) '''
        elif mensagem.lower() in ('voltar', 'v'):
            return f'''Olá, digite o código referente a natureza do erro:{os.linesep}1 - Erro de conexão{os.linesep}2 - Erro de inicialização{os.linesep}3 - Perca de pacotes{os.linesep}4 - PC não quer funcionar'''        
        
        else:
            return f'''Olá, digite "duck" quando quiser falar comigo!!!'''
    
    # Responder
    def responder(self, resposta, chat_id):
        link_requisicao = f'{self.url_base}sendMessage?chat_id={chat_id}&text={resposta}'
        requests.get(link_requisicao)

    # Responder
    def monitorar(self, resposta, chat_id):
        link_requisicao = f'{self.url_base}sendMessage?chat_id={chat_id}text={resposta}'
        requests.get(link_requisicao)

bot = TelegramBot()
bot.Iniciar()