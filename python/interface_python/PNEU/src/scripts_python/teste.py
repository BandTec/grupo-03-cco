import psutil # importa a biblioteca

#variáveis que vão ser definidas no momento em que o sistema começar a rodar

maximo_cpu = 95 #define a porcentagem máxima de CPU que pode ser utilizada

maximo_memoria_GB = 7 #define o máximo de memória que o sistema pode utilizar
maximo_memoria_porcentagem = 90 #define o máximo de memória que o sistema pode utilizar em porcentagem

maximo_disco_GB = 700 #define o máximo de disco que o sistema pode utilizar
maximo_disco_porcentagem = 90 #define o máximo de disco que o sistema pode utilizar em porcentagem

contador_log = 0 #seta o contador de Logs em 0
temporizador = 0 #seta o temporizador em 0
intervalo = 3 #variável que controla o tempo em Segundos de delay entre um log e outro

mensagem_erro = "Houve um erro crítico inesperado, um Log será emitido e em seguida o sistema irá desligar"
mensagem_erro_memoria = "O uso de memória ultrapassou o máximo estabelecido de "
mensagem_erro_disco = "O uso de disco ultrapassou o máximo estabelecido de "

#loop que será executado enquanto algum erro crítico não ocorra

while True:

    #gerando variáveis de leitura de dados

    porcentagem_uso_cpu = psutil.cpu_percent(interval = intervalo) #Atribui à varíavel a leitura da porcentagem de uso da CPU  a cada 1s
    
    monitoramento_memoria = psutil.virtual_memory() #Atribui à varíavel, o método da biblioteca que monitora vários indíces da memória
    memoria_porcentagem = monitoramento_memoria.percent #Acessa o atributo "percent" (porcentagem da memória utilizada) e o atribui a variável
    
    leitura_disco = psutil.disk_usage('C:') #Atribui à varíavel, o método da biblioteca que monitora vários indíces da disco
    disco_porcentagem = leitura_disco.percent #Acessa o atributo "percent" (porcentagem da disco utilizada) e o atribui a variável

    #incrementadores
    temporizador += intervalo #conta os segundos
    contador_log += 1 #conta os Logs emitidos

    #mensagens
    mensagem_tempo = "O sistema ficou no ar por " + str(temporizador) +" segundos" #variável que guarda a mensagem que mostra quanto tempo o sistema ficou no ar
    

    #cálculos 

    memoria_total = monitoramento_memoria.total / pow(1024,3) #Acessa o atributo "total" (total de memória do computador), o converte de Bytes para GB e o atribui à variável
    memoria_em_uso = monitoramento_memoria.used / pow(1024,3) #Acessa o atributo "used" (Memória em uso), o converte de Bytes para GB e o atribui à variável 
    memoria_disponivel = monitoramento_memoria.available / pow(1024,3) #Acessa o atributo "availble" (memória disponível para uso), o converte de Bytes para GB e o atribui à variável
    
    disco_total = leitura_disco.total / pow(1024,3) #Acessa o atributo "total" (total de disco do computador), o converte de Bytes para GB e o atribui à variável
    disco_em_uso = leitura_disco.used / pow(1024,3)  #Acessa o atributo "used" (Disco em uso), o converte de Bytes para GB e o atribui à variável
    disco_disponivel = leitura_disco.free / pow(1024,3) #Acessa o atributo "availble" (Disco disponível para uso), o converte de Bytes para GB e o atribui à variável

    #Estrutura do log do sistema

    print(" \n----------------------------------------- \n") 
    print("Iniciando Log", contador_log)

    print(" \n----------------------------------------- \n") 
    print("Gerando Log de CPU \n")
    print("Uso de CPU =" ,porcentagem_uso_cpu ,"%") #printa a porcentagem da CPU em uso

    print("\n------------------------------------------ \n")  
    print("Gerando Log de memória")
    print('Memória Total do computador = {:.2f}' .format(memoria_total),"GB") #printa a quantidade de memória total do computador
    print('Memória em uso = {:.2f}' .format(memoria_em_uso),"GB") #printa a quantidade de memória em uso
    print('Porcentagem da memória em uso = {:.2f}' .format(memoria_porcentagem),"%") #printa a porcentagem da memória em uso
    print('Memória disponível = {:.2f}' .format(memoria_disponivel),"GB") #printa a quantidade de memória disponivel

    print("\n------------------------------------------ \n")
    print("Gerando Log de disco \n")
    print('Capacidade total de disco do computador = {:.2f}' .format(disco_total),"GB") #printa a capacidade total de disco total do computador
    print('Disco em uso = {:.2f}' .format(disco_em_uso),"GB") #printa a quantidade do disco em uso
    print('Porcentagem do disco em uso = {:.2f}' .format(disco_porcentagem),"%") #printa a porcentagem do disco em uso
    print('Disco disponível = {:.2f}' .format(disco_disponivel),"GB \n") #printa a quantidade de disco disponivel

    print("----------------------------------------- \n") 
    print("Fim do log" , contador_log,"\n")


    #tratando erros críticos de CPU

    if porcentagem_uso_cpu > maximo_cpu: 
        print(mensagem_erro)
        print("Uso de CPU atingiu o limite de " + str(maximo_cpu) + "% de uso de CPU")
        print(mensagem_tempo)
        break


    #tratando erros críticos de memória

    if memoria_em_uso > maximo_memoria_GB:
        print(mensagem_erro)
        print(mensagem_erro_memoria + str(maximo_memoria_GB) + "GB")
        print(mensagem_tempo)
        break
    
    if memoria_porcentagem > maximo_memoria_porcentagem:
        print(mensagem_erro)
        print(mensagem_erro_memoria + str(maximo_memoria_porcentagem) + "%")
        print(mensagem_tempo)
        break

    #tratando erros críticos de disco    

    if disco_em_uso > maximo_disco_GB:
        print(mensagem_erro)
        print(mensagem_erro_disco , str(maximo_disco_GB) + "GB")
        print(mensagem_tempo)
        break
    
    if disco_porcentagem > maximo_disco_porcentagem:
        print(mensagem_erro)
        print(mensagem_erro_disco , str(maximo_disco_porcentagem) + "%")
        print(mensagem_tempo)
        break
