import requests
import time
import json
import os


class TelegramBotHelp:
    def __init__(self):
        token = '1463259956:AAHkIGSqYjG2a2MCUId_Lv7pfVW8-KJIuj0'
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
                    eh_primeira_mensagem = int(
                        dado["message"]["message_id"]) == 1
                    resposta = self.criar_resposta(mensagem, eh_primeira_mensagem)
                    self.responder(resposta, chat_id)
                    print("Mensagem enviada: " + str(resposta))

    # Obter mensagens
    def obter_novas_mensagens(self, update_id):
        link_requisicao = f'{self.url_base}getUpdates?timeout=100'
        if update_id:
            link_requisicao = f'{link_requisicao}&offset={update_id + 1}'
        resultado = requests.get(link_requisicao)
        return json.loads(resultado.content)

    # Criar uma resposta
    def criar_resposta(self, mensagem, eh_primeira_mensagem):
        # se a mensagem que eu mandei, for a primeira mensagem, ou for igual a "conversar" ou "monitorar", a condição será satisfeita
        if eh_primeira_mensagem == True or mensagem in ('duck', 'Duck'):
          return f'''Digite o código referente a natureza do erro:{os.linesep}1 - 500 Erro interno de servidor (Internal Server Error){os.linesep}2 - 502 Bad Gateway{os.linesep}3 - 503 Service Unavailable (Serviço Indisponível){os.linesep}4 - 504 Gateway Timeout{os.linesep}5 - 505 HTTP Version not supported'''

        if mensagem == '1':
            return f'''Por padrão e por questões de segurança, o indicado é que arquivos tenham permissão 644 e pastas, 755. Mesmo sendo pouco comum, alguns CMSs mudam as permissões padrão para algumas instalações, ser reverter para o permissionamento original após a conclusão do processo.{os.linesep}(encerrar/voltar)'''

        elif mensagem == '2':
            return f'''O arquivo .htaccess costuma constar na pasta principal do site, mas também pode estar localizado em outras pastas e constitui um meio de configurar parâmetros do site e ambiente da conta. Ao realizar mudanças neste arquivo, renomeie o original para ter uma cópia inalterada dele e assim retornar ao padrão anterior à modificação rapidamente.{os.linesep}(encerrar/voltar)'''

        elif mensagem == '3':
            return f'''A versão do PHP é um motivo comum de erros HTTP, na medida em que a programação do site pode estar realizando requisições que são incompatíveis com a versão utilizada. Resolve-se facilmente a questão, mudando-se a versão exigida pelo site ou CMS usado, diretamente no cPanel da conta.{os.linesep}(encerrar/voltar)'''

        elif mensagem == '4':
            return f'''Assim como no caso da versão, determinados sites, frameworks e CMSs exigem que algumas extensões sejam ativadas para que o site funcione corretamente. A ausência de uma extensão exigida, irá produzir erros. Também como no caso da versão do PHP, as extensões disponíveis podem ser rapidamente e facilmente ativadas via cPanel.{os.linesep}(encerrar/voltar)'''
        
        elif mensagem == '5':
            return f'''Este é um módulo de segurança do Apache, cujo papel é a inclusão de regras de segurança para determinadas solicitações feitas por uma conta, bloqueando a programação que atende aos padrões e práticas inadequadas. Neste caso, há duas alternativas. Corrigir a programação correspondente, ou desativar a regra que acarreta em erro, porém tendo-se em mente que ao fazê-lo, a segurança do site estará comprometida.{os.linesep}(encerrar/voltar)'''
            
        elif mensagem == 'Não' or mensagem == 'não' or mensagem == 'Nao' or mensagem == 'nao':
            return f'''Ah, ok. Sempre que quiser pode contar comigo!!! *quack*'''

        elif mensagem.lower() in ('encerrar', 'e'):
            return '''Volte sempre, espero que eu tenha ajudado! *quack-quack* :)'''
            
        elif mensagem.lower() in ('voltar', 'v'):
            return f'''Digite o código referente a natureza do erro:{os.linesep}1 - 500 Erro interno de servidor (Internal Server Error){os.linesep}2 - 502 Bad Gateway{os.linesep}3 - 503 Service Unavailable (Serviço Indisponível){os.linesep}4 - 504 Gateway Timeout{os.linesep}5 - 505 HTTP Version not supported'''        
        
        else:
            return f'''Olá, me chamo DuckBot. *quack*{os.linesep}Estou aqui para te ajudar com as eventuais falhas de nosso sistema, digite "duck" para ver a lista de erros mais comuns!!'''

    # Responder
    def responder(self, resposta, chat_id):
        link_requisicao = f'{self.url_base}sendMessage?chat_id={chat_id}&text={resposta}'
        requests.get(link_requisicao)


botHelp = TelegramBotHelp()
botHelp.Iniciar()